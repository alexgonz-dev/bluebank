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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluebank.bluebankapp.adapters.TransactionsAdapter
import com.bluebank.bluebankapp.data.TransactionType
import com.bluebank.bluebankapp.databinding.FragmentManageTransactionsBinding
import com.bluebank.bluebankapp.tryParseBalance
import com.bluebank.bluebankapp.viewmodels.ManageTransactionsViewModel

class ManageTransactionsFragment : Fragment(),
    ManageTransactionsViewModel.ManageTransactionsViewModelListener {

    private lateinit var viewModel: ManageTransactionsViewModel
    private val args: ManageTransactionsFragmentArgs by navArgs()
    private lateinit var binding: FragmentManageTransactionsBinding
    private lateinit var adapter: TransactionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageTransactionsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ManageTransactionsViewModel::class.java]
        viewModel.initialize(this@ManageTransactionsFragment)
        binding.accountNumber.text = args.accountId

        with(binding.rvTransactions) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(
                context, LinearLayoutManager(context).orientation
            )
            addItemDecoration(divider)
        }

        viewModel.transactionsLiveData.observe(viewLifecycleOwner) { transactions ->
            transactions?.let {
                adapter = TransactionsAdapter(requireContext(), it)
                binding.rvTransactions.adapter = adapter
                binding.rvTransactions.layoutManager = LinearLayoutManager(activity)
            }
        }
        viewModel.refreshTransactions(args.accountId)

        val transactionTypes = TransactionType.entries.map { it.name }
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, transactionTypes)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTransactionType.adapter = arrayAdapter

        binding.buttonCreateTransaction.setOnClickListener {
            createTransaction()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.refreshTransactions(args.accountId)
        super.onViewCreated(view, savedInstanceState)
    }

    fun createTransaction() {
        val balance = tryParseBalance(binding.editTextAmount.text.toString())
        val selectedTransactionType =
            TransactionType.entries[binding.spinnerTransactionType.selectedItemPosition]
        val city = binding.spinnerCity.selectedItem.toString()

        viewModel.tryCreateTransaction(args.accountId, balance, city, selectedTransactionType)

    }

    override fun onTransactionCreated() {
        Handler(Looper.getMainLooper()).post {
            with(binding) {
                editTextAmount.text = null
                spinnerTransactionType.setSelection(0)
                spinnerCity.setSelection(0)
            }
            viewModel.refreshTransactions(args.accountId)
        }
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