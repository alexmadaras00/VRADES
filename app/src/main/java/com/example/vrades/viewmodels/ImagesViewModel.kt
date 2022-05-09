package com.example.vrades.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.vrades.firebase.domain.use_cases.vrades_repository.VradesUseCases
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val vradesUseCases: VradesUseCases
) : ViewModel() {
    fun getImageByUrl(name: String) = liveData(Dispatchers.IO) {
        vradesUseCases.getPictureByName(name).collect {
            emit(it)
        }
    }

    fun getImages() = liveData(Dispatchers.IO) {
        vradesUseCases.getPictures().collect {
            emit(it)
        }
    }
}