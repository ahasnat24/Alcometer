package com.example.alcometer

import com.example.alcometer.calculator.AlcometerCalculator
import com.example.alcometer.model.Gender
import org.junit.Assert.assertEquals
import org.junit.Test

class AlcometerCalculatorTest {

    @Test
    fun calculateBloodAlcohol_maleExampleFromSpec_returnsCorrectResult() {
        // 89kg male, 3 bottles, 1 hour
        val result = AlcometerCalculator.calculateBloodAlcohol(
            weightKg = 89.0,
            bottles = 3,
            hours = 1,
            gender = Gender.MALE
        )
        // Litres = 0.99, Grams = 35.64, Burning = 8.9, Grams left = 26.74
        // 26.74 / (89 * 0.7) = 26.74 / 62.3 = ~0.4292
        assertEquals(0.43, result, 0.005)
    }

    @Test
    fun calculateBloodAlcohol_femaleExampleFromSpec_returnsCorrectResult() {
        // 89kg female, 3 bottles, 1 hour
        val result = AlcometerCalculator.calculateBloodAlcohol(
            weightKg = 89.0,
            bottles = 3,
            hours = 1,
            gender = Gender.FEMALE
        )
        // Litres = 0.99, Grams = 35.64, Burning = 8.9, Grams left = 26.74
        // 26.74 / (89 * 0.6) = 26.74 / 53.4 = ~0.5007
        assertEquals(0.50, result, 0.005)
    }

    @Test
    fun calculateBloodAlcohol_negativeGramsLeft_returnsZero() {
        // 80kg male, 1 bottle, 10 hours -> burning (8.0 * 10 = 80) far exceeds alcohol intake
        val result = AlcometerCalculator.calculateBloodAlcohol(
            weightKg = 80.0,
            bottles = 1,
            hours = 10,
            gender = Gender.MALE
        )
        assertEquals(0.0, result, 0.0001)
    }

    @Test
    fun calculateBloodAlcohol_zeroWeight_returnsZero() {
        val result = AlcometerCalculator.calculateBloodAlcohol(
            weightKg = 0.0,
            bottles = 3,
            hours = 1,
            gender = Gender.MALE
        )
        assertEquals(0.0, result, 0.0001)
    }
}
