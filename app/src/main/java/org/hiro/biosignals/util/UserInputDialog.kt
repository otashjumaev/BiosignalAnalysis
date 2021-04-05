package org.hiro.biosignals.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import org.hiro.biosignals.databinding.DialogUserInputBinding


class UserInputDialog(context: Context, val save: (name: String) -> Unit) :
    AlertDialog.Builder(context) {

    init {
        val binding = DialogUserInputBinding.inflate(LayoutInflater.from(context))
        binding.nameInoutLayout.hint = "User name"
        setView(binding.root)

        val dialog = create()

        binding.positiveBtn.setOnClickListener {
            save(binding.nameInput.text.toString())
            dialog.dismiss()
        }
        binding.negativeBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}

