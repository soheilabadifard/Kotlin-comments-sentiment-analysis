package com.github.soheilabadifard.kotlincommentssentimentanalysis.toolWindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class SentimentAnalysisToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()
        val content = contentFactory.createContent(SentimentAnalysisPanel(), "", false)
        toolWindow.contentManager.addContent(content)
    }
}