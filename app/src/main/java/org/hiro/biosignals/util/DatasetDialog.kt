package org.hiro.biosignals.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import org.hiro.biosignals.databinding.DialogUserInputBinding


class DatasetDialog(context: Context, val save: (name: String) -> Unit) :
    AlertDialog.Builder(context) {

    init {
        val binding = DialogUserInputBinding.inflate(LayoutInflater.from(context))
        binding.nameInoutLayout.hint = "File name"
        setView(binding.root)
        val dialog = create()
        setCancelable(false)
        binding.positiveBtn.setOnClickListener {
            save(binding.nameInput.text.toString())
            dialog.dismiss()
        }
        binding.negativeBtn.visibility = View.GONE

        dialog.show()
    }

}

