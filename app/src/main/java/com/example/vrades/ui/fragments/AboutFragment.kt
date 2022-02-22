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
import com.example.vrades.R
import com.example.vrades.databinding.FragmentAboutBinding
import com.example.vrades.databinding.FragmentAboutBindingImpl

class AboutFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val _binding: FragmentAboutBinding? = null
    var binding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBindingImpl.inflate(inflater)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpanText()

    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setSpanText() {
        val string: Spannable = SpannableStringBuilder(R.string.follow_on_insta.toString())
        val typeface = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.pacifico),
            Typeface.NORMAL
        )
        string.setSpan(
            TypefaceSpan(typeface),
            17,
            string.lastIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvFollow.text = string
    }


}