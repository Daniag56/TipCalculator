package com.alanturing.tipcalculator.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TipCalculatorViewModel(): ViewModel() {

    private val _state = MutableStateFlow(TipCalculatorScreenState())
    val state: StateFlow<TipCalculatorScreenState>
        get() = _state.asStateFlow()

    fun calculateSplit() {
        val currentState = _state.value
        val amount = currentState.amount.toDoubleOrNull() ?: 0.0
        val guests = currentState.guests.toIntOrNull() ?: 0

        if (amount <= 0 || guests <= 0) return

        val tipMultiplier = when (currentState.tipAmount) {
            1f -> 1.05
            2f -> 1.10
            3f -> 1.15
            4f -> 1.20
            else -> 1.0
        }

        val totalWithTip = amount * tipMultiplier
        val splitResult = totalWithTip / guests

        _state.value = currentState.copy(
            result = String.format("%.2f", splitResult)
        )
    }

    fun changeTip(isEnabled: Boolean) {
        val oldState = _state.value
        _state.value = oldState.copy(
            tip = isEnabled
        )
    }

    fun onAmountChange(newAmount: String) {
        val oldState = _state.value
        _state.value = oldState.copy(
            amount = newAmount,
            isCalculateEnabled = newAmount.toDoubleOrNull()?.let { it > 0 } == true && oldState.guests.toIntOrNull()?.let { it > 0 } == true
        )
    }

    fun onGuestsChange(newGuests: String) {
        val oldState = _state.value
        _state.value = oldState.copy(
            guests = newGuests,
            isCalculateEnabled = newGuests.toIntOrNull()?.let { it > 0 } == true && oldState.amount.toDoubleOrNull()?.let { it > 0 } == true
        )
    }

    fun onTipAmountChange(newTipAmount: Float) {
        val oldState = _state.value
        _state.value = oldState.copy(
            tipAmount = newTipAmount
        )
    }
}

data class TipCalculatorScreenState(
    val guests: String = "0",
    val amount: String = "0.00",
    val tip: Boolean = false,
    val tipAmount: Float = 0.0F,
    val result: String = "",
    val isCalculateEnabled: Boolean = false
)