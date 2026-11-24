package com.example.alcometer.calculator

import com.example.alcometer.model.Gender

/**
 * Calculator utility for estimating blood alcohol level based on:
 * - Weight (in kg)
 * - Number of 0.33L beer bottles drunk
 * - Time passed in hours
 * - Gender (Male / Female)
 *
 * Formulas applied:
 * Litres = bottles * 0.33
 * Grams = litres * 8 * 4.5
 * Burning = weight / 10
 * Grams left = grams - (burning * hours)
 * Result (Male) = grams left / (weight * 0.7)
 * Result (Female) = grams left / (weight * 0.6)
 */
object AlcometerCalculator {

    fun calculateBloodAlcohol(
        weightKg: Double,
        bottles: Int,
        hours: Int,
        gender: Gender
    ): Double {
        if (weightKg <= 0.0) return 0.0

        val litres = bottles * 0.33
        val grams = litres * 8.0 * 4.5
        val burning = weightKg / 10.0
        val gramsLeft = (grams - (burning * hours)).coerceAtLeast(0.0)

        val genderFactor = if (gender == Gender.MALE) 0.7 else 0.6
        return gramsLeft / (weightKg * genderFactor)
    }
}
