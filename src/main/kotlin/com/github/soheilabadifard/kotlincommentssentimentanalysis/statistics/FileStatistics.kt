package com.github.soheilabadifard.kotlincommentssentimentanalysis.statistics

data class FileStatistics(
    val fileName: String,
    var positiveCount: Int = 0,
    var negativeCount: Int = 0,
    var totalPositiveProbability: Float = 0.0f,
    var totalNegativeProbability: Float = 0.0f
) {
    fun addPositive(probability: Float) {
        positiveCount++
        totalPositiveProbability += probability
    }

    fun addNegative(probability: Float) {
        negativeCount++
        totalNegativeProbability += probability
    }

    val averagePositiveProbability: Float
        get() = if (positiveCount > 0) totalPositiveProbability / positiveCount else 0.0f

    val averageNegativeProbability: Float
        get() = if (negativeCount > 0) totalNegativeProbability / negativeCount else 0.0f
}