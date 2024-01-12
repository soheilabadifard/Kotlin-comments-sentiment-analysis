package com.github.soheilabadifard.kotlincommentssentimentanalysis.core

import com.github.soheilabadifard.kotlincommentssentimentanalysis.toolWindow.SentimentAnalysisPanel
import com.github.soheilabadifard.kotlincommentssentimentanalysis.statistics.FileStatistics

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment

import com.genesys.roberta.tokenizer.RobertaTokenizer
import com.genesys.roberta.tokenizer.RobertaTokenizerResources


import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VfsUtil

import com.intellij.psi.PsiComment
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil

import io.kinference.ort.ORTEngine
import io.kinference.ort.model.ORTModel
import io.kinference.ort.data.utils.createORTData
import io.kinference.ort.ORTData

import kotlinx.coroutines.*
import kotlin.math.exp
class SentimentAnalysisProcessor(private val project: Project) {
    private val pluginScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var model: ORTModel? = null
    private var myTokenizer: RobertaTokenizer? = null
    private val projectPath = project.basePath

    init {
        if (projectPath != null) {
            initModel(projectPath)
            initTokenizer(projectPath)
        }
    }
    val env: OrtEnvironment = OrtEnvironment.getEnvironment()

    // Map to hold statistics for each file
    val fileStatisticsMap = mutableMapOf<String, FileStatistics>()

    fun performAnalysis() {

        pluginScope.launch {
            // Suspend until the model and tokenizer are loaded
            while (model == null || myTokenizer == null) {
                delay(500)
                // Wait for 500ms before checking again
            }
            ApplicationManager.getApplication().invokeLater {
                val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Sentiment Analysis Results")
                val myPanel = toolWindow?.contentManager?.getContent(0)?.component as? SentimentAnalysisPanel
                myPanel?.clear()

                ApplicationManager.getApplication().runReadAction {

                    // Get an instance of PsiManager for the project
                    val psiManager = PsiManager.getInstance(project)

                    // Get all source roots from the project
                    val virtualFiles = ProjectRootManager.getInstance(project).contentSourceRoots

                    // Iterate over each root
                    virtualFiles.forEach { root ->
                        VfsUtil.iterateChildrenRecursively(root, null) { virtualFile ->
                            if (virtualFile.extension == "kt") {
                                val psiFile = psiManager.findFile(virtualFile)

                                // Extract comments from the PsiFile
                                if (psiFile != null) {
                                    val fileName = virtualFile.name
                                    val currentFileStats =
                                        fileStatisticsMap.getOrPut(fileName) { FileStatistics(fileName) }
                                    println("Processing file: $fileName")

                                    val comments = PsiTreeUtil.findChildrenOfType(psiFile, PsiComment::class.java)

                                    comments.forEach { comment ->
                                        var output: Map<String, ORTData<*>?>?

                                        // Get the text of the comment
                                        var commentText = comment.text
                                        commentText = commentText.replace("//", "")
                                        commentText = commentText.replace("/*", "")
                                        commentText = commentText.replace("*/", "")
                                        println("Comment: $commentText")

                                        pluginScope.launch {

                                            val inputIds = myTokenizer?.tokenize(commentText)
                                            println("Encoded Ids: $inputIds")

                                            // modify Encoded Ids according to the model requirement
                                            // from [input_ids] to [[input_ids]]
                                            val newInputIds = Array(1) { inputIds?.let { it1 -> LongArray(it1.size) } }
                                            if (inputIds != null) {
                                                System.arraycopy(inputIds, 0, newInputIds[0], 0, inputIds.size)
                                            }

                                            // val idsTensor = OnnxTensor.createTensor(env, newInputIds)
                                            val finalIds =
                                                createORTData("input", OnnxTensor.createTensor(env, newInputIds))

                                            // run model inference
                                            output = model?.predict(listOf(finalIds))

                                            // Find the index of the maximum value in scores
                                            val sentimentTensor: OnnxTensor = output?.get("output")?.data as OnnxTensor
                                            val sentiment: Array<FloatArray> =
                                                (sentimentTensor.value as Array<FloatArray>?)!!
                                            val sentimentList: List<Float> = sentiment[0].toList()
                                            val sent = getSentiment(sentimentList)
                                            println("Comment: $commentText , Sentiment: ${sent.keys}, Probability: ${sent.values}")
                                            val cntMessage = """ File: $fileName \n Comment: $commentText \n Sentiment: ${sent.keys}, Probability: ${sent.values} \n"""
                                            myPanel?.appendText(cntMessage)
                                            sent.forEach { (type, probability) ->
                                                when (type) {
                                                    "Positive" -> currentFileStats.addPositive(probability)
                                                    "Negative" -> currentFileStats.addNegative(probability)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            true  // Continue iterating
                        }
                    }
                    pluginScope.launch {
                        delay(1000)  // Adjust this delay based on your processing time
                        fileStatisticsMap.values.forEach { stats ->
                            val statsMessage = """
                                File: ${stats.fileName}
                                Positive Comments: ${stats.positiveCount}, Avg Probability: ${stats.averagePositiveProbability}
                                Negative Comments: ${stats.negativeCount}, Avg Probability: ${stats.averageNegativeProbability}
                                """.trimIndent()
                            // Display statsMessage in your tool window or console
                            ApplicationManager.getApplication().invokeLater {
                                myPanel?.appendStatisticsText(statsMessage)
                            }
                        }
                    }
                }

            }
        }
    }

    private fun initModel(path: String) {
        pluginScope.launch{
            model = ORTEngine.loadModel("$path/roberta-sequence-classification-9.onnx")
        }
        println("Model loaded")
    }
    private fun initTokenizer(path: String) {
        val tokenizerResource = RobertaTokenizerResources(path)
        myTokenizer = RobertaTokenizer(tokenizerResource)
        println("Tokenizer loaded")
    }
    private fun softMax(x: List<Float>): List<Float>{
        val odds = x.map { exp(it.toDouble())}
        val sum = odds.sum()
        return odds.map { it.toFloat() / sum.toFloat() }
    }
    private fun getSentiment(x: List<Float>): Map<String, Float>{
        val out: MutableMap<String, Float> = mutableMapOf()
        val probability = softMax(x)
        return when(val maxIndex = x.indexOf(x.maxOrNull())){
            0 -> out.apply {
                put("Negative", probability[maxIndex])
            }
            1 -> out.apply {
                put("Positive", probability[maxIndex])
            }
            else -> out.apply {
                put("Unknown", 0.0F)
            }
        }
    }
}