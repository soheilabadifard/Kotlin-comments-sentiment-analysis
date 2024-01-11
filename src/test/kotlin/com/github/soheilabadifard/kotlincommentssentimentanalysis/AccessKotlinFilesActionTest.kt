package com.github.soheilabadifard.kotlincommentssentimentanalysis

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.ide.DataManager

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

//class AccessKotlinFilesActionTest{
//
//    @Mock
//    private lateinit var mockProject: Project
//
//    @Mock
//    private lateinit var mockAnActionEvent: AnActionEvent
//
//    @Mock
//    private lateinit var mockVirtualFile: VirtualFile
//
//    @Mock
//    private lateinit var mockPsiFile: PsiFile
//
//    @Mock
//    private lateinit var mockPsiManager: PsiManager
//
//    private lateinit var testProjectRoot: Path
//
//    @BeforeEach
//    fun setUp() {
//
//        testProjectRoot = Files.createTempDirectory("testProject")
//        copyResourceToFile("base_vocabulary.json", testProjectRoot)
//        copyResourceToFile("merges.txt", testProjectRoot)
//        copyResourceToFile("vocabulary.json", testProjectRoot)
//        copyResourceToFile("roberta-sequence-classification-9.onnx", testProjectRoot)
//
//        MockitoAnnotations.openMocks(this)
//
//        Mockito.`when`(mockAnActionEvent.project).thenReturn(mockProject)
//        Mockito.`when`(mockProject.basePath).thenReturn(testProjectRoot.toString())
//        Mockito.`when`(mockProject.getService(PsiManager::class.java)).thenReturn(mockPsiManager)
//        Mockito.`when`(mockPsiManager.findFile(mockVirtualFile)).thenReturn(mockPsiFile)
//        // Mock other necessary interactions
//    }
//
//    @Test
//    fun testActionPerformed() {
//        val action = AccessKotlinFilesAction()
//
//        // Set up a scenario where mockVirtualFile represents a Kotlin file
//        Mockito.`when`(mockVirtualFile.extension).thenReturn("kt")
//        // You can further mock interactions with mockVirtualFile and mockPsiFile as needed
//
//        // Execute the action
//        action.actionPerformed(mockAnActionEvent)
//
//        // Verify the outcomes, such as interactions with mockVirtualFile, mockPsiFile, etc.
//        // You can also verify interactions with UI components if applicable
//    }
//    @AfterEach
//    fun tearDown() {
//        testProjectRoot.toFile().deleteRecursively()
//    }
//    private fun copyResourceToFile(resourceName: String, destinationDirectory: Path) {
//        val resource = this.javaClass.classLoader.getResource(resourceName)
//        if (resource != null) {
//            val resourcePath = Path.of(resource.toURI())
//            val destinationPath = destinationDirectory.resolve(resourceName)
//            Files.copy(resourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING)
//        } else {
//            throw IllegalArgumentException("Resource not found: $resourceName")
//        }
//    }
//}

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class AccessKotlinFilesActionTest : BasePlatformTestCase() {

    override fun setUp() {
        super.setUp()
        // Additional setup if needed
    }

    
    fun `test AccessKotlinFilesAction`() {
        // Load a Kotlin file
        val virtualFile = myFixture.addFileToProject("Test.kt", "fun main() {}").virtualFile
        myFixture.openFileInEditor(virtualFile)

        // Prepare the data context
        val dataContext = DataManager.getInstance().getDataContext(myFixture.editor.component)

        // Trigger the actionPerformed
        val action = AccessKotlinFilesAction()
        action.actionPerformed(AnActionEvent(null, dataContext, "", action.templatePresentation, ActionManager.getInstance(), 0))

        // Assert expected outcomes
        // Assertions go here

        // More detailed checks can be performed depending on what actionPerformed does
    }

    override fun tearDown() {
        try {
            // Test specific tear down
        } finally {
            super.tearDown()
        }
    }

    override fun getTestDataPath(): String {
        return "/testdata" // Set this to your test data path
    }
}
