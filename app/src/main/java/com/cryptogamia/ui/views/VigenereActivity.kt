package com.cryptogamia.ui.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.doAfterTextChanged
import com.cryptogamia.R
import com.cryptogamia.utils.VigenereCipher

class VigenereActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vigenere)


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
            VigenereCipher.identifiers = identifiers.text.toString()
        }
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        submit.setOnClickListener { _ ->
            if (encode.isChecked) {
                val cipher = VigenereCipher.encrypt(
                    message.text.toString(),
                    key.text.toString()
                )
                if (!MainActivity.words.contains(key.text.toString().lowercase())) {
                    MainActivity.words.add(key.text.toString().lowercase())
                }

                val kr = message.text.toString().split(" ")
                for (k in kr) {
                    if (!MainActivity.words.contains(k.lowercase())) {
                        MainActivity.words.add(k.lowercase())
                    }
                }

                result.text = cipher
                clipboardManager.setPrimaryClip(ClipData.newPlainText("cipher", cipher))
                Toast.makeText(this, "Copied to clipboard.", Toast.LENGTH_SHORT).show()
            } else {
                if (key.text.toString().isEmpty() && bruteforce.isChecked) {
                    var best = arrayListOf<Map<String, String>>()
                    for (k in MainActivity.words) {
                        val cipher = VigenereCipher.decrypt(
                            message.text.toString(),
                            k
                        )

                        var i = 0
                        val p = cipher.split(" ")
                        for (kk in p) {
                            if (MainActivity.words.contains(kk.lowercase())) {
                                i += 1
                            }
                        }

                        best.add(mapOf("key" to k, "freq" to "$i"))
                    }

                    val bestResult = best.sortedBy { it["freq"]?.toInt() }
                    key.setText(bestResult[bestResult.size-1]["key"])
                    result.text = bestResult[bestResult.size-1]["key"]?.let {
                        VigenereCipher.decrypt(message.text.toString(),
                            it
                        )
                    };
                } else {
                    if (key.text.isEmpty()) {
                        Toast.makeText(this, "Key must be set", Toast.LENGTH_SHORT).show()
                    } else {
                        val cipher = VigenereCipher.decrypt(
                            message.text.toString(),
                            key.text.toString()
                        )
                        result.text = cipher
                        clipboardManager.setPrimaryClip(ClipData.newPlainText("cipher", cipher))
                        Toast.makeText(this, "Copied to clipboard.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun onFinish() {
        println("FINISH")
    }
}