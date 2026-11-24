package com.example.alcometer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.alcometer.ui.AlcometerScreen
import com.example.alcometer.ui.AlcometerViewModel
import com.example.alcometer.ui.theme.AlcometerTheme

class MainActivity : ComponentActivity() {

    private val viewModel: AlcometerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlcometerTheme {
                val uiState by viewModel.uiState.collectAsState()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AlcometerScreen(
                        uiState = uiState,
                        onWeightChanged = viewModel::onWeightChanged,
                        onGenderChanged = viewModel::onGenderChanged,
                        onBottlesChanged = viewModel::onBottlesChanged,
                        onHoursChanged = viewModel::onHoursChanged,
                        onCalculate = viewModel::calculate,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
