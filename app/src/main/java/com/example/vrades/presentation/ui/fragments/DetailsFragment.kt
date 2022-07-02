package com.example.vrades.presentation.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vrades.R
import com.example.vrades.databinding.DialogLoadingBinding
import com.example.vrades.databinding.FragmentDetailsBinding
import com.example.vrades.domain.model.Response
import com.example.vrades.presentation.ui.binding.setImageUrl
import com.example.vrades.presentation.utils.Constants
import com.example.vrades.presentation.viewmodels.TestViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TestViewModel by activityViewModels()
    private var dialog: Dialog? = null
    private var dialogBinding: DialogLoadingBinding? = null
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        dialog = Dialog(this.requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openDialog()
        getUser()
        getTestResults()
    }

    private fun getTestResults() {
        val date = args.dateTest
        viewModel.getTestByDate(date).observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    binding.apply {
                        println("FOUND TEST: ${it.data}")
                        val pieChart = Chart
                        val pieData = viewModel?.getData(it.data)
                        pieData?.setValueFormatter(PercentFormatter(pieChart))
                        pieChart.invalidate()
                        pieChart.data = pieData
                        initPieChart(pieChart)
                        var finalResult = ""
                        val maxValue = it.data.maxOf { result ->
                            result.value
                        }
                        it.data.forEach { item ->
                            if (item.value == maxValue) {
                                finalResult = item.key
                            }
                        }
                        val textViewResult = binding.tvFinalResultDetails2
                        textViewResult.text = finalResult.uppercase()
                    }
                }
                is Error -> {

                }
                else -> {

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        binding.apply {
            val buttonBack = btnBackDetails
            buttonBack.setOnClickListener {
                findNavController().navigate(DetailsFragmentDirections.actionNavDetailsToNavProfile())
            }

        }
        onHandleBackButton()

    }

    private fun openDialog() {
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

    private fun onHandleBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {

        }

    }

    private fun initPieChart(pieChart: PieChart) {
        pieChart.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            isRotationEnabled = true
            dragDecelerationFrictionCoef = 0.9f
            holeRadius = 1f
            isDrawHoleEnabled = true
            legend.form = Legend.LegendForm.NONE
            isHighlightPerTapEnabled = true
            animateX(1400, Easing.EaseInOutCirc)
            setHoleColor(R.color.background_gradient_end)
            legend.isEnabled = false
            transparentCircleRadius = 1f
            maxAngle = 360f
        }
    }

    private fun getUser() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    binding.apply {
                        val textViewName = tvNameDetails
                        val imageViewProfile = civProfilePictureDetails
                        val user = it.data
                        textViewName.text = user.username
                        setImageUrl(imageViewProfile, user.image)

                        // closing the dialog
                    }
                }
                is Response.Error -> {
                    println(it.message)
                }
                else -> {
                    println(Constants.ERROR_REF)
                }
            }

        }
        dismissDialog()

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}