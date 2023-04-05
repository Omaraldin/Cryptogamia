package com.cryptogamia.ui.views

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cryptogamia.R
import com.cryptogamia.data.model.FrequencyItem
import com.cryptogamia.utils.SubstitutionCipher
import java.util.*

class FrequencyAnalysis : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frequency_analysis)
        val message = findViewById<TextView>(R.id.message)
        val update = findViewById<Button>(R.id.update)
        supportActionBar?.hide()

        val text = intent.getStringExtra("text")
        val anagram: Map<String, Int>? =
            text?.let { SubstitutionCipher.countNGramFrequencies(it, 1) }
        val items: MutableList<FrequencyItem> = ArrayList<FrequencyItem>()

        message.text = text!!.uppercase(Locale.getDefault())

        val sorted = SubstitutionCipher.sortFrequencies(anagram)
        if (sorted != null) {
            for (i in 0..sorted.size-1) {
                val k = sorted[i]
                val item = k.value?.let { k.key?.let { it1 -> FrequencyItem(it1, it, null) } }
                if (item != null) {
                    items.add(item)
                }
            }
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val viewModel: FrequencyAnalysisViewModel = ViewModelProvider(this)[FrequencyAnalysisViewModel::class.java]
        viewModel.setItemList(items)

        viewModel.getItemList().observe(this) { frequencyItems ->
            val adapter =
                FrequencyAnalysisAdapter(frequencyItems, message)
            recyclerView.adapter = adapter
        }

        update.setOnClickListener { v: View? ->
            (recyclerView.adapter as FrequencyAnalysisAdapter?)?.getItems()
                ?.forEach { frequencyItem ->
                    if (frequencyItem.replacement?.text.toString().isNotEmpty()) {
                        message.text = message.text.toString().uppercase()
                            .replace(
                                frequencyItem.word.toRegex(),
                                frequencyItem.replacement?.text.toString()
                                    .uppercase()
                            )
                    }
                }
        }
    }
}