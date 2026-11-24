package com.example.alcometer.ui

import androidx.lifecycle.ViewModel
import com.example.alcometer.calculator.AlcometerCalculator
import com.example.alcometer.model.Gender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AlcometerUiState(
    val weightInput: String = "89",
    val gender: Gender = Gender.MALE,
    val bottles: Int = 3,
    val hours: Int = 1,
    val result: Double = 0.43,
    val isWeightError: Boolean = false
)

class AlcometerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AlcometerUiState())
    val uiState: StateFlow<AlcometerUiState> = _uiState.asStateFlow()

    init {
        // Initial calculation with default values
        calculate()
    }

    fun onWeightChanged(newWeight: String) {
        // Allow numeric inputs including optional decimal separator
        val filtered = newWeight.filter { it.isDigit() || it == '.' || it == ',' }
        val isError = filtered.isNotEmpty() && filtered.toDoubleOrNull() == null && filtered.replace(',', '.').toDoubleOrNull() == null
        _uiState.update { currentState ->
            currentState.copy(
                weightInput = filtered,
                isWeightError = isError
            )
        }
    }

    fun onGenderChanged(gender: Gender) {
        _uiState.update { it.copy(gender = gender) }
    }

    fun onBottlesChanged(bottles: Int) {
        _uiState.update { it.copy(bottles = bottles) }
    }

    fun onHoursChanged(hours: Int) {
        _uiState.update { it.copy(hours = hours) }
    }

    fun calculate() {
        val currentState = _uiState.value
        val parsedWeight = currentState.weightInput.replace(',', '.').toDoubleOrNull() ?: 0.0

        if (parsedWeight <= 0.0) {
            _uiState.update { it.copy(result = 0.0, isWeightError = currentState.weightInput.isNotBlank()) }
            return
        }

        val resultValue = AlcometerCalculator.calculateBloodAlcohol(
            weightKg = parsedWeight,
            bottles = currentState.bottles,
            hours = currentState.hours,
            gender = currentState.gender
        )

        _uiState.update {
            it.copy(
                result = resultValue,
                isWeightError = false
            )
        }
    }
}
