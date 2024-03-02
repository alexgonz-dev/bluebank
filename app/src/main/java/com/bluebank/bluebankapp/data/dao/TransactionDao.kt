package com.bluebank.bluebankapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bluebank.bluebankapp.data.entities.AccountEntity
import com.bluebank.bluebankapp.data.entities.TransactionEntity

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun createTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE accountId = :id ORDER BY date DESC")
    fun getAccountById(id: String): List<TransactionEntity>

    @Query("SELECT * FROM transactions ORDER BY date ASC")
    fun getAll(): List<TransactionEntity>
}