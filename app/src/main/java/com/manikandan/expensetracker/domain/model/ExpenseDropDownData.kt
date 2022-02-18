package com.manikandan.expensetracker.domain.model

import com.manikandan.expensetracker.presentation.common.util.ExpenseDropDownType

data class ExpenseDropDownData(
    val dropDownType: ExpenseDropDownType,
    val dropDownList: List<String>,
    val dropDownTitle: String,
    val selectedText: String,
    val hint: String
)