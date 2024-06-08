package com.example.todolist.ui.alertDialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

const val TAG = "onCreateDialog"

class NewProjectDialogFragment : DialogFragment() {

    private var listener:DialogListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement DialogListener")
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Create new project!")

            val customLayout: View =
                layoutInflater.inflate(com.example.todolist.R.layout.alert_dialog_new_project, null)
            builder.setView(customLayout)
            val editText =
                customLayout.findViewById<EditText>(com.example.todolist.R.id.editTextProjectName)

            builder.setPositiveButton("Save new project") { dialog, id ->
                dialog.cancel()

                if (editText.text.isEmpty()) {
                    Toast.makeText(context, "Fill in neccesary input field", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val data = editText.text.toString()
                    listener?.onFinishDialog(data)

                }


            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }
}