package com.manikandan.expensetracker.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

val Context.currentConnectivityState: ConnectionState
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager = connectivityManager)
    }

private fun getCurrentConnectivityState(connectivityManager: ConnectivityManager): ConnectionState {
    val isConnected = connectivityManager.allNetworks.any { network ->
        connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

    return if (isConnected) ConnectionState.Available else ConnectionState.Unavailable
}

@ExperimentalCoroutinesApi
fun Context.observeConnectivityStateAsFlow() = callbackFlow {

    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callBack = networkCallback { connectionState ->
        trySend(connectionState)
    }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callBack)

    val currentConnectionState = getCurrentConnectivityState(connectivityManager = connectivityManager)
    trySend(currentConnectionState)

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callBack)
    }

}

fun networkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            callback(
                ConnectionState.Unavailable
            )
        }
    }
}
