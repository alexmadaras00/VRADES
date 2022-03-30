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
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
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

}