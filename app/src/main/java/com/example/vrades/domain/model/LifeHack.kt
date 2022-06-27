package com.example.vrades.domain.model

data class LifeHack(
    val name: String,
    val icon: String,
    val details: String? = null
) {
    constructor(icon: String, details: String) : this("", icon, details)

}
