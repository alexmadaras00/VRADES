package com.example.vrades.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.vrades.R
import com.example.vrades.firebase.domain.use_cases.profile_repository.ProfileUseCases
import com.example.vrades.model.LifeHack
import com.example.vrades.utils.Constants.MEDITATION_ICON
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SolutionsViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {
    private val lifeHacks = ArrayList<LifeHack>() //

    fun getUser() = liveData(Dispatchers.IO) {
        profileUseCases.getUserById().collect {
            emit(it)
        }
    }

    private fun addLifeHacks() {
        lifeHacks.add(LifeHack("Meditation", MEDITATION_ICON, "20min/day"))
    }

    fun getLifeHacks(): ArrayList<LifeHack> {
        addLifeHacks()
        return lifeHacks
    }
}