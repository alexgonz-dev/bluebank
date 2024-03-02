package com.bluebank.bluebankapp.data

import android.content.Context
import com.bluebank.bluebankapp.R

enum class TransactionType(val value: Int) {
    Consignment(1),
    Withdrawal(2);

    companion object {
        fun fromInt(value: Int) = TransactionType.entries.first { it.value == value }

        fun getDisplayValue(context: Context, type: TransactionType): String {
            return with(context) {
                when (type) {
                    Consignment -> resources.getString(R.string.unkown_account)
                    Withdrawal -> resources.getString(R.string.savings_account)
                }
            }
        }
    }
}