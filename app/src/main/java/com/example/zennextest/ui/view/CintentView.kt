package com.example.zennextest.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.example.zennextest.R
import com.example.zennextest.databinding.GenericDialogBinding
import com.example.zennextest.ui.exception.UIExceptionMapper
import com.example.zennextest.ui.extension.onClick
import com.example.zennextest.ui.extension.show
import com.example.zennextest.ui.extension.showIf
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ContentView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    fun showErrorDialog(
        exception: Exception?,
        closeAction: (() -> Unit)? = null,
        retryAction: (() -> Unit)? = null,
        isDialogCancellable: Boolean = true,
        isCloseVisible: Boolean = true,
    ) {
        showGenericErrorDialog(
            context = context,
            exception = exception,
            closeButtonTextRes = R.string.cancel,
            closeAction = closeAction,
            retryAction = retryAction,
            isDialogCancellable = isDialogCancellable,
            isCloseVisible = isCloseVisible
        )
    }

    private fun showGenericErrorDialog(
        context: Context,
        exception: Exception?,
        @StringRes closeButtonTextRes: Int = R.string.cancel,
        closeAction: (() -> Unit)? = null,
        retryAction: (() -> Unit)? = null,
        isCloseVisible: Boolean = true,
        isDialogCancellable: Boolean = true
    ) {
        val mapper = UIExceptionMapper()
        val builder = MaterialAlertDialogBuilder(
            context,
            R.style.CustomAlertDialog
        ).setCancelable(isDialogCancellable)

        val dialogBinding = GenericDialogBinding.inflate(
            LayoutInflater.from(
                context
            )
        )
        builder.setView(dialogBinding.root)
        val alertDialog = builder.create()

        with(dialogBinding) {
            mapper.titleMapper(context, exception).let { title ->
                tvTitle.showIf { title.isNotEmpty() }
                tvTitle.text = title
            }

            mapper.subtitleMapper(context, exception).let { subtitle ->
                tvSubtitle.showIf { subtitle.isNotEmpty() }
                tvSubtitle.text = subtitle
            }

            btnCancel.showIf { isCloseVisible }
            divider.showIf { isCloseVisible }
            btnCancel.text =
                context.resources.getString(closeButtonTextRes)
            btnCancel.onClick {
                alertDialog.dismiss()
                closeAction?.invoke()
            }

            btnOk.show()
            btnOk.text = context.resources.getString(R.string.ok)
            btnOk.onClick {
                alertDialog.dismiss()
                retryAction?.invoke()
            }
        }

        alertDialog.setOnDismissListener {
            if (isDialogCancellable) closeAction?.invoke()
        }

        alertDialog.show()
    }
}
