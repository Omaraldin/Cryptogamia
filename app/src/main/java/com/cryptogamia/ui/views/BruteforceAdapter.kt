package com.cryptogamia.ui.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cryptogamia.R
import com.cryptogamia.data.model.BruteforceResultItem

class BruteforceAdapter(private val items: List<BruteforceResultItem>) :
    RecyclerView.Adapter<BruteforceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BruteforceAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bruteforce_result_item, parent, false)
        return BruteforceAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BruteforceAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.context
        val clipboardManager =
            holder.itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        holder.key.text = item.key.toString()

        holder.message.text = item.message
        holder.itemView.setOnClickListener {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("message", item.message))
            Toast.makeText(
                holder.itemView.context,
                "Copied message of key ${item.key}.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val key: TextView = itemView.findViewById(R.id.key)
        val message: TextView = itemView.findViewById(R.id.message)


    }
}