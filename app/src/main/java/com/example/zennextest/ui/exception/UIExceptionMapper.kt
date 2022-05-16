package com.example.zennextest.ui.exception

import android.content.Context
import com.example.zennextest.core.GeneralException
import com.example.zennextest.core.NetworkException
import com.example.zennextest.core.ServerException
import com.example.zennextest.core.TimeoutException
import com.example.zennextest.R

@Suppress("UnusedPrivateMember", "UNUSED_EXPRESSION")
class UIExceptionMapper {
    fun titleMapper(context: Context, throwable: Throwable?): String {
        return when (throwable) {
            is NetworkException -> context.resources.getString(R.string.error_network)
            is ServerException -> context.resources.getString(R.string.error_server)
            is TimeoutException -> context.resources.getString(R.string.error_smt_go_wrong)
            is GeneralException -> throwable.message.toString()
            else -> context.resources.getString(R.string.error_smt_go_wrong)
        }
    }

    fun subtitleMapper(context: Context, throwable: Throwable?): String {
        return when (throwable) {
            is NetworkException -> context.resources.getString(R.string.error_network_subtitle)
            is ServerException -> context.resources.getString(R.string.error_server_subtitle)
            is TimeoutException -> context.resources.getString(R.string.error_smt_go_wrong_subtitle)
            is GeneralException -> ""
            else -> context.resources.getString(R.string.error_smt_go_wrong_subtitle)
        }
    }
}
