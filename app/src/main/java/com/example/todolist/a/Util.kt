package com.example.todolist.a

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.example.todolist.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun Activity.showErrorDialog(text: String?) {
    AlertDialog.Builder(this).apply {
        setTitle(R.string.error_title_text)
        setMessage(text.orEmpty())
        setNeutralButton(R.string.error_button_text) { dialog, _ ->
            dialog.dismiss()
        }
    }.run {
        create()
        show()
    }
}



sealed class Either<out L, out R> {
    companion object {
        inline fun <R> of(action: () -> R): Either<Exception, R> {
            return try {
                Right(action())
            } catch (ex: Exception) {
                Left(ex)
            }
        }
    }
}

data class Right<out R>(val value: R) : Either<Nothing, R>()
data class Left<out L>(val value: L) : Either<L, Nothing>()

suspend fun <T> safeRequest(request: suspend () -> T): Either<Exception, T> =
    Either.of { withContext(Dispatchers.IO) { request() } }