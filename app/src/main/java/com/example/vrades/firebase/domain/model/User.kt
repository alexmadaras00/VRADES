package com.example.vrades.model

data class User(
    var email: String,
    val username: String,
    val image: String,
    var isTutorialEnabled: Boolean? = false,
    var advices: List<LifeHack>? = listOf(),
    var tests: List<Test>? = listOf()
)
