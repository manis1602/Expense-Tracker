package com.manikandan.expensetracker.domain.model

sealed class UiEvents{
    data class ShowSnackBar(val message: String): UiEvents()
}
