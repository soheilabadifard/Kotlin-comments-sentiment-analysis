package com.github.soheilabadifard.kotlincommentssentimentanalysis.startup

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.io.File

class MyStartupActivityTest: BasePlatformTestCase() {
    private lateinit var myStartupActivity: MyStartupActivity
    override fun setUp() {
        super.setUp()
        myStartupActivity = MyStartupActivity()
    }

    fun testRunActivity() {

        myStartupActivity.runActivity(project)

        // Verify that files are downloaded/copied to the correct locations
        val modelFile = File(project.basePath + "/roberta-sequence-classification-9.onnx")
        assertTrue(modelFile.exists())

        val vocabFile = File(project.basePath + "/vocabulary.json")
        assertTrue(vocabFile.exists())

        val baseFile = File(project.basePath + "/base_vocabulary.json")
        assertTrue(baseFile.exists())

        val mergeFile = File(project.basePath + "/merges.txt")
        assertTrue(mergeFile.exists())

    }
}