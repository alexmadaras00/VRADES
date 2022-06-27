package com.example.vrades.presentation.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.vrades.databinding.DialogFeedbackRequestBinding
import com.example.vrades.databinding.DialogLoadingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingDialog: DialogFragment() {
    private var _binding: DialogLoadingBinding? = null
    private val binding get() = _binding!!
    private lateinit var builder: MaterialAlertDialogBuilder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogLoadingBinding.inflate(layoutInflater.cloneInContext(context))
        builder = MaterialAlertDialogBuilder(requireContext())
        builder.setView(binding.root)
        return builder.create()
    }
}