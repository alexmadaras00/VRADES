package com.example.vrades.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentWritingTestBinding
import com.example.vrades.utils.UIUtils
import com.example.vrades.viewmodels.TestViewModel


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
        binding.lifecycleOwner = this
        binding.viewModelTest = viewModel
        binding.executePendingBindings()
        return binding.root
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
                val words = "words"
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
            buttonProceed.setOnClickListener {
                navController.navigate(WritingTestFragmentDirections.actionNavWritingToNavDetails())
                viewModelTest!!.setStateCount(3)
            }
            buttonRestart.setOnClickListener {
                navController.navigate(WritingTestFragmentDirections.actionWritingTestFragmentToFaceDetectionFragment())
                viewModelTest!!.setStateCount(0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val MINIMUM_WORDS = 1
        fun newInstance() = FaceDetectionFragment()
    }

}