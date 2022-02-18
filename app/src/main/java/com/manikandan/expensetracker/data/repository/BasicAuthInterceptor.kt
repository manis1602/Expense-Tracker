package com.manikandan.expensetracker.data.repository

import com.manikandan.expensetracker.domain.repository.DataStoreOperations
import kotlinx.coroutines.*
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(
    private val dataStoreOperations: DataStoreOperations
) : Interceptor {
    private val scope = CoroutineScope(CoroutineName("Custom_Coroutine_Scope") + Dispatchers.IO)

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val userCredentials = runBlocking {
            withContext(scope.coroutineContext) {
                val user = dataStoreOperations.readUserCredentials()
                Credentials.basic(user.emailAddress, user.password)
            }
        }
        request = request.newBuilder().header("Authorization", userCredentials).build()
        return chain.proceed(request)
    }
}
