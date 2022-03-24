package com.example.vrades.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.example.vrades.R
import com.example.vrades.databinding.FragmentDetailsBinding
import com.example.vrades.databinding.FragmentResultsBinding
import com.example.vrades.viewmodels.DetailsViewModel
import com.example.vrades.viewmodels.ResultsViewModel
import com.example.vrades.viewmodels.SolutionsViewModel


class DetailsFragment : Fragment() {

    private val _binding: FragmentDetailsBinding? = null
    var binding = _binding!!
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        val pie = AnyChart.pie()
        val chart = viewModel.getPieChart()
        binding.Chart.setChart(chart)
    }

}