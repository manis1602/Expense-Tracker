package com.manikandan.expensetracker.utils

import com.manikandan.expensetracker.domain.model.ErrorData

sealed class NetworkResult<T>(val data: T? = null, val errorData: ErrorData? = null){
    class Success<T>(data: T): NetworkResult<T>(data = data)
    class Error<T>(data: T? = null, errorData: ErrorData): NetworkResult<T>(data = data, errorData = errorData)
//    class Loading<T>(data: T? = null): NetworkResult<T>(data = data)
}