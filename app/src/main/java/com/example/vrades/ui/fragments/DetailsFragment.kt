package com.example.vrades.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anychart.AnyChart
import com.example.vrades.R
import com.example.vrades.databinding.FragmentDetailsBinding
import com.example.vrades.databinding.FragmentResultsBinding
import com.example.vrades.enums.TestState
import com.example.vrades.viewmodels.DetailsViewModel
import com.example.vrades.viewmodels.ResultsViewModel
import com.example.vrades.viewmodels.SolutionsViewModel


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        _binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val pie = AnyChart.pie()
        val chart = viewModel.getPieChart()
        val state = viewModel.getState()
        binding.apply {
            val imageChart = Chart
            val buttonBack = btnBackDetails
            imageChart.setChart(chart)
            buttonBack.setOnClickListener {
                if (state == TestState.WRITING_DETECTION_COMPLETED) {
                    findNavController().navigate(DetailsFragmentDirections.actionNavDetailsToNavHome())
                } else findNavController().navigate(DetailsFragmentDirections.actionNavDetailsToNavResults())
            }

        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}