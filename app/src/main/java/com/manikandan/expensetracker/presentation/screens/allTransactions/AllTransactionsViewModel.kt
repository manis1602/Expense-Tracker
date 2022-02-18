package com.manikandan.expensetracker.presentation.screens.allTransactions

import androidx.lifecycle.ViewModel
import com.manikandan.expensetracker.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllTransactionsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
}