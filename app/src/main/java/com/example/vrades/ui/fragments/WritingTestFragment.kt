package com.example.vrades.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentWritingTestBinding
import com.example.vrades.model.Response
import com.example.vrades.model.Test
import com.example.vrades.utils.Constants
import com.example.vrades.utils.UIUtils
import com.example.vrades.utils.UIUtils.toast
import com.example.vrades.viewmodels.TestViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class WritingTestFragment : Fragment() {

    private var _binding: FragmentWritingTestBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[TestViewModel::class.java]
        _binding = FragmentWritingTestBinding.inflate(inflater)

        binding.viewModelTest = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataWritingTest()
        val navController = requireView().findNavController()
        binding.apply {
            val buttonProceed = btnNext2
            val buttonRestart = btnRestart2
            buttonProceed.setOnClickListener {
                viewModelTest!!.setStateCount(3)
                addTestToRealtime()
            }
            buttonRestart.setOnClickListener {
                navController.navigate(WritingTestFragmentDirections.actionWritingTestFragmentToFaceDetectionFragment())
                viewModelTest!!.setStateCount(0)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val navController = requireView().findNavController()
        binding.apply {
            val textViewWords = tvCheckedWords2
            val imageViewWords = ivCheckedWords2
            val editTextWriting = etWritingText
            val buttonProceed = btnNext2
            val buttonRestart = btnRestart2
            editTextWriting.doOnTextChanged { text, _, _, _ ->
                val wordsText = text.toString().split(" ")
                val length = wordsText.size
                val words: String = if (length == 1) "word" else "words"
                val finalString = length.toString() + words
                if (length >= MINIMUM_WORDS) {
                    textViewWords.visibility = View.VISIBLE
                    imageViewWords.visibility = View.VISIBLE
                    textViewWords.text = finalString
                    buttonProceed.isVisible = true
                    buttonRestart.isVisible = true
                    viewModelTest!!.setStateCount(3)
                    viewModelTest!!.setWritingStateCount(3)
                }
            }
            editTextWriting.doAfterTextChanged {
                val text = it.toString().split(" ")
                val length = text.size
                val words: String = if (length == 1) "word" else "words"
                val finalString = length.toString() + words
                if (length >= MINIMUM_WORDS) {
                    textViewWords.visibility = View.VISIBLE
                    imageViewWords.visibility = View.VISIBLE
                    textViewWords.text = finalString
                    buttonProceed.isVisible = true
                    buttonRestart.isVisible = true
                    viewModelTest!!.setStateCount(3)
                    viewModelTest!!.setWritingStateCount(3)
                }
            }
            editTextWriting.setOnEditorActionListener(TextView.OnEditorActionListener { _, p1, _ ->
                if (p1 == EditorInfo.IME_ACTION_DONE) {
                    UIUtils.dismissKeyboard(requireActivity())
                }
                return@OnEditorActionListener false
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun getDataWritingTest() {
        viewModel.getDataWritingTest().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                        val data = it.data
                        val randomQuestion = data.random()
                        binding.tvWriting.text = randomQuestion

                }
                is Response.Error -> {
                    println(it.message)
                }
                else -> {
                }
            }
        }
    }

    private fun addTestToRealtime() {
        val currentDate = LocalDate.now().toString()
        val currentState = viewModel.getCurrentState().ordinal
        val result = "happy"
        val test = Test(currentDate, currentState, result, true)
        viewModel.addTestToRealtime(test).observe(viewLifecycleOwner){
            when (it) {
                is Response.Success -> {
                    toast(requireContext(),"Test completed!")
                    generateAdvicesByTestResult()
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
    private fun generateAdvicesByTestResult() {
        viewModel.generateAdvicesByTestResult().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Test results generated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(WritingTestFragmentDirections.actionNavWritingToNavDetails())
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


    companion object {
        const val MINIMUM_WORDS = 1
        fun newInstance() = FaceDetectionFragment()
    }

}