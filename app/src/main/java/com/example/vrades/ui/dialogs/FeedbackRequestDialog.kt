package com.example.vrades.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.vrades.R
import com.example.vrades.databinding.DialogFeedbackRequestBinding
import com.example.vrades.databinding.FragmentMyProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackRequestDialog: DialogFragment() {
    private var _binding: DialogFeedbackRequestBinding? = null
    private val binding get() = _binding!!
    private lateinit var builder: AlertDialog.Builder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFeedbackRequestBinding.inflate(layoutInflater.cloneInContext(context))
        builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        return builder.create()
        }

    override fun onStart() {
        super.onStart()
        val navController = findNavController()
        binding.apply {
            val buttonYes = btnYes
            val buttonNo = btnNo
            buttonYes.setOnClickListener {
                navController.navigate(FeedbackRequestDialogDirections.actionNavDialogToNavSolutions())
            }
            buttonNo.setOnClickListener {
                dialog!!.dismiss()
            }
        }
    }

}