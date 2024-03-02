package com.bluebank.bluebankapp.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bluebank.bluebankapp.R
import com.bluebank.bluebankapp.data.AccountType
import com.bluebank.bluebankapp.databinding.FragmentCreateAccountBinding
import com.bluebank.bluebankapp.tryParseBalance
import com.bluebank.bluebankapp.viewmodels.CreateAccountViewModel

class CreateAccountFragment : Fragment(), CreateAccountViewModel.CreateAccountViewModelListener {

    private lateinit var viewModel: CreateAccountViewModel
    private lateinit var binding: FragmentCreateAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[CreateAccountViewModel::class.java]

        viewModel.initialize(this@CreateAccountFragment)
        val accountTypes = AccountType.entries.map { it.name }.toMutableList()
        accountTypes.removeAt(0)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, accountTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAccountType.adapter = adapter


        binding.buttonCreateAccount.setOnClickListener {
            createAccount()
        }
        return binding.root
    }

    private fun createAccount() {
        val accountID = binding.editTextAccountId.text.toString();
        val balance = tryParseBalance(binding.editTextBalance.text.toString())
        val selectedAccountType =
            AccountType.entries[binding.spinnerAccountType.selectedItemPosition + 1]
        val city = binding.spinnerCity.selectedItem.toString()

        viewModel.tryCreateAccount(accountID, selectedAccountType, city, balance)
    }

    override fun onAccountCreated() {
        findNavController().navigate(R.id.action_back_accountManagement)
    }

    override fun onMessage(message: Int) {
        sendMessage(message)
    }

    override fun onMessage(message: Int, length: Int) {
        sendMessage(message, length)
    }

    private fun sendMessage(message: Int, length: Int = Toast.LENGTH_LONG) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(
                requireContext(),
                getString(message), length
            ).show()
        }
    }
}