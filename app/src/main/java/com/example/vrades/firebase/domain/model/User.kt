package com.example.vrades.model

data class User(
    var email: String,
    val username: String,
    var age: Int? = 0,
    var occupation: String? = null,
    var isTutorialEnabled: Boolean? = false,
    var advices: List<LifeHack>? = listOf(),
    var tests: List<Test>? = listOf()
)
