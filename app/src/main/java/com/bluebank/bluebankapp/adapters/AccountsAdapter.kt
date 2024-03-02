package com.bluebank.bluebankapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bluebank.bluebankapp.R
import com.bluebank.bluebankapp.data.AccountType
import com.bluebank.bluebankapp.data.entities.AccountEntity
import com.bluebank.bluebankapp.databinding.AccountItemBinding

class AccountsAdapter(
    private val context: Context,
    private val accountList: List<AccountEntity>,
    private val listener: AccountItemListener
) :
    RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = AccountItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.account_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = accountList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = accountList[position]
        with(holder.binding) {
            tvBalanceValue.text = account.balance.toString()
            tvAccountType.text = AccountType.getDisplayValue(context, account.accountType)
            tvAccountNumber.text = account.accountId
            tvCity.text = account.origin

            root.setOnClickListener {
                listener.manageTransaction(account.accountId)
            }
        }

    }


    interface AccountItemListener {
        fun manageTransaction(accountId: String)
    }
}