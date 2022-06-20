package com.example.vrades.model

import com.example.vrades.enums.TestState
import java.time.LocalDate

data class Test(
    val date: String?,
    val state: Int? = 0,
    val result: String,
    val isCompleted: Boolean?
)
