package com.example.vrades.ui.fragments

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

abstract class VradesBaseFragment : Fragment() {

    abstract fun connectViewModelEvents()
    abstract fun disconnectViewModelEvents()
}
