package com.github.soheilabadifard.kotlincommentssentimentanalysis.toolWindow

import javax.swing.BoxLayout
import java.awt.Component
import java.awt.Dimension
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JScrollPane

class SentimentAnalysisPanel : JPanel() {
    val textArea = JTextArea(10, 50)
    val statisticsTextArea = JTextArea(10, 50)

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)

        textArea.isEditable = false  // If you want to make the text area read-only
        statisticsTextArea.isEditable = false

        val scrollPane = JScrollPane(textArea)
        val statisticsScrollPane = JScrollPane(statisticsTextArea)

//        scrollPane.preferredSize = Dimension(700, 500)
//        statisticsScrollPane.preferredSize = Dimension(700, 300)
        scrollPane.alignmentX = Component.LEFT_ALIGNMENT
        statisticsScrollPane.alignmentX = Component.LEFT_ALIGNMENT

        scrollPane.maximumSize = Dimension(Integer.MAX_VALUE, scrollPane.maximumSize.height)
        statisticsScrollPane.maximumSize = Dimension(Integer.MAX_VALUE, statisticsScrollPane.maximumSize.height)

        add(scrollPane)
        add(statisticsScrollPane)
    }

    fun appendText(text: String) {
        textArea.append("$text\n")
    }

    fun appendStatisticsText(text: String) {
        statisticsTextArea.append("$text\n")
    }

    fun clear() {
        textArea.text = ""  // Clear the text area
        statisticsTextArea.text = ""
    }
}