package com.example.vrades.model

data class LifeHack(val name: String, val icon: Int, val details: String) {
    constructor(name: String, icon: Int) : this(name, icon, "")
}
