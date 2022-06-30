package com.example.vrades.domain.model

data class Test(
    val date: String?,
    val state: Int? = 0,
    val result: String,
    val emotionsScore: MutableMap<String,Float>,
    val isCompleted: Boolean?

)
