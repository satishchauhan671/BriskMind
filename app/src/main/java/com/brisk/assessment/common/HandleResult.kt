package com.brisk.assessment.common

sealed class HandleResult<T>(val data: T? = null) {
    class Insert<T>(data: T) : HandleResult<T>(data)

}
