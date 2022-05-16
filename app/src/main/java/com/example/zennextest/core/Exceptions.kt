package com.example.zennextest.core

open class TestAppZennexException : Exception()

class NetworkException : TestAppZennexException()
class TimeoutException : TestAppZennexException()
class GeneralException(val value: String? = "", val code: Int? = null) : TestAppZennexException()
class ServerException(val errorCode: Int? = null) : TestAppZennexException()
