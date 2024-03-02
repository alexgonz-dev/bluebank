package com.bluebank.bluebankapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluebank.bluebankapp.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManageAccountsViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    val accountList = database?.accountDao()?.getAll()

//    fun addSampleData() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                val sampleNotes = SampleDataProvider.getNotes()
//                database?.noteDao()?.insertAll(sampleNotes)
//            }
//        }
//    }
}