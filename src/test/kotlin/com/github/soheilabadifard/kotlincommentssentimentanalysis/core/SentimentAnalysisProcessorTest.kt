package com.github.soheilabadifard.kotlincommentssentimentanalysis.core

import com.github.soheilabadifard.kotlincommentssentimentanalysis.statistics.FileStatistics
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.PsiComment
import java.nio.file.Files
import java.nio.file.Path
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify


class SentimentAnalysisProcessorTest : BasePlatformTestCase() {

    private lateinit var mockProcessorFactory: SentimentAnalysisProcessorFactory
    private lateinit var mockProcessor: SentimentAnalysisProcessor

    override fun setUp() {
        super.setUp()
        mockProcessorFactory = mock()
        mockProcessor = mock()
        whenever(mockProcessorFactory.create(myFixture.project)).thenReturn(mockProcessor)

        setupMockProcessorBehavior()
        // Copy tokenizer resources to the test project
        copyTokenizerResources()
        setupVirtualFileSystem()
    }
    private fun setupMockProcessorBehavior() {
        // FileStatsMap is a Map<String, FileStatistics>
        // Set up the mock to return a predefined map or specific values when accessed
        val mockFileStatsMap = mapOf(
            "MyTestFile.kt" to createMockFileStatistics()
        )
        whenever(mockProcessor.fileStatisticsMap).thenReturn(mockFileStatsMap as MutableMap<String, FileStatistics>?)
    }

    private fun createMockFileStatistics(): FileStatistics {
        // Create an instance of FileStatistics with predefined values
        val mockFileStats = FileStatistics("MyTestFile.kt")

        // FileStatistics class has methods to add sentiment data
        // Add mock data for positive comments
        mockFileStats.addPositive(0.8F) // Example probability value for a positive comment

        return mockFileStats
    }

    private fun copyTokenizerResources() {
        // Define the path to your tokenizer resources within your project
        val tokenizerResourcePath = Path.of("src/test/testdata/")

        // Copy each file from tokenizer Resource Path to the test project's virtual file system
        Files.walk(tokenizerResourcePath).forEach { filePath ->
            if (!Files.isDirectory(filePath)) {
                val relativePath = tokenizerResourcePath.relativize(filePath).toString()
                val fileContent = Files.readString(filePath)
                myFixture.addFileToProject(relativePath, fileContent)
            }
        }
    }

    private fun setupVirtualFileSystem() {
        val kotlinFileContent = """
                package org.intellij.sdk.kotlin

                import com.intellij.openapi.actionSystem.AnActionEvent
                import com.intellij.openapi.actionSystem.PlatformDataKeys
                import com.intellij.openapi.project.DumbAwareAction
                import com.intellij.openapi.ui.Messages
                class MyTestFile : DumbAwareAction() {
                  // positive
                  override fun actionPerformed(event: AnActionEvent) {
                    val project = event.getData(PlatformDataKeys.PROJECT)
                    Messages.showMessageDialog(project, "Hello from Kotlin!", "Greeting", Messages.getInformationIcon())
                  }
                
                }
            """.trimIndent()

        // Create and write to a Kotlin file in the temporary directory
        myFixture.addFileToProject("src/main/kotlin/org/intellij/sdk/kotlin/MyTestFile.kt", kotlinFileContent)
    }

    fun testActionPerformed() {

        val processor = mockProcessorFactory.create(myFixture.project)
        processor.performAnalysis()
        verify(mockProcessor).performAnalysis()

        val fileStatsMap = mockProcessor.fileStatisticsMap


        val psiFile = myFixture.findFileInTempDir("src/main/kotlin/org/intellij/sdk/kotlin/MyTestFile.kt")
        assertNotNull("PsiFile should not be null", psiFile)

        val psiManager = PsiManager.getInstance(project)
        val comments = PsiTreeUtil.findChildrenOfType(psiManager.findFile(psiFile!!), PsiComment::class.java)

        assertFalse("There should be comments in the file", comments.isEmpty())

        val expectedFileName = "MyTestFile.kt"
        val fileStats = fileStatsMap[expectedFileName]
        assertNotNull("Stats for $expectedFileName should exist", fileStats)

        // Verify the statistics of the file
        assertTrue("There should be at least one positive comment", fileStats!!.positiveCount > 0)

        assertTrue("Average probability for positive comments should be within range",
            fileStats.averagePositiveProbability in 0.0..1.0)

        // Check if the map is not empty
        //assertFalse("fileStatisticsMap should not be empty", fileStatsMap.isEmpty())

        // For demonstration, let's say you know the expected output for a specific file
        //val expectedFileName = "MyTestFile.kt"
        //val fileStats = fileStatsMap[expectedFileName]
        //assertNotNull("Stats for $expectedFileName should exist", fileStats)

        // Verify the statistics of the file
        //assertTrue("There should be at least one positive comment", fileStats!!.positiveCount > 0)
        //assertTrue("Average probability for positive comments should be within range",
        //    fileStats.averagePositiveProbability in 0.0..1.0)
    }
}