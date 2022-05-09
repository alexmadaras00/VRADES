package com.example.vrades.model

data class LifeHack(val name: String, val icon: String, val details: String) {
    constructor(name: String, icon: String) : this(name, icon, "")
}
