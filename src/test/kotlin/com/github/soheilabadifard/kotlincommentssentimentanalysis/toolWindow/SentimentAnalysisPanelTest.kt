package com.github.soheilabadifard.kotlincommentssentimentanalysis.toolWindow

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import javax.swing.SwingUtilities

class SentimentAnalysisPanelTest {

    private lateinit var panel: SentimentAnalysisPanel

    @BeforeEach
    fun setUp() {
        // Initialize the panel before each test
        panel = SentimentAnalysisPanel()
    }

    @Test
    fun testAppendCommentText() {
        SwingUtilities.invokeLater {
            val testComment = "Test comment"
            panel.appendText(testComment)
            assertTrue(
                panel.textArea.text.contains(testComment),
                "The comment text area should contain the appended comment text."
            )
        }
    }

    @Test
    fun testAppendStatisticsText() {
        SwingUtilities.invokeLater {
            val testStatistics = "Test statistics"
            panel.appendStatisticsText(testStatistics)
            assertTrue(
                panel.statisticsTextArea.text.contains(testStatistics),
                "The statistics text area should contain the appended statistics text."
            )
        }
    }

    @Test
    fun testClear() {
        SwingUtilities.invokeLater {
            // Append some text first
            panel.appendText("Test comment")
            panel.appendStatisticsText("Test statistics")

            // Now clear the text areas
            panel.clear()

            assertTrue(panel.textArea.text.isEmpty(), "The comment text area should be empty after clear.")
            assertTrue(panel.statisticsTextArea.text.isEmpty(), "The statistics text area should be empty after clear.")
        }
    }
}