package com.manikandan.expensetracker.presentation.screens.home.util

import com.manikandan.expensetracker.domain.model.SingleTransaction

class DateComparator : Comparator<SingleTransaction> {
    override fun compare(transaction1: SingleTransaction?, transaction2: SingleTransaction?): Int {
        if (transaction1 == null || transaction2 == null) {
            return 0
        }
        /*
        For ascending
        return exp1.date.compareTo(exp2.date)
         */
        // For descending
        return transaction2.transactionDate.compareTo(transaction1.transactionDate)
    }

}