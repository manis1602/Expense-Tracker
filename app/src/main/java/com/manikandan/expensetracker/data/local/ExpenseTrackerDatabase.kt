package com.manikandan.expensetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manikandan.expensetracker.domain.model.SingleTransaction

@Database(entities = [SingleTransaction::class], version = 1)
abstract class ExpenseTrackerDatabase: RoomDatabase() {

    abstract fun userTransactionsDao(): UserTransactionsDao

}