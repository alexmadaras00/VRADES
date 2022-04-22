package com.example.vrades.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vrades.R
import com.example.vrades.models.LifeHack

class SolutionsViewModel : ViewModel() {
    private val lifeHacks = ArrayList<LifeHack>() //

    private fun addLifeHacks() {
        lifeHacks.add(LifeHack("Meditation", R.drawable.meditation_icon_138394, "20min/day"))
        lifeHacks.add(LifeHack("Jogging", R.drawable.ic_baseline_directions_run_24, "15min/day"))
    }

    fun getLifeHacks(): ArrayList<LifeHack> {
        addLifeHacks()
        return lifeHacks
    }
}