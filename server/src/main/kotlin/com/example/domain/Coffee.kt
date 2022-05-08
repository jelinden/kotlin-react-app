package com.example.domain

import com.example.util.RandomString

data class Coffee(
    val id: String,
    val name: String,
    val price: Double,
    val weight: Double,
    val roastlevel: Int
) {
    constructor(name: String, price: Double, weight: Double, roastlevel: Int) : this(RandomString(16), name, price, weight, roastlevel)
    override fun toString(): String = id + ' ' + name + ' ' + price + ' ' + weight + ' ' + roastlevel
}
