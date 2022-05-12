package com.example.vrades.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vrades.databinding.FragmentSolutionsBinding
import com.example.vrades.model.Response
import com.example.vrades.ui.adapters.AdapterLifeHacks
import com.example.vrades.ui.binding.setImageUrl
import com.example.vrades.utils.Constants
import com.example.vrades.viewmodels.SolutionsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SolutionsFragment : Fragment() {

    private lateinit var viewModel: SolutionsViewModel
    private var _binding: FragmentSolutionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SolutionsViewModel::class.java]
        _binding = FragmentSolutionsBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        getUser()
        val lifeHacks = viewModel.getLifeHacks()
        binding.apply {
            val recyclerViewLifeHacks = rvLifeHacks
            val buttonBack = btnBackSolutions
            val adapterLifeHacks = AdapterLifeHacks()
            adapterLifeHacks.setDataSource(lifeHacks!!)
            recyclerViewLifeHacks.adapter = adapterLifeHacks
            recyclerViewLifeHacks.layoutManager = LinearLayoutManager(context)
            recyclerViewLifeHacks.setHasFixedSize(true)
            recyclerViewLifeHacks.hasNestedScrollingParent()
            buttonBack.setOnClickListener{
                findNavController().navigate(SolutionsFragmentDirections.actionNavSolutionsToNavProfile())
            }

        }
    }
    private fun getUser() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    binding.apply {
                        val textViewName = tvNameSolutions
                        val imageViewProfile = civProfilePictureDetails
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