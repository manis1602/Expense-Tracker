package com.manikandan.expensetracker.presentation.common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.manikandan.expensetracker.utils.ConnectionState
import com.manikandan.expensetracker.utils.currentConnectivityState
import com.manikandan.expensetracker.utils.observeConnectivityStateAsFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    return produceState(initialValue = context.currentConnectivityState){
        context.observeConnectivityStateAsFlow().collect {
            value = it
        }
    }

}