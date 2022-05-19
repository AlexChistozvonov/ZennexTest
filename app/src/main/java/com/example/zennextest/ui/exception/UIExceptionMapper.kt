package com.example.zennextest.ui.exception

import android.content.Context
import com.example.zennextest.R

@Suppress("UnusedPrivateMember", "UNUSED_EXPRESSION")
class UIExceptionMapper {
    fun titleMapper(context: Context): String {
        return context.resources.getString(R.string.error_network)
    }

    fun subtitleMapper(context: Context): String {
        return context.resources.getString(R.string.error_smt_go_wrong_subtitle)
    }
}

