package com.cryptogamia.ui.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.doAfterTextChanged
import com.cryptogamia.R
import com.cryptogamia.data.model.BruteforceResultItem
import com.cryptogamia.utils.CaesarCipher

class CaesarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caesar)

        supportActionBar?.hide()

        val back = this.findViewById<View>(R.id.backButton)
        back.setOnClickListener { _ ->
            finish()
        }

        val resultLabel = this.findViewById<TextView>(R.id.result_label)
        val messageLabel = this.findViewById<TextView>(R.id.text_label)

        val encode = this.findViewById<SwitchCompat>(R.id.encode)
        val bruteforce = this.findViewById<SwitchCompat>(R.id.bruteforce)

        encode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                resultLabel.text = "Cipher"
                messageLabel.text = "Message"
                bruteforce.isEnabled = false;
                bruteforce.isChecked = false;
            } else {
                resultLabel.text = "Message"
                messageLabel.text = "Cipher"
                bruteforce.isEnabled = true;
            }
        }

        bruteforce.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                encode.isChecked = false;
                encode.isEnabled = false;
            } else {
                encode.isEnabled = true;
            }
        }

        val submit = this.findViewById<Button>(R.id.submit)
        val result = this.findViewById<TextView>(R.id.result)
        val message = this.findViewById<EditText>(R.id.message)
        val key = this.findViewById<EditText>(R.id.key)
        val identifiers = this.findViewById<TextView>(R.id.identifiers)

        identifiers.doAfterTextChanged {
            CaesarCipher.identifiers = identifiers.text.toString()
        }
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        submit.setOnClickListener { _ ->
            if (encode.isChecked) {
                val cipher = CaesarCipher.encrypt(
                    message.text.toString(),
                    key.text.toString().toInt()
                )
                result.text = cipher
                clipboardManager.setPrimaryClip(ClipData.newPlainText("cipher", cipher))
                Toast.makeText(this, "Copied to clipboard.", Toast.LENGTH_SHORT).show()
            } else {
                if (!bruteforce.isChecked) {
                    val text = CaesarCipher.decrypt(
                        message.text.toString(),
                        key.text.toString().toInt()
                    )
                    result.text = text
                    clipboardManager.setPrimaryClip(ClipData.newPlainText("message", text))
                    Toast.makeText(this, "Copied to clipboard.", Toast.LENGTH_SHORT).show()
                } else {
                    val messages = CaesarCipher.bruteforce(message.text.toString())
                    val intent = Intent(this, CaesarBruteforceResult::class.java)
                    for ((k, v) in messages) {
                        val item = BruteforceResultItem(k, v)
                        intent.putExtra("${k}_item", item);
                    }
                    startActivity(intent);
                }
            }
        }
    }
}