package com.cryptogamia.ui.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cryptogamia.R
import com.cryptogamia.data.model.BruteforceResultItem

class CaesarBruteforceResult : AppCompatActivity() {
    private lateinit var viewModel: BruteforceItemViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BruteforceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caesar_bruteforce_result)
        supportActionBar?.hide()

        val back = this.findViewById<View>(R.id.backButton)
        back.setOnClickListener { _ ->
            finish()
        }
        val list = arrayListOf<BruteforceResultItem>()
        for (key in 1..25) {
            val item = intent.getParcelableExtra<BruteforceResultItem>("${key}_item")
            if (item != null) {
                list.add(item)
            } else {
                list.add(BruteforceResultItem(key, ""))
            }
        }
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(BruteforceItemViewModel::class.java)
        viewModel.setItemList(list)

        viewModel.getItemList().observe(this, Observer {
            adapter = BruteforceAdapter(it)
            recyclerView.adapter = adapter
        })

    }

}