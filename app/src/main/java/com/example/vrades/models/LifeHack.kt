package com.example.vrades.models

data class LifeHack(val name: String, val icon: Int, val details: String) {
    constructor(name: String, icon: Int) : this(name, icon, "")
}
