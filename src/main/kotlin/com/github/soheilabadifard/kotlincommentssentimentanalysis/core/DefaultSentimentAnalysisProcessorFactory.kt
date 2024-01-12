package com.github.soheilabadifard.kotlincommentssentimentanalysis.core

import com.intellij.openapi.project.Project

// Implement the default factory
class DefaultSentimentAnalysisProcessorFactory : SentimentAnalysisProcessorFactory {
    override fun create(project: Project): SentimentAnalysisProcessor {
        return SentimentAnalysisProcessor(project)
    }
}