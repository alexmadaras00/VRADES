package com.example.vrades.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vrades.R
import com.example.vrades.databinding.FragmentResultsBinding
import com.example.vrades.databinding.FragmentSettingsBinding
import com.example.vrades.viewmodels.ResultsViewModel
import com.example.vrades.viewmodels.SettingsViewModel
import com.example.vrades.viewmodels.SolutionsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : DialogFragment() {

    private lateinit var viewModel: ResultsViewModel
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ResultsViewModel::class.java]
        _binding = FragmentResultsBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.apply{
            val buttonBack = btnBackResults
            val buttonDetails = btnDetailedInfo
            val navController = findNavController()
            buttonDetails.setOnClickListener{
                navController.navigate(ResultsFragmentDirections.actionNavResultsToNavDetails())
            }
            buttonBack.setOnClickListener {
                navController.navigate(ResultsFragmentDirections.actionNavResultsToNavProfile())
            }
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}