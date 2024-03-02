package com.bluebank.bluebankapp.data

import android.content.Context
import com.bluebank.bluebankapp.R

enum class AccountType(val value: Int) {
    Unknown(0),
    Savings(1),
    Checking(2);

    companion object {
        fun fromInt(value: Int) = entries.first { it.value == value }

        fun getDisplayValue(context: Context, type: AccountType): String {
            return with(context) {
                when (type) {
                    Unknown -> resources.getString(R.string.unkown_account)
                    Savings -> resources.getString(R.string.savings_account)
                    Checking -> resources.getString(R.string.checking_account)
                }
            }
        }
    }
}