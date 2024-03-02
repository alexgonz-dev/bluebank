package com.bluebank.bluebankapp.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bluebank.bluebankapp.R
import com.bluebank.bluebankapp.data.AccountType
import com.bluebank.bluebankapp.data.AppDatabase
import com.bluebank.bluebankapp.data.TransactionType
import com.bluebank.bluebankapp.data.entities.AccountEntity
import com.bluebank.bluebankapp.data.entities.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class CreateAccountViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    private var listener: CreateAccountViewModelListener? = null

    fun initialize(listener: CreateAccountViewModelListener) {
        this.listener = listener
    }

    fun tryCreateAccount(accountID: String, accountType: AccountType, city: String, balance: Int?) {
        viewModelScope.launch {
            if (balance == null) {
                listener?.onMessage(R.string.balance_invalid, Toast.LENGTH_SHORT)
                return@launch
            }

            val newAccount = AccountEntity(Date(), accountID, accountType, city, balance)

            if (!validateAccountID(newAccount.accountId)) {
                listener?.onMessage(R.string.account_id_invalid, Toast.LENGTH_SHORT)
                return@launch
            }

            if (accountExists(accountID)) {
                listener?.onMessage(R.string.account_already_exists, Toast.LENGTH_SHORT)
                return@launch
            }
            createAccount(newAccount)
            listener?.onMessage(R.string.account_successfully_created)
            listener?.onAccountCreated()
        }

    }

    private suspend fun createAccount(account: AccountEntity) {
        withContext(Dispatchers.IO) {
            database?.accountDao()?.createAccount(account)

            val transaction =
                TransactionEntity(
                    account.date,
                    account.accountId,
                    TransactionType.Consignment,
                    account.origin,
                    account.balance
                )

            database?.transactionDao()?.createTransaction(transaction)
        }
    }

    private suspend fun accountExists(id: String): Boolean {
        return getAccount(id) != null
    }

    private suspend fun getAccount(id: String): AccountEntity? {
        return viewModelScope.async {
            withContext(Dispatchers.IO) {
                database?.accountDao()?.getAccountByAccountId(id)
            }
        }.await()
    }

    private fun validateAccountID(accountID: String): Boolean {
        return try {
            accountID.toInt()
            !accountID.contains(" ") && accountID.isNotEmpty() && accountID.length == 6
        } catch (e: NumberFormatException) {
            false
        }
    }

    interface CreateAccountViewModelListener {

        fun onAccountCreated()
        fun onMessage(message: Int);
        fun onMessage(message: Int, length: Int);
    }
}