package com.bluebank.bluebankapp.data.converters

import androidx.room.TypeConverter
import com.bluebank.bluebankapp.data.AccountType

class AccountTypeConverter {

    @TypeConverter
    fun fromInt(value: Int): AccountType {
        return AccountType.fromInt(value)
    }

    @TypeConverter
    fun fromType(type: AccountType): Int {
        return type.value
    }
}