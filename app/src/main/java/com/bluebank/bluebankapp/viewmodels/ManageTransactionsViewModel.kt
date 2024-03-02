package com.bluebank.bluebankapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bluebank.bluebankapp.R
import com.bluebank.bluebankapp.data.AppDatabase
import com.bluebank.bluebankapp.data.TransactionType
import com.bluebank.bluebankapp.data.entities.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class ManageTransactionsViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    private val _transactionsLiveData = MutableLiveData<List<TransactionEntity>>()
    val transactionsLiveData: LiveData<List<TransactionEntity>>
        get() = _transactionsLiveData

    init {
        _transactionsLiveData.value = emptyList()
    }

    private var listener: ManageTransactionsViewModelListener? = null

    fun initialize(listener: ManageTransactionsViewModelListener) {
        this.listener = listener
    }

    fun tryCreateTransaction(
        accountID: String,
        amount: Int?,
        city: String,
        transactionType: TransactionType
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val account = database?.accountDao()?.getAccountByAccountId(accountID)
                if (account == null) {
                    listener?.onMessage(R.string.invalid_account)
                    return@withContext
                }

                if (amount == null || (transactionType == TransactionType.Withdrawal && account.balance < amount)) {
                    listener?.onMessage(R.string.invalid_amount)
                    return@withContext
                }

                val transaction =
                    TransactionEntity(Date(), accountID, transactionType, city, amount)

                database?.transactionDao()?.createTransaction(transaction)

                account.balance = when (transactionType) {
                    TransactionType.Consignment -> account.balance + amount
                    TransactionType.Withdrawal -> account.balance - amount
                }

                database?.accountDao()?.createAccount(account)
                listener?.onMessage(R.string.transactions_successfully_created)
                listener?.onTransactionCreated()
            }
        }
    }

    fun refreshTransactions(accountID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _transactionsLiveData.postValue(
                    database?.transactionDao()?.getAccountById(accountID)
                )
            }
        }
    }

    interface ManageTransactionsViewModelListener {

        fun onTransactionCreated()
        fun onMessage(message: Int);
        fun onMessage(message: Int, length: Int);
    }
}