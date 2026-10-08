package com.alanturing.tipcalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alanturing.tipcalculator.R
import com.alanturing.tipcalculator.ui.theme.TipCalculatorTheme


@Composable
fun TipCalculatorScreen(
    viewModel: TipCalculatorViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        innerPadding ->
        val columnModifier = Modifier
            .consumeWindowInsets(innerPadding)
            .padding(innerPadding)
        Column(
            modifier = columnModifier
        ) {
            val textFieldsModifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
            val customQuantityKeyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal
            )
            TextField(
                modifier = textFieldsModifier,
                value = state.amount,
                keyboardOptions = customQuantityKeyboardOptions,
                onValueChange = viewModel::onAmountChange
            )
            val customGuestKeyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
            TextField(
                value = state.guests,
                modifier = textFieldsModifier,
                keyboardOptions = customGuestKeyboardOptions,
                onValueChange = viewModel::onGuestsChange
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(stringResource(R.string.tipLabel))
                Switch(
                    checked = state.tip,
                    onCheckedChange = viewModel::changeTip
                )
            }
            Slider(
                enabled = state.tip,
                value = state.tipAmount,
                onValueChange = viewModel::onTipAmountChange,
                steps = 3,
                valueRange = 0f..4f

            )

            Button(
                enabled = state.isCalculateEnabled,
                modifier = Modifier.fillMaxWidth(),
                onClick = viewModel::calculateSplit
            ) {
                Icon(painterResource(R.drawable.icon_calculate_24),
                    contentDescription = "Calculate")
                Text("Calcular")
            }
            if (state.result.isNotBlank())
                Text(stringResource(R.string.splitLabel, state.result));
        }
    }
}
@Composable
@Preview
fun TipCalculatorScreenPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}