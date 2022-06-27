package com.example.vrades.presentation.ui.fragments.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vrades.databinding.FragmentTutorialWritingBinding
import com.example.vrades.presentation.utils.UIUtils
import com.example.vrades.presentation.viewmodels.TutorialViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialWritingFragment : Fragment() {
    private val viewModel: TutorialViewModel by activityViewModels()
    private var _binding: FragmentTutorialWritingBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialWritingBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.apply {
            val editTextWriting = etWriting
            val textViewInfo = tvWritingInfo
            val textViewWords = tvCheckedWords
            val imageViewWords = ivCheckedWords
            val textViewDone = tvWritingCompleted
            val textViewCongrats = tvWritingCongrats
            val buttonNext = btnNextTutorialWriting
            editTextWriting.doOnTextChanged { text, _, _, _ ->
                val wordsText = text.toString().split(" ")
                val length = wordsText.size
                val words: String = if (length == 1) "word" else "words"
                val finalString = "$length $words"
                if (length >= MINIMUM_WORDS) {
                    textViewWords.visibility = View.VISIBLE
                    imageViewWords.visibility = View.VISIBLE
                    textViewWords.text = finalString
                    buttonNext.isEnabled = true
                    textViewCongrats.visibility = View.VISIBLE
                    textViewDone.visibility = View.VISIBLE
                    textViewInfo.visibility = View.INVISIBLE
                }
            }
            editTextWriting.doAfterTextChanged {
                val text = it.toString().split(" ")
                val length = text.size
                val words: String = if (length == 1) "word" else "words"
                val finalString = "$length $words"
                if (length >= MINIMUM_WORDS) {
                    textViewWords.visibility = View.VISIBLE
                    imageViewWords.visibility = View.VISIBLE
                    textViewWords.text = finalString
                    buttonNext.isEnabled = true
                    textViewCongrats.visibility = View.VISIBLE
                    textViewDone.visibility = View.VISIBLE
                    textViewInfo.visibility = View.INVISIBLE
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

    companion object {
        const val MINIMUM_WORDS = 1
    }

}