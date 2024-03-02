package com.bluebank.bluebankapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluebank.bluebankapp.databinding.FragmentManageAccountsBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bluebank.bluebankapp.adapters.AccountsAdapter
import com.bluebank.bluebankapp.viewmodels.ManageAccountsViewModel
import com.bluebank.bluebankapp.R

class ManageAccountsFragment : Fragment(), AccountsAdapter.AccountItemListener {

    private lateinit var viewModel: ManageAccountsViewModel
    private lateinit var binding: FragmentManageAccountsBinding
    private lateinit var adapter: AccountsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageAccountsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ManageAccountsViewModel::class.java]

        with(binding.rvAccounts) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(
                context, LinearLayoutManager(context).orientation
            )
            addItemDecoration(divider)
        }

        viewModel.accountList?.observe(viewLifecycleOwner, Observer {
            adapter = AccountsAdapter(requireContext(), it, this@ManageAccountsFragment)
            binding.rvAccounts.adapter = adapter
            binding.rvAccounts.layoutManager = LinearLayoutManager(activity)
        })

        binding.fabAddAccount.setOnClickListener {
            createAccount()
        }

        return binding.root
    }

    private fun createAccount() {
        findNavController().navigate(R.id.action_accountCreation)
    }

    override fun manageTransaction(accountId: String) {
        val action = ManageAccountsFragmentDirections.actionManageTransactions(accountId)
        findNavController().navigate(action)
    }
}