package com.manikandan.expensetracker.utils

sealed class ConnectionState{
    object Available: ConnectionState()
    object Unavailable: ConnectionState()
}
