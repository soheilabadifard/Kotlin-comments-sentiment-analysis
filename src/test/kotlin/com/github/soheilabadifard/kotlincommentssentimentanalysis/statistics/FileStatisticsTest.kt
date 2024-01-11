package com.github.soheilabadifard.kotlincommentssentimentanalysis.statistics

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FileStatisticsTest {

    @Test
    fun testAddPositive() {
        val stats = FileStatistics("TestFile.kt")
        stats.addPositive(0.8f)

        assertEquals(1, stats.positiveCount)
        assertEquals(0.8f, stats.totalPositiveProbability)
    }

    @Test
    fun testAddNegative() {
        val stats = FileStatistics("TestFile.kt")
        stats.addNegative(0.6f)

        assertEquals(1, stats.negativeCount)
        assertEquals(0.6f, stats.totalNegativeProbability)
    }

    @Test
    fun testAveragePositiveProbability() {
        val stats = FileStatistics("TestFile.kt")
        stats.addPositive(0.8f)
        stats.addPositive(0.6f)

        val expectedAverage = (0.8f + 0.6f) / 2
        assertEquals(expectedAverage, stats.averagePositiveProbability)
    }

    @Test
    fun testAverageNegativeProbability() {
        val stats = FileStatistics("TestFile.kt")
        stats.addNegative(0.3f)
        stats.addNegative(0.7f)

        val expectedAverage = (0.3f + 0.7f) / 2
        assertEquals(expectedAverage, stats.averageNegativeProbability)
    }

    @Test
    fun testAverageProbabilityWithNoComments() {
        val stats = FileStatistics("TestFile.kt")

        assertEquals(0.0f, stats.averagePositiveProbability)
        assertEquals(0.0f, stats.averageNegativeProbability)
    }
}