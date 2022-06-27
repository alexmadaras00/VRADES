package com.example.vrades.presentation.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vrades.databinding.DialogLoadingBinding
import com.example.vrades.databinding.FragmentSolutionsBinding
import com.example.vrades.domain.model.LifeHack
import com.example.vrades.domain.model.Response
import com.example.vrades.presentation.ui.adapters.AdapterLifeHacks
import com.example.vrades.presentation.ui.binding.setImageUrl
import com.example.vrades.presentation.utils.Constants
import com.example.vrades.presentation.viewmodels.MyProfileViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SolutionsFragment : Fragment() {

    private val viewModel: MyProfileViewModel by activityViewModels()
    private var _binding: FragmentSolutionsBinding? = null
    private val binding get() = _binding!!
    private var dialog: Dialog? = null
    private var dialogBinding: DialogLoadingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSolutionsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openDialog()
        getUser()
    }


    override fun onStart() {
        super.onStart()

        val buttonBack = binding.btnBackSolutions
        buttonBack.setOnClickListener {
            findNavController().navigate(SolutionsFragmentDirections.actionNavSolutionsToNavProfile())
        }
    }
    private fun getUser() {
        viewModel.getUser().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    binding.apply {
                        val textViewName = tvNameSolutions
                        val imageViewProfile = civProfilePictureSolutions
                        val textViewLastResult = tvResultSolutions
                        val user = response.data
                        val tests = user.tests
                        val lifeHacks = user.advices
                        ("Latest result: " + tests!!.last().result.uppercase()).also {
                            textViewLastResult.text = it
                        }
                        textViewName.text = user.username
                        setImageUrl(imageViewProfile, user.image)
                        getEmotions(tests.last().result)
                        if (lifeHacks?.isNotEmpty() == true) {
                            configureRecyclerView(user.advices!!)

                        }
                    }
                }
                is Response.Error -> {
                    println(response.message)
                }
                else -> {
                    println(Constants.ERROR_REF)
                }
            }
        }
    }

    private fun configureRecyclerView(lifeHacks: List<LifeHack>) {
        binding.apply {
            val recyclerViewLifeHacks = rvLifeHacks
            val adapterLifeHacks = AdapterLifeHacks()
            adapterLifeHacks.setDataSource(lifeHacks)
            recyclerViewLifeHacks.adapter = adapterLifeHacks
            recyclerViewLifeHacks.layoutManager = LinearLayoutManager(context)
            recyclerViewLifeHacks.setHasFixedSize(true)
            recyclerViewLifeHacks.hasNestedScrollingParent()
            dismissDialog() // closing the dialog
        }
    }

    private fun getEmotions(last: String) {
        viewModel.getEmotions().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    binding.apply {
                        val textViewReview = tvReviewText
                        textViewReview.text = it.data[last].toString()
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

    private fun openDialog() {
        dialog = Dialog(this.requireContext())
        dialogBinding = DialogLoadingBinding.inflate(LayoutInflater.from(context), null, false)
        dialog!!.setContentView(dialogBinding!!.root)
        dialog!!.show()
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setView(binding.root)
    }

    private fun dismissDialog() {
        if (dialog!!.isShowing)
            dialog!!.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}