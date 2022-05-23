package ru.internetcloud.twodatasources.presentation.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.internetcloud.twodatasources.R

class MessageDialogFragment : DialogFragment() {

    companion object {

        private const val MESSAGE_ARG = "message_arg"
        private const val TITLE_ARG = "title_arg"

        fun newInstance(title: String, message: String): MessageDialogFragment {
            val args = Bundle().apply {
                putString(TITLE_ARG, title)
                putString(MESSAGE_ARG, message)
            }
            return MessageDialogFragment().apply {
                arguments = args
            }
        }
    }

    private var title: String = ""
    private var message: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        arguments?.let { arg ->
            title = arg.getString(TITLE_ARG, "")
            message = arg.getString(MESSAGE_ARG, "")
        } ?: run {
            throw RuntimeException("There are not arguments in MessageDialogFragment")
        }

        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setMessage(message)
        if (title.isNotEmpty()) {
            alertDialogBuilder.setTitle(title)
        }

        alertDialogBuilder.setPositiveButton(R.string.ok_button, null)

        return alertDialogBuilder.create()
    }
}
