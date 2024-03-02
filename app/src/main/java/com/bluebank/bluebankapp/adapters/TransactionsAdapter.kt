package com.bluebank.bluebankapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bluebank.bluebankapp.R
import com.bluebank.bluebankapp.data.TransactionType
import com.bluebank.bluebankapp.data.entities.TransactionEntity
import com.bluebank.bluebankapp.databinding.TransactionItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionsAdapter(
    private val context: Context,
    private val transactionList: List<TransactionEntity>
) :
    RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = TransactionItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.transaction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactionList[position]
        with(holder.binding) {
            val extra = if (transaction.transactionType == TransactionType.Withdrawal) "-" else ""
            tvAmountValue.text = buildString {
                append(extra)
                append(transaction.amount.toString())
            }
            tvDate.text = formatDateToString(
                transaction.date,
                context.getString(R.string.display_date_format)
            )

            val color = when (transaction.transactionType) {
                TransactionType.Consignment -> android.R.color.holo_green_light
                TransactionType.Withdrawal -> android.R.color.holo_red_light
            }

            tvAmountValue.setTextColor(ContextCompat.getColor(context, color))

        }
    }

    fun formatDateToString(date: Date, outputFormat: String): String {
        val dateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        return dateFormat.format(date)
    }

    override fun getItemCount(): Int = transactionList.size

}