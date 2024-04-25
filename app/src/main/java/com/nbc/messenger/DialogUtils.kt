package com.nbc.messenger

import android.content.Context
import androidx.appcompat.app.AlertDialog

internal fun showNumberSelectionDialog(context: Context, onNumberSelected: (Int) -> Unit) {
    val numbers = arrayOf("1", "2", "3")

    AlertDialog.Builder(context)
        .setTitle("몇 분 뒤에 문자를 보낼까요?")
        .setItems(numbers) { _, which ->
            val selectedNumber = numbers[which].toInt()
            onNumberSelected(selectedNumber)
        }
        .show()

}