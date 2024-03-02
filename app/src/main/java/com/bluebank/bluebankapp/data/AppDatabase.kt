package com.bluebank.bluebankapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bluebank.bluebankapp.data.converters.AccountTypeConverter
import com.bluebank.bluebankapp.data.converters.DateConverter
import com.bluebank.bluebankapp.data.dao.AccountDao
import com.bluebank.bluebankapp.data.dao.TransactionDao
import com.bluebank.bluebankapp.data.entities.AccountEntity
import com.bluebank.bluebankapp.data.entities.TransactionEntity

@Database(
    entities = [AccountEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, AccountTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao?
    abstract fun transactionDao(): TransactionDao?

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "bluebank.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}