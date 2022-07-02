package com.example.vrades.domain.model

data class User(
    var email: String,
    val username: String,
    val image: String,
    var advices: List<LifeHack>? = listOf(),
    var tests: List<Test>? = listOf()
)
