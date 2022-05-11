package com.example.vrades.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentAboutBinding
import com.example.vrades.utils.Constants.BACKGROUND_APP
import com.example.vrades.utils.UIUtils.loadImageUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater)

        binding.executePendingBindings()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val buttonBack = btnBackAbout
            val constraintLayoutBackground = clAbout
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