package com.bluebank.bluebankapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bluebank.bluebankapp.viewmodels.MainViewModel
import com.bluebank.bluebankapp.R
import com.bluebank.bluebankapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {


    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity)
            .supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_new_account -> createNewAccount()
                    R.id.action_new_transaction -> createNewTransaction()
                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    fun createNewAccount(): Boolean {
        findNavController().navigate(R.id.action_manageAccounts)
        return true
    }

    fun createNewTransaction(): Boolean {
        Toast.makeText(
            requireContext(),
            getString(R.string.not_implemented_message), Toast.LENGTH_LONG
        ).show()
//        findNavController().navigate(R.id.action_manageTransactions)
        return true
    }
}