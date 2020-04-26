package com.dimirim.minorhobby.ui.custom

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.dimirim.minorhobby.R
import kotlinx.android.synthetic.main.dialog_basic.*

class CupertinoDialog(val context: Context) {
    fun show(title: String, content: String) {
        val dialog: Dialog = Dialog(context, R.style.CupertinoDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_basic)
        dialog.show()

        dialog.text_title.text = title
        dialog.text_content.text = content
        dialog.button_confirm.setOnClickListener {
            dialog.dismiss()
        }
        dialog.layout_root.setOnClickListener {
            dialog.dismiss()
        }
    }
}
