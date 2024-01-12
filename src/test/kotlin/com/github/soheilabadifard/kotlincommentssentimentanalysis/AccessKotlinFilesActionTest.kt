package com.github.soheilabadifard.kotlincommentssentimentanalysis

import com.github.soheilabadifard.kotlincommentssentimentanalysis.core.SentimentAnalysisProcessor
import com.github.soheilabadifard.kotlincommentssentimentanalysis.core.SentimentAnalysisProcessorFactory
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.mockito.kotlin.any



class AccessKotlinFilesActionTest : BasePlatformTestCase() {

    private lateinit var mockProcessorFactory: SentimentAnalysisProcessorFactory
    private lateinit var mockProcessor: SentimentAnalysisProcessor

    override fun setUp() {
        super.setUp()
        mockProcessorFactory = mock()
        mockProcessor = mock()
        whenever(mockProcessorFactory.create(any())).thenReturn(mockProcessor)
    }

    fun testActionPerformed() {
        val action = AccessKotlinFilesAction(mockProcessorFactory)
        val mockEvent = mock<AnActionEvent>().apply {
            whenever(project).thenReturn(mock<Project>())
        }

        action.actionPerformed(mockEvent)

        // Verify that performAnalysis was called
        verify(mockProcessor).performAnalysis()
    }
}
