package com.example.vrades.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vrades.R
import com.example.vrades.models.LifeHack

class SolutionsViewModel : ViewModel() {
    private val _lifeHacks = MutableLiveData<ArrayList<LifeHack>>() //
    private val lifeHacks: LiveData<ArrayList<LifeHack>> get() = _lifeHacks
    private val dataSet = lifeHacks.value

    fun addLifeHacks() {
        dataSet?.add(LifeHack("Meditation", R.drawable.meditation_icon_138394, "20min/day"))
        dataSet?.add(LifeHack("Jogging", R.drawable.ic_baseline_directions_run_24, "15min/day"))
    }

    fun getLifeHacks() = dataSet
}