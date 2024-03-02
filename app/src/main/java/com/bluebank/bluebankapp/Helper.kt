package com.bluebank.bluebankapp

fun tryParseBalance(balance: String): Int? {
    return try {
        val balanceInt = balance.toInt()
        if (balanceInt > 0)
            balanceInt
        else
            null
    } catch (e: NumberFormatException) {
        null
    }
}