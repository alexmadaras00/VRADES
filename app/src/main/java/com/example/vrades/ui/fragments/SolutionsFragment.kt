package com.example.vrades.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vrades.R
import com.example.vrades.databinding.FragmentDetailsBinding
import com.example.vrades.databinding.FragmentSolutionsBinding
import com.example.vrades.ui.adapters.AdapterLifeHacks
import com.example.vrades.viewmodels.SolutionsViewModel

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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        val lifeHacks = viewModel.getLifeHacks()
        binding.apply {
            val recyclerViewLifeHacks = rvLifeHacks
            val buttonBack = btnBackSolutions
            val adapterLifeHacks = AdapterLifeHacks()

            if (lifeHacks != null) {
                adapterLifeHacks.setDataSource(lifeHacks)
            }
            recyclerViewLifeHacks.adapter = adapterLifeHacks
            recyclerViewLifeHacks.layoutManager = LinearLayoutManager(context)
            recyclerViewLifeHacks.setHasFixedSize(true)
            recyclerViewLifeHacks.hasNestedScrollingParent()
            buttonBack.setOnClickListener{
                findNavController().navigate(SolutionsFragmentDirections.actionNavSolutionsToNavProfile())
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}