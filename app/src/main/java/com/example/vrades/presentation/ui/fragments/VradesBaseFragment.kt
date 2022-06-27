package com.example.vrades.presentation.ui.fragments

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class VradesBaseFragment : Fragment() {

    abstract fun connectViewModelEvents()
    abstract fun disconnectViewModelEvents()
}
