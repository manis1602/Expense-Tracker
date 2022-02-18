package com.manikandan.expensetracker.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.manikandan.expensetracker.data.remote.ExpenseTrackerUserApi
import com.manikandan.expensetracker.data.remote.ExpenseTrackerUserTransactionApi
import com.manikandan.expensetracker.data.repository.BasicAuthInterceptor
import com.manikandan.expensetracker.data.repository.RemoteDataSourceImpl
import com.manikandan.expensetracker.domain.repository.DataStoreOperations
import com.manikandan.expensetracker.domain.repository.RemoteDataSource
import com.manikandan.expensetracker.utils.Constants.BASE_URL
import com.manikandan.expensetracker.utils.Constants.OK_HTTP_CLIENT_BASIC
import com.manikandan.expensetracker.utils.Constants.OK_HTTP_CLIENT_WITH_BASIC_AUTH_INTERCEPTED
import com.manikandan.expensetracker.utils.Constants.RETROFIT_INSTANCE_FOR_USER_API
import com.manikandan.expensetracker.utils.Constants.RETROFIT_INSTANCE_FOR_USER_TRANSACTION_API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    private val contentType = MediaType.get("application/json")
    private val jsonConverterFactory = Json.asConverterFactory(contentType = contentType)

    @Named(OK_HTTP_CLIENT_BASIC)
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Named(OK_HTTP_CLIENT_WITH_BASIC_AUTH_INTERCEPTED)
    @Provides
    @Singleton
    fun provideOkHttpClientWithBasicAuthIntercepted(dataStoreOperations: DataStoreOperations): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(BasicAuthInterceptor(dataStoreOperations = dataStoreOperations))
            .build()
    }

    @Named(RETROFIT_INSTANCE_FOR_USER_API)
    @Provides
    @Singleton
    fun provideRetrofitInstance(@Named(OK_HTTP_CLIENT_BASIC) okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jsonConverterFactory)
            .build()

    }

    @Named(RETROFIT_INSTANCE_FOR_USER_TRANSACTION_API)
    @Provides
    @Singleton
    fun provideRetrofitInstanceWithInterceptor(
        @Named(
            OK_HTTP_CLIENT_WITH_BASIC_AUTH_INTERCEPTED
        ) okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jsonConverterFactory)
            .build()

    }

    @Provides
    @Singleton
    fun provideExpenseTrackerUserApi(
        @Named(
            RETROFIT_INSTANCE_FOR_USER_API
        ) retrofit: Retrofit
    ): ExpenseTrackerUserApi {
        return retrofit.create(ExpenseTrackerUserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideExpenseTrackerUserTransactionApi(
        @Named(RETROFIT_INSTANCE_FOR_USER_TRANSACTION_API) retrofit: Retrofit
    ): ExpenseTrackerUserTransactionApi {
        return retrofit.create(ExpenseTrackerUserTransactionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        expenseTrackerUserApi: ExpenseTrackerUserApi,
        expenseTrackerUserTransactionApi: ExpenseTrackerUserTransactionApi
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            expenseTrackerUserApi = expenseTrackerUserApi,
            expenseTrackerUserTransactionApi = expenseTrackerUserTransactionApi
        )
    }
}
