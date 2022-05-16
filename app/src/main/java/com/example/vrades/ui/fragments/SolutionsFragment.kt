package com.example.vrades.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vrades.databinding.FragmentSolutionsBinding
import com.example.vrades.model.LifeHack
import com.example.vrades.model.Response
import com.example.vrades.ui.adapters.AdapterLifeHacks
import com.example.vrades.ui.binding.setImageUrl
import com.example.vrades.utils.Constants
import com.example.vrades.viewmodels.MyProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SolutionsFragment : Fragment() {

    private val viewModel: MyProfileViewModel by activityViewModels()
    private var _binding: FragmentSolutionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSolutionsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        getUser()
        val buttonBack = binding.btnBackSolutions
        buttonBack.setOnClickListener {
            findNavController().navigate(SolutionsFragmentDirections.actionNavSolutionsToNavProfile())
        }
    }
    private fun getUser() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    binding.apply {
                        val textViewName = tvNameSolutions
                        val imageViewProfile = civProfilePictureSolutions
                        val textViewLastResult = tvResultSolutions
                        val textViewReview = tvReviewText
                        val user = it.data
                        val tests = user.tests
                        val advices = user.advices
                        textViewLastResult.text = "Latest result " + tests!!.last().toString()
                        textViewName.text = user.username
                        setImageUrl(imageViewProfile, user.image)
                        getEmotions(tests.last().result)
                        configureRecyclerView(user.advices!!)
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

    private fun configureRecyclerView(lifeHacks: List<LifeHack>) {
        binding.apply {
            val recyclerViewLifeHacks = rvLifeHacks
            val adapterLifeHacks = AdapterLifeHacks()
            adapterLifeHacks.setDataSource(lifeHacks)
            recyclerViewLifeHacks.adapter = adapterLifeHacks
            recyclerViewLifeHacks.layoutManager = LinearLayoutManager(context)
            recyclerViewLifeHacks.setHasFixedSize(true)
            recyclerViewLifeHacks.hasNestedScrollingParent()

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}