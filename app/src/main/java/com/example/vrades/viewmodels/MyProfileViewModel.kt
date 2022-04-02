package com.example.vrades.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.vrades.enums.TestState
import com.example.vrades.models.Test
import java.time.LocalDate
import kotlin.collections.ArrayList

class MyProfileViewModel : ViewModel() {

    private var tests = ArrayList<Test>()
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTests(){

        tests.add(Test(LocalDate.of(2022,1,22),TestState.AUDIO_DETECTION_COMPLETED))
        tests.add(Test(LocalDate.of(2022,2,10),TestState.TEST_STARTED))
        tests.add(Test(LocalDate.of(2022,2,10),TestState.TEST_STARTED))
        tests.add(Test(LocalDate.of(2022,2,10),TestState.TEST_STARTED))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTests(): ArrayList<Test>{
        addTests()
        return tests
    }
}