package com.manikandan.expensetracker.domain.usecases

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

data class UseCases(
    val saveOnBoardingStateUseCase: SaveOnBoardingStateUseCase,
    val readOnBoardingStateUseCase: ReadOnBoardingStateUseCase,
    val saveDataStoreUserCredentialsUseCase: SaveDataStoreUserCredentialsUseCase,
    val readDataStoreUserCredentialsUseCase: ReadDataStoreUserCredentialsUseCase,
    val saveLoginStateUseCase: SaveLoginStateUseCase,
    val readLoginStateUseCase: ReadLoginStateUseCase,
    val loginUserUseCase: LoginUserUseCase,
    val registerUserUseCase: RegisterUserUseCase,
    val resetPasswordUseCase: ResetPasswordUseCase,
    val getUsersAllTransactionsUseCase: GetUsersAllTransactionsUseCase,
    val addTransactionUseCase: AddTransactionUseCase,
    val updateTransactionUseCase: UpdateTransactionUseCase,
    val deleteTransactionUseCase: DeleteTransactionUseCase
)
