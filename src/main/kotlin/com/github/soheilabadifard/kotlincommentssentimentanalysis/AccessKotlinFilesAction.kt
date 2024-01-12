package com.github.soheilabadifard.kotlincommentssentimentanalysis

import com.github.soheilabadifard.kotlincommentssentimentanalysis.core.SentimentAnalysisProcessor

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class AccessKotlinFilesAction : AnAction("Analyze KT File's Sentiment") {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val projectPath = project.basePath ?: return
        val processor = SentimentAnalysisProcessor(project)
        processor.performAnalysis()
    }
}