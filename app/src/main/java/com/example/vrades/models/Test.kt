package com.example.vrades.models

import com.example.vrades.enums.TestState
import java.time.LocalDate
import java.util.*

data class Test(val date: LocalDate, val isCompleted: TestState){

}
