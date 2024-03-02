package com.bluebank.bluebankapp.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bluebank.bluebankapp.data.AccountType
import kotlinx.parcelize.Parcelize
import java.util.Date

const val NEW_ACCOUNT_ID = 0

@Parcelize
@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var date: Date,
    var accountType: AccountType,
    var accountId: String,
    var origin: String,
    var balance: Int
) : Parcelable {
    constructor(
        date: Date,
        accountId: String,
        accountType: AccountType,
        origin: String,
        balance: Int
    ) : this(
        NEW_ACCOUNT_ID,
        date,
        accountType,
        accountId,
        origin,
        balance
    )
}