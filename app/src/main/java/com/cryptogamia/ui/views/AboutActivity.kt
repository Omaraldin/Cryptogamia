package com.cryptogamia.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cryptogamia.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.hide()

        val back = this.findViewById<View>(R.id.backButton)
        back.setOnClickListener { _ ->
            finish()
        }
    }
}