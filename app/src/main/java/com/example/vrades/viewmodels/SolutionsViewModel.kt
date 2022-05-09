package com.example.vrades.viewmodels

import androidx.lifecycle.ViewModel
import com.example.vrades.R
import com.example.vrades.model.LifeHack
import com.example.vrades.utils.Constants.MEDITATION_ICON

class SolutionsViewModel : ViewModel() {
    private val lifeHacks = ArrayList<LifeHack>() //

    private fun addLifeHacks() {
        lifeHacks.add(LifeHack("Meditation", MEDITATION_ICON, "20min/day"))
    }

    fun getLifeHacks(): ArrayList<LifeHack> {
        addLifeHacks()
        return lifeHacks
    }
}