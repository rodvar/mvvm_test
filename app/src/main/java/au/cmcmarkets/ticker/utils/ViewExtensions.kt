package au.cmcmarkets.ticker.utils

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.databinding.BindingAdapter

fun Context?.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration).show()

fun View?.hideKeyboard() {
    //Find the currently focused view, so we can grab the correct window token from it.
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    this?.also { view ->
        context.getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

@BindingAdapter("textPrice")
fun TextView?.setTextPrice(text: String?) {
    try {
        text?.toBigDecimal()?.let {
            this?.text = Html.fromHtml(
                "<b>" + it.toBigInteger() + "</b>" + "<small>." +
                        it.subtract(it.toBigInteger().toBigDecimal()).multiply(100.toBigDecimal())
                            .toBigInteger() + "</small>"
            )
        }
    } catch (e: Exception) {
        Log.e("text price", "Failed to set text price", e)
        this?.text = text
    }
}