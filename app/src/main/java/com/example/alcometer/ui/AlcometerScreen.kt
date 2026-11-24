package com.example.alcometer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcometer.model.Gender
import com.example.alcometer.ui.theme.AlcoPurple
import com.example.alcometer.ui.theme.AlcoTeal
import com.example.alcometer.ui.theme.AlcometerTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlcometerScreen(
    uiState: AlcometerUiState,
    onWeightChanged: (String) -> Unit,
    onGenderChanged: (Gender) -> Unit,
    onBottlesChanged: (Int) -> Unit,
    onHoursChanged: (Int) -> Unit,
    onCalculate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val bottleOptions = (1..20).toList()
    val hourOptions = (1..24).toList()

    var bottlesExpanded by remember { mutableStateOf(false) }
    var hoursExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Header Title
        Text(
            text = "Alcometer",
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            color = AlcoPurple,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Enter Weight Input
        OutlinedTextField(
            value = uiState.weightInput,
            onValueChange = onWeightChanged,
            label = { Text("Enter weight") },
            singleLine = true,
            isError = uiState.isWeightError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onCalculate()
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AlcoPurple,
                focusedLabelColor = AlcoPurple
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (uiState.isWeightError) {
            Text(
                text = "Please enter a valid weight in kg",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Gender Selection Radio Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        role = Role.RadioButton,
                        onClick = { onGenderChanged(Gender.MALE) }
                    )
            ) {
                RadioButton(
                    selected = uiState.gender == Gender.MALE,
                    onClick = { onGenderChanged(Gender.MALE) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AlcoTeal
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Male", fontSize = 16.sp)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        role = Role.RadioButton,
                        onClick = { onGenderChanged(Gender.FEMALE) }
                    )
            ) {
                RadioButton(
                    selected = uiState.gender == Gender.FEMALE,
                    onClick = { onGenderChanged(Gender.FEMALE) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AlcoTeal
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Female", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Select Bottles Dropdown
        ExposedDropdownMenuBox(
            expanded = bottlesExpanded,
            onExpandedChange = { bottlesExpanded = !bottlesExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = uiState.bottles.toString(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Select bottles") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = bottlesExpanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AlcoPurple,
                    focusedLabelColor = AlcoPurple
                ),
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = bottlesExpanded,
                onDismissRequest = { bottlesExpanded = false }
            ) {
                bottleOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.toString()) },
                        onClick = {
                            onBottlesChanged(option)
                            bottlesExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Select Hours Dropdown
        ExposedDropdownMenuBox(
            expanded = hoursExpanded,
            onExpandedChange = { hoursExpanded = !hoursExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = uiState.hours.toString(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Select hours") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = hoursExpanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AlcoPurple,
                    focusedLabelColor = AlcoPurple
                ),
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = hoursExpanded,
                onDismissRequest = { hoursExpanded = false }
            ) {
                hourOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.toString()) },
                        onClick = {
                            onHoursChanged(option)
                            hoursExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Result display text
        val formattedResult = String.format(Locale.getDefault(), "%.2f", uiState.result)
        Text(
            text = formattedResult,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = AlcoTeal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Calculate Button
        Button(
            onClick = {
                keyboardController?.hide()
                onCalculate()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = AlcoPurple,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "CALCULATE",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlcometerScreenMalePreview() {
    AlcometerTheme {
        AlcometerScreen(
            uiState = AlcometerUiState(
                weightInput = "89",
                gender = Gender.MALE,
                bottles = 3,
                hours = 1,
                result = 0.43
            ),
            onWeightChanged = {},
            onGenderChanged = {},
            onBottlesChanged = {},
            onHoursChanged = {},
            onCalculate = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlcometerScreenFemalePreview() {
    AlcometerTheme {
        AlcometerScreen(
            uiState = AlcometerUiState(
                weightInput = "89",
                gender = Gender.FEMALE,
                bottles = 3,
                hours = 1,
                result = 0.50
            ),
            onWeightChanged = {},
            onGenderChanged = {},
            onBottlesChanged = {},
            onHoursChanged = {},
            onCalculate = {}
        )
    }
}
