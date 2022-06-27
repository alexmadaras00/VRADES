package com.example.vrades.presentation.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentResultsBinding
import com.example.vrades.domain.model.Response
import com.example.vrades.presentation.ui.binding.setImageUrl
import com.example.vrades.presentation.utils.Constants
import com.example.vrades.presentation.viewmodels.ResultsViewModel
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
        getUser()
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

    private fun getUser() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    binding.apply {
                        val imageViewProfile = civProfilePictureResults
                        val textViewName = tvNameResults
                        val user = it.data
                        textViewName.text = user.username
                        setImageUrl(imageViewProfile, user.image)
                    }
                }
                is Response.Error -> {
                    println(Constants.ERROR_REF)
                }
                else -> {
                    println(Constants.ERROR_REF)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}