package com.bluebank.bluebankapp.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bluebank.bluebankapp.data.TransactionType
import kotlinx.parcelize.Parcelize
import java.util.Date


const val NEW_TRANSACTION_ID = 0

@Parcelize
@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var date: Date,
    var accountId: String,
    var transactionType: TransactionType,
    var city: String,
    var amount: Int
) : Parcelable {
    constructor(
        date: Date,
        accountId: String,
        transactionType: TransactionType,
        city: String,
        amount: Int
    ) : this(
        NEW_TRANSACTION_ID,
        date,
        accountId,
        transactionType,
        city,
        amount
    )
}