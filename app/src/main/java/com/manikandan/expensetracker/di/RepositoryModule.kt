package com.manikandan.expensetracker.di

import android.content.Context
import com.manikandan.expensetracker.data.repository.DataStoreOperationsImpl
import com.manikandan.expensetracker.data.repository.RepositoryImpl
import com.manikandan.expensetracker.domain.repository.DataStoreOperations
import com.manikandan.expensetracker.domain.repository.LocalDataSource
import com.manikandan.expensetracker.domain.repository.RemoteDataSource
import com.manikandan.expensetracker.domain.repository.Repository
import com.manikandan.expensetracker.domain.usecases.UseCases
import com.manikandan.expensetracker.domain.usecases.add_transaction.AddTransactionUseCase
import com.manikandan.expensetracker.domain.usecases.delete_transaction.DeleteTransactionUseCase
import com.manikandan.expensetracker.domain.usecases.get_users_all_transactions.GetUsersAllTransactionsUseCase
import com.manikandan.expensetracker.domain.usecases.login_user.LoginUserUseCase
import com.manikandan.expensetracker.domain.usecases.read_datastore_user_credentials.ReadDataStoreUserCredentialsUseCase
import com.manikandan.expensetracker.domain.usecases.read_login_state.ReadLoginStateUseCase
import com.manikandan.expensetracker.domain.usecases.read_on_boarding_state.ReadOnBoardingStateUseCase
import com.manikandan.expensetracker.domain.usecases.register_user.RegisterUserUseCase
import com.manikandan.expensetracker.domain.usecases.reset_password.ResetPasswordUseCase
import com.manikandan.expensetracker.domain.usecases.save_datatore_user_credentials.SaveDataStoreUserCredentialsUseCase
import com.manikandan.expensetracker.domain.usecases.save_login_state.SaveLoginStateUseCase
import com.manikandan.expensetracker.domain.usecases.save_on_boarding_state.SaveOnBoardingStateUseCase
import com.manikandan.expensetracker.domain.usecases.update_transaction.UpdateTransactionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(@ApplicationContext context: Context): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        dataStoreOperations: DataStoreOperations
    ): Repository {
        return RepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            dataStoreOperations = dataStoreOperations
        )
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            readOnBoardingStateUseCase = ReadOnBoardingStateUseCase(repository = repository),
            saveOnBoardingStateUseCase = SaveOnBoardingStateUseCase(repository = repository),
            readDataStoreUserCredentialsUseCase = ReadDataStoreUserCredentialsUseCase(repository = repository),
            saveDataStoreUserCredentialsUseCase = SaveDataStoreUserCredentialsUseCase(repository = repository),
            readLoginStateUseCase = ReadLoginStateUseCase(repository = repository),
            saveLoginStateUseCase = SaveLoginStateUseCase(repository = repository),
            loginUserUseCase = LoginUserUseCase(repository = repository),
            registerUserUseCase = RegisterUserUseCase(repository = repository),
            resetPasswordUseCase = ResetPasswordUseCase(repository = repository),
            getUsersAllTransactionsUseCase = GetUsersAllTransactionsUseCase(repository = repository),
            addTransactionUseCase = AddTransactionUseCase(repository = repository),
            updateTransactionUseCase = UpdateTransactionUseCase(repository = repository),
            deleteTransactionUseCase = DeleteTransactionUseCase(repository = repository)
        )
    }
}
