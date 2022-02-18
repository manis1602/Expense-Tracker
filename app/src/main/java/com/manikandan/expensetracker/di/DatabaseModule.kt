package com.manikandan.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.manikandan.expensetracker.data.local.ExpenseTrackerDatabase
import com.manikandan.expensetracker.data.repository.LocalDataSourceImpl
import com.manikandan.expensetracker.domain.repository.LocalDataSource
import com.manikandan.expensetracker.utils.Constants.EXPENSE_TRACKER_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabaseInstance(@ApplicationContext context: Context): ExpenseTrackerDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseTrackerDatabase::class.java,
            EXPENSE_TRACKER_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(expenseTrackerDatabase: ExpenseTrackerDatabase): LocalDataSource {
        return LocalDataSourceImpl(expenseTrackerDatabase = expenseTrackerDatabase)
    }
}