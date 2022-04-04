package com.example.vrades.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vrades.R
import com.example.vrades.databinding.FragmentDetailsBinding
import com.example.vrades.enums.TestState
import com.example.vrades.viewmodels.DetailsViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.formatter.PercentFormatter


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

        val state = viewModel.getState()
        val pieData = viewModel.getData()
        val maxValueEmotion = viewModel.getMax()
        binding.apply {
            val pieChart = Chart
            val buttonBack = btnBackDetails
            val dominantEmotion = tvFinalResultDetails2
            pieData.setValueFormatter(PercentFormatter(pieChart))
            pieChart.data = pieData
            pieChart.invalidate()
            initPieChart(pieChart)
            buttonBack.setOnClickListener {
                if (state == TestState.WRITING_DETECTION_COMPLETED) {
                    findNavController().navigate(DetailsFragmentDirections.actionNavDetailsToNavHome())
                } else findNavController().navigate(DetailsFragmentDirections.actionNavDetailsToNavResults())
            }
            dominantEmotion.text = maxValueEmotion
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initPieChart(pieChart: PieChart) {
        pieChart.setUsePercentValues(true)
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.isRotationEnabled = true
        pieChart.dragDecelerationFrictionCoef = 0.9f
        pieChart.holeRadius = 1f
        pieChart.isDrawHoleEnabled = true
        pieChart.legend.form = Legend.LegendForm.NONE
        pieChart.isHighlightPerTapEnabled = true
        pieChart.animateX(1400, Easing.EaseInOutCirc)
        pieChart.setHoleColor(R.color.background_gradient_end)
        pieChart.legend.isEnabled = false
        pieChart.transparentCircleRadius = 1f
    }


}