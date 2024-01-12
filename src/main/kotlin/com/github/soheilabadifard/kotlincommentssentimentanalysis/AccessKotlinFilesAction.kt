package com.github.soheilabadifard.kotlincommentssentimentanalysis

import com.github.soheilabadifard.kotlincommentssentimentanalysis.core.SentimentAnalysisProcessorFactory
import com.github.soheilabadifard.kotlincommentssentimentanalysis.core.DefaultSentimentAnalysisProcessorFactory

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class AccessKotlinFilesAction(private val processorFactory: SentimentAnalysisProcessorFactory = DefaultSentimentAnalysisProcessorFactory()) : AnAction("Analyze KT File's Sentiment") {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val processor = processorFactory.create(project)
        processor.performAnalysis()
    }
}
