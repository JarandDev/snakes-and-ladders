package dev.jarand.snakesandladders.core

class Dice {

    fun roll(): Int {
        return (1..6).random()
    }
}
