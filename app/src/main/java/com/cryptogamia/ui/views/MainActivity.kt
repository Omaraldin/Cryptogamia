package com.cryptogamia.ui.views

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.cryptogamia.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    companion object {
        var words: ArrayList<String> = arrayListOf();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val content = readTextFromAssets(this, "words.txt");
        words = ArrayList(content.split("\n"))

        val toggleTheme = this.findViewById<SwitchCompat>(R.id.dark_light_switch)

        val caesar = this.findViewById<TextView>(R.id.caesarCipher)
        val vigenere = this.findViewById<TextView>(R.id.vigenereCipher)
        val substitution = this.findViewById<TextView>(R.id.substitutionCipher)
        val about = this.findViewById<TextView>(R.id.about_button)

        about.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        caesar.setOnClickListener { item ->
            val intent = Intent(this, CaesarActivity::class.java)
            startActivity(intent)
        }

        vigenere.setOnClickListener { item ->
            startActivity(Intent(this, VigenereActivity::class.java))

        }

        substitution.setOnClickListener { item ->
            startActivity(Intent(this, SubstitutionActivity::class.java))


        }

        toggleTheme.isChecked =
            baseContext?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_YES) == Configuration.UI_MODE_NIGHT_YES;

        toggleTheme.setOnCheckedChangeListener { _, isChecked ->
            runOnUiThread {
                Handler(Looper.getMainLooper()).postDelayed({
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }, 250)
            }
        }
    }

    private fun readTextFromAssets(context: Context, fileName: String): String {
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)
        val inputReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputReader)

        val stringBuilder = StringBuilder()
        var line: String?
        try {
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            bufferedReader.close()
            inputReader.close()
            inputStream.close()
        }

        return stringBuilder.toString()
    }

}

