//package com.github.soheilabadifard.kotlincommentssentimentanalysis
//
////import com.intellij.openapi.command.WriteCommandAction
////import com.intellij.openapi.vfs.LocalFileSystem
////import com.intellij.openapi.actionSystem.ActionManager
////import com.intellij.ide.DataManager
////import com.intellij.openapi.fileTypes.FileTypeManager
////import com.intellij.openapi.project.Project
////import com.intellij.openapi.vfs.VirtualFile
////import com.intellij.psi.PsiFile
////import com.intellij.psi.PsiFileFactory
////import com.intellij.psi.PsiManager
////import com.intellij.testFramework.fixtures.BasePlatformTestCase
////import com.intellij.testFramework.HeavyPlatformTestCase
////import org.junit.jupiter.api.BeforeAll
//
//
//
//import com.intellij.openapi.actionSystem.ActionManager
//import com.intellij.openapi.actionSystem.AnActionEvent
//import com.intellij.openapi.actionSystem.DataContext
////import com.intellij.openapi.actionSystem.Presentation
////import com.intellij.openapi.project.ProjectManager
//import org.junit.jupiter.api.AfterEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.io.TempDir
//
//import java.nio.file.Files
//import java.nio.file.Path
//import java.nio.file.StandardCopyOption
//
////class AccessKotlinFilesActionTest{
////
////    @Mock
////    private lateinit var mockProject: Project
////
////    @Mock
////    private lateinit var mockAnActionEvent: AnActionEvent
////
////    @Mock
////    private lateinit var mockVirtualFile: VirtualFile
////
////    @Mock
////    private lateinit var mockPsiFile: PsiFile
////
////    @Mock
////    private lateinit var mockPsiManager: PsiManager
////
////    private lateinit var testProjectRoot: Path
////
////    @BeforeEach
////    fun setUp() {
////
////        testProjectRoot = Files.createTempDirectory("testProject")
////        copyResourceToFile("base_vocabulary.json", testProjectRoot)
////        copyResourceToFile("merges.txt", testProjectRoot)
////        copyResourceToFile("vocabulary.json", testProjectRoot)
////        copyResourceToFile("roberta-sequence-classification-9.onnx", testProjectRoot)
////
////        MockitoAnnotations.openMocks(this)
////
////        Mockito.`when`(mockAnActionEvent.project).thenReturn(mockProject)
////        Mockito.`when`(mockProject.basePath).thenReturn(testProjectRoot.toString())
////        Mockito.`when`(mockProject.getService(PsiManager::class.java)).thenReturn(mockPsiManager)
////        Mockito.`when`(mockPsiManager.findFile(mockVirtualFile)).thenReturn(mockPsiFile)
////        // Mock other necessary interactions
////    }
////
////    @Test
////    fun testActionPerformed() {
////        val action = AccessKotlinFilesAction()
////
////        // Set up a scenario where mockVirtualFile represents a Kotlin file
////        Mockito.`when`(mockVirtualFile.extension).thenReturn("kt")
////        // You can further mock interactions with mockVirtualFile and mockPsiFile as needed
////
////        // Execute the action
////        action.actionPerformed(mockAnActionEvent)
////
////        // Verify the outcomes, such as interactions with mockVirtualFile, mockPsiFile, etc.
////        // You can also verify interactions with UI components if applicable
////    }
////    @AfterEach
////    fun tearDown() {
////        testProjectRoot.toFile().deleteRecursively()
////    }
////    private fun copyResourceToFile(resourceName: String, destinationDirectory: Path) {
////        val resource = this.javaClass.classLoader.getResource(resourceName)
////        if (resource != null) {
////            val resourcePath = Path.of(resource.toURI())
////            val destinationPath = destinationDirectory.resolve(resourceName)
////            Files.copy(resourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING)
////        } else {
////            throw IllegalArgumentException("Resource not found: $resourceName")
////        }
////    }
////}
//
//class AccessKotlinFilesActionTest {
//
//    private lateinit var myAction: AccessKotlinFilesAction
//    private lateinit var mockEvent: AnActionEvent
//
//    @TempDir
//    lateinit var tempDir: Path
//
//    @BeforeEach
//    fun setUp() {
//
//        setupVirtualFileSystem(tempDir)
//
//        // Assume your tokenizer resources are in 'src/test/resources/tokenizer'
//        val tokenizerResourcePath = Path.of("src/test/testdata/")
//        copyDirectory(tokenizerResourcePath, tempDir)
//
//        // Create a dummy DataContext
//        val dataContext = DataContext { dataId -> null } // Return null for all data IDs
//
//        // Create a dummy Presentation object
//        val presentation = AccessKotlinFilesAction().templatePresentation
//
//        // Use a default project instance (adjust as necessary)
//        //val project = ProjectManager.getInstance().defaultProject
//
//        // Create a mock AnActionEvent
//        mockEvent = AnActionEvent(null, dataContext, "", presentation, ActionManager.getInstance(), 0)
//
//        // Additional setup if needed
//        myAction = AccessKotlinFilesAction()
//    }
//
//
////    fun `test AccessKotlinFilesAction`() {
////        // Load a Kotlin file
////        val virtualFile = myFixture.addFileToProject("Test.kt", "fun main() {}").virtualFile
////        myFixture.openFileInEditor(virtualFile)
////
////        // Prepare the data context
////        val dataContext = DataManager.getInstance().getDataContext(myFixture.editor.component)
////
////        // Trigger the actionPerformed
////        val action = AccessKotlinFilesAction()
////        action.actionPerformed(AnActionEvent(null, dataContext, "", action.templatePresentation, ActionManager.getInstance(), 0))
////
////        // Assert expected outcomes
////        // Assertions go here
////
////        // More detailed checks can be performed depending on what actionPerformed does
////    }
//    @Test
//    fun testActionPerformed (){
//        myAction.actionPerformed(mockEvent)
//
//    }
//    @AfterEach
//    fun tearDown() {
//    }
//
////    override fun getTestDataPath(): String {
////        return "/testdata" // Set this to your test data path
////    }
//    private fun setupVirtualFileSystem(testRootPath: Path) {
//        val kotlinFileContent = """
//                // positive
//                fun main() {
//                    println("Hello, world!")
//                }
//            """.trimIndent()
//
//        // Create and write to a Kotlin file in the temporary directory
//        Files.writeString(testRootPath.resolve("MyTestFile.kt"), kotlinFileContent)
//    }
//    private fun copyDirectory(sourceDirectoryLocation: Path, destinationDirectoryLocation: Path) {
//        Files.walk(sourceDirectoryLocation).forEach { sourcePath ->
//            val destinationPath = destinationDirectoryLocation.resolve(sourceDirectoryLocation.relativize(sourcePath))
//            if (Files.isDirectory(sourcePath)) {
//                if (Files.notExists(destinationPath)) {
//                    Files.createDirectory(destinationPath)
//                }
//            } else {
//                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING)
//            }
//        }
//    }
//}
