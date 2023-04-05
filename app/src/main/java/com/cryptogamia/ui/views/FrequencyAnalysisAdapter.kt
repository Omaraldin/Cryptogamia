package com.cryptogamia.ui.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cryptogamia.R
import com.cryptogamia.data.model.FrequencyItem

class FrequencyAnalysisAdapter(items: List<FrequencyItem>, message: TextView) :
    RecyclerView.Adapter<FrequencyAnalysisAdapter.ViewHolder>() {
    private var items: List<FrequencyItem>
    private val message: TextView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.frequency_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: FrequencyItem = items[position]
        item.replacement = holder.replacement
        holder.word.text = item.word
        holder.count.text = "" + item.count
    }

    override fun getItemCount(): Int {
        return getItems().size
    }

    fun getItems(): List<FrequencyItem> {
        return items
    }

    fun setItems(items: List<FrequencyItem>) {
        this.items = items
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var word: TextView
        var replacement: EditText
        var count: TextView

        init {
            word = itemView.findViewById(R.id.word)
            replacement = itemView.findViewById(R.id.replacement)
            count = itemView.findViewById(R.id.rate)
        }
    }

    init {
        this.items = items
        this.message = message
    }
}
