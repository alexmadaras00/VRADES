package com.example.vrades.domain.model

import com.example.vrades.domain.model.LifeHack
import com.example.vrades.domain.model.Test

data class User(
    var email: String,
    val username: String,
    val image: String,
    var isTutorialEnabled: Boolean? = false,
    var advices: List<LifeHack>? = listOf(),
    var tests: List<Test>? = listOf()
)
