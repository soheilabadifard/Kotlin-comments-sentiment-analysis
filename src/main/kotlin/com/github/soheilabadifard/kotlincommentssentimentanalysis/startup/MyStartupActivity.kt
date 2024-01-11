package com.github.soheilabadifard.kotlincommentssentimentanalysis.startup

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.project.DumbAware
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL

class MyStartupActivity : StartupActivity, DumbAware {
    override fun runActivity(project: Project) {

        // download the file from resources to the destination path
        val fileUrlModel = "https://github.com/onnx/models/raw/main/validated/text/machine_comprehension/roberta/model/roberta-sequence-classification-9.onnx"
        val destinationPathModel = project.basePath + "/roberta-sequence-classification-9.onnx"
        downloadResourceToFile(fileUrlModel, destinationPathModel)

        val baseVocabFilePath = "/base_vocabulary.json"
        val baseVocabDestPath = project.basePath + "/base_vocabulary.json"
        copyResourceToFile(baseVocabFilePath, baseVocabDestPath)

        val vocabFilePath = "/vocabulary.json"
        val vocabDestPath = project.basePath + "/vocabulary.json"
        copyResourceToFile(vocabFilePath, vocabDestPath)

        val mergeFilePath = "/merges.txt"
        val mergeDestPath = project.basePath + "/merges.txt"
        copyResourceToFile(mergeFilePath, mergeDestPath)
    }

    private fun downloadResourceToFile(lnk: String, destinationPath: String) {
        val destinationFile = File(destinationPath)

        if (!destinationFile.exists()){
            println("Downloading file from: $lnk")
            try {
                val url = URL(lnk)
                val connection = url.openConnection()

                // Open input stream from the URL connection
                val inputStream = connection.getInputStream()
                destinationFile.parentFile.mkdirs()  // Ensure the parent directories exist

                val outputStream = FileOutputStream(destinationFile)

                // Copy data from the input stream to the output stream
                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                println("File downloaded to: $destinationPath")
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
//            val inputStream: InputStream = javaClass.getResourceAsStream(resourcePath)
//                ?: throw IllegalArgumentException("Resource not found: $resourcePath")
//            FileOutputStream(destinationFile).use { output ->
//                inputStream.copyTo(output)
//            }
        }
        else {
            println("File already exists: $destinationPath")
        }
    }

    private fun copyResourceToFile(resourcePath: String, destinationPath: String) {
        val destinationFile = File(destinationPath)

        if (!destinationFile.exists()){
            val inputStream: InputStream = javaClass.getResourceAsStream(resourcePath)
                ?: throw IllegalArgumentException("Resource not found: $resourcePath")


            destinationFile.parentFile.mkdirs()  // Ensure the parent directories exist

            FileOutputStream(destinationFile).use { output ->
                inputStream.copyTo(output)
            }
        }
        else {
            println("File already exists: $destinationPath")
        }

    }
}