package com.example.vrades.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.ui.text.toUpperCase
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vrades.R
import com.example.vrades.databinding.DialogLoadingBinding
import com.example.vrades.databinding.FragmentDetailsBinding
import com.example.vrades.model.Response
import com.example.vrades.ui.binding.setImageUrl
import com.example.vrades.utils.Constants
import com.example.vrades.viewmodels.DetailsViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailsViewModel
    private var dialog: Dialog? = null
    private var dialogBinding: DialogLoadingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        _binding = FragmentDetailsBinding.inflate(inflater)

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
        val pieData = viewModel.getData()
        val maxValueEmotion = viewModel.getMax()

        binding.apply {
            val pieChart = Chart
            val buttonBack = btnBackDetails
            val dominantEmotion = tvFinalResultDetails2
            pieData.setValueFormatter(PercentFormatter(pieChart))
            pieChart.invalidate()
            pieChart.data = pieData
            initPieChart(pieChart)
            buttonBack.setOnClickListener {
                findNavController().navigate(DetailsFragmentDirections.actionNavDetailsToNavProfile())
            }
            dominantEmotion.text = maxValueEmotion
        }
        onHandleBackButton()

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
                        val textViewResult = tvFinalResultDetails2
                        val user = it.data
                        val tests = user.tests
                        textViewResult.text = tests?.last()?.result?.uppercase()
                        textViewName.text = user.username
                        setImageUrl(imageViewProfile, user.image)
                        dismissDialog() // closing the dialog
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
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}