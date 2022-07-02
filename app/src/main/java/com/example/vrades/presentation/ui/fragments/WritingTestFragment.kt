package com.example.vrades.presentation.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.os.StrictMode
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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.vrades.R
import com.example.vrades.data.api.detections.TextDetectionAPI
import com.example.vrades.databinding.DialogLoadingBinding
import com.example.vrades.databinding.FragmentWritingTestBinding
import com.example.vrades.domain.model.Response
import com.example.vrades.domain.model.Test
import com.example.vrades.presentation.utils.Constants
import com.example.vrades.presentation.utils.Constants.MINIMUM_WORDS
import com.example.vrades.presentation.utils.UIUtils
import com.example.vrades.presentation.utils.UIUtils.toast
import com.example.vrades.presentation.viewmodels.TestViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDateTime

@AndroidEntryPoint
class WritingTestFragment : Fragment() {

    private var _binding: FragmentWritingTestBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TestViewModel by activityViewModels()
    private var dialog: Dialog? = null
    private var dialogBinding: DialogLoadingBinding? = null
    private var isUserInput = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWritingTestBinding.inflate(inflater)
        binding.viewModelTest = viewModel
        binding.executePendingBindings()
        dialog = Dialog(this.requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        getDataWritingTest()
        val navController = requireView().findNavController()
        binding.apply {
            val buttonProceed = btnNext2
            val buttonRestart = btnRestart2
            val editTextWriting = etWritingText
            val textViewWords = tvCheckedWords2
            val imageViewWords = ivCheckedWords2
            editTextWriting.setText(
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                        "\n" +
                        "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham."
            )
            if (editTextWriting.text?.split(" ")?.size!! >= MINIMUM_WORDS &&
                editTextWriting.text.toString().length <= 149
            ) {
                val finalString = "${editTextWriting.text?.split(" ")?.size!!} words"
                textViewWords.visibility = View.VISIBLE
                imageViewWords.visibility = View.VISIBLE
                textViewWords.text = finalString
                buttonProceed.isVisible = true
                buttonRestart.isVisible = true
                viewModelTest!!.setWritingStateCount(3)
            }
            buttonProceed.setOnClickListener {
                viewModelTest!!.setStateCount(3)
                Thread.sleep(12)
                openDialog()
                lifecycleScope.launch(Dispatchers.IO) {
                    addTestToRealtime(etWritingText.text.toString())
                }
            }
            buttonRestart.setOnClickListener {
                navController.navigate(WritingTestFragmentDirections.actionWritingTestFragmentToFaceDetectionFragment())
                viewModelTest!!.setStateCount(0)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            val editTextWriting = etWritingText
            val buttonProceed = btnNext2
            val buttonRestart = btnRestart2
            val textViewWords = tvCheckedWords2
            val imageViewWords = ivCheckedWords2
            lifecycleScope.launchWhenCreated {
                editTextWriting.doOnTextChanged { text, _, _, _ ->
                    val finalString = "${text.toString().split(" ").size} words"
                    if (text.toString().split(" ").size >= MINIMUM_WORDS &&
                        text.toString().length <= 149
                    ) {
                        textViewWords.visibility = View.VISIBLE
                        imageViewWords.visibility = View.VISIBLE
                        textViewWords.text = finalString
                        buttonProceed.isVisible = true
                        buttonRestart.isVisible = true
                        viewModelTest!!.setWritingStateCount(3)
                    } else {
                        viewModelTest!!.setWritingStateCount(2)
                        buttonProceed.visibility = View.GONE
                        buttonRestart.visibility = View.GONE
                        textViewWords.visibility = View.GONE
                        imageViewWords.visibility = View.GONE
                    }
                }
                editTextWriting.doAfterTextChanged {
                    val finalString = "${it.toString().split(" ").size} words"
                    if (it.toString()
                            .split(" ").size >= MINIMUM_WORDS && it.toString().length <= 149
                    ) {
                        textViewWords.visibility = View.VISIBLE
                        imageViewWords.visibility = View.VISIBLE
                        textViewWords.text = finalString
                        buttonProceed.isVisible = true
                        buttonRestart.isVisible = true
                        editTextWriting.hint = getString(R.string.congrats_writing)
                        viewModelTest!!.setWritingStateCount(3)
                    } else {
                        viewModelTest!!.setWritingStateCount(2)
                        buttonProceed.visibility = View.GONE
                        buttonRestart.visibility = View.GONE
                        textViewWords.visibility = View.GONE
                        imageViewWords.visibility = View.GONE
                        editTextWriting.hint = ""
                    }
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
        dialog!!.dismiss();
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

    private suspend fun addTestToRealtime(text: String) {
        val currentDate = LocalDateTime.now().toString()
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.setStateCount(3)
        }
        val currentState = viewModel.getCurrentState().ordinal
        val result = TextDetectionAPI.detectText(text)
        if (result == "Invalid JSON format") {
            toast(requireContext(), "The text is not clear enough. Please try again!")
        } else {
            println("INPUT: $text")
            println("RESULT WRITING: $result")
            val emotionsMap = configJsonToMap(result)
            val maxEmotion = calculateMaxEmotion(emotionsMap)
            withContext(Dispatchers.Main) {
                toast(requireContext(), "Writing Detection Result: $maxEmotion")
            }
            viewModel.setDigitalWritingDetectedResult(emotionsMap)
            displayPartialResults()
            val percentages = viewModel.getPercentageOfResults()
            val testResults = viewModel.getFinalDetectionResult()
            println("FINAL PERCENTAGES: $percentages")
            val test = Test(currentDate, currentState, testResults, percentages, true)
            println("test: $test")
            withContext(Dispatchers.Main) {
                viewModel.addTestToRealtime(test).observe(viewLifecycleOwner) {
                    when (it) {
                        is Response.Success -> {
                            toast(requireContext(), "Test completed!")
                            generateAdvicesByTestResult(currentDate)
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
            dismissDialog()
        }
    }

    private fun displayPartialResults() {
        val faceDetectionPercentages = viewModel.getFaceDetectionResults()
        val digitalWritingDetectionPercentages = viewModel.getDigitalWritingDetectionResults()
        val audioPercentages = viewModel.getAudioDetectionResults()
        println("FACE: $faceDetectionPercentages")
        println("AUDIO: $audioPercentages")
        println("WRITING: $digitalWritingDetectionPercentages")
    }

    private fun calculateMaxEmotion(emotionsMap: MutableMap<String, Float>): String {
        val maxValue = emotionsMap.maxOf {
            it.value
        }
        val maxValueKeys: MutableList<String> = mutableListOf()
        var finalResult = ""
        for ((key, value) in emotionsMap) {
            if (value == maxValue) {
                maxValueKeys.add(key)
            }
        }
        if (maxValueKeys.size > 1)
            maxValueKeys.indices.forEach {
                if (it > 0)
                    finalResult += ", $it"
                else finalResult += it
            }
        else finalResult = maxValueKeys[0]
        return finalResult
    }

    private fun configJsonToMap(result: String): MutableMap<String, Float> {
        val emotionsMap = mutableMapOf<String, Float>()
        val pathJson = JSONObject(result).getJSONObject("sentence")
        emotionsMap["angry"] = pathJson.getDouble("anger").toFloat()
        emotionsMap["disgust"] = pathJson.getDouble("disgust").toFloat()
        emotionsMap["fear"] = pathJson.getDouble("fear").toFloat()
        emotionsMap["happy"] = pathJson.getDouble("joy").toFloat()
        emotionsMap["neutral"] = pathJson.getDouble("noemo").toFloat()
        emotionsMap["sad"] = pathJson.getDouble("sadness").toFloat()
        emotionsMap["surprise"] = pathJson.getDouble("surprise").toFloat()
        emotionsMap["love"] = pathJson.getDouble("love").toFloat()
        return emotionsMap

    }

    private fun generateAdvicesByTestResult(currentDate: String) {
        viewModel.generateAdvicesByTestResult().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Test results generated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Response.Error -> {
                    println(it.message)
                }
                else -> {
                    println(Constants.ERROR_REF)
                }

            }

        }
        val actionDetails = WritingTestFragmentDirections.actionNavWritingToNavDetails(currentDate)
        findNavController().navigate(actionDetails)
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

    companion object {
        fun newInstance() = FaceDetectionFragment()
    }

}