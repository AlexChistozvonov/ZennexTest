package com.example.zennextest.ui.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.zennextest.ui.util.SafeClickListener
import com.example.zennextest.R
import com.example.zennextest.databinding.GenericDialogBinding
import com.example.zennextest.ui.exception.UIExceptionMapper
import com.google.android.material.dialog.MaterialAlertDialogBuilder


inline fun <T : View> T.showIf(condition: (T) -> Boolean): T {
    if (condition(this)) {
        show()
    } else {
        hide()
    }
    return this
}

fun View.show() {
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.hide() {
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}

fun View.onClick(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun Fragment.showGeneralErrorDialog(
    context: Context,
    closeAction: (() -> Unit)? = null,
    submitAction: (() -> Unit)? = null,
    @StringRes submitButtonTextRes: Int = R.string.ok,
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
        mapper.titleMapper(context).let { title ->
            tvTitle.showIf { title.isNotEmpty() }
            tvTitle.text = title
        }

        mapper.subtitleMapper(context).let { subTitle ->
            tvSubtitle.showIf { subTitle.isNotEmpty() }
            tvSubtitle.text = subTitle
        }

        btnOk.show()
        btnOk.text = context.resources.getString(submitButtonTextRes)
        btnOk.onClick {
            alertDialog.dismiss()
            submitAction?.invoke()
        }
    }

    alertDialog.setOnDismissListener {
        if (isDialogCancellable) closeAction?.invoke()
    }

    alertDialog.show()
}
