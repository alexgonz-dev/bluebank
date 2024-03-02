package com.bluebank.bluebankapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bluebank.bluebankapp.data.entities.AccountEntity

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAccount(account: AccountEntity)

    @Query("SELECT * FROM accounts ORDER BY date ASC")
    fun getAll(): LiveData<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    fun getAccountById(id: Int): AccountEntity?

    @Query("SELECT * FROM accounts WHERE accountId = :id")
    fun getAccountByAccountId(id: String): AccountEntity?
}