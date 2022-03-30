package com.example.vrades.ui.fragments

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vrades.R
import com.example.vrades.databinding.FragmentAboutBinding
import com.example.vrades.databinding.FragmentAboutBindingImpl

class AboutFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBindingImpl.inflate(inflater)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val buttonBack = btnBackAbout
            val textViewFollow = tvFollow
            buttonBack.setOnClickListener {
                findNavController().navigate(AboutFragmentDirections.actionNavAboutToNavHome())
            }

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}