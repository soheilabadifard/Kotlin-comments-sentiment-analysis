package com.github.soheilabadifard.kotlincommentssentimentanalysis.core

import com.intellij.openapi.project.Project

interface SentimentAnalysisProcessorFactory {
    fun create(project: Project): SentimentAnalysisProcessor
}