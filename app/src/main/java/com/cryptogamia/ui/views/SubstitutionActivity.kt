package com.cryptogamia.ui.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
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
import com.cryptogamia.utils.SubstitutionCipher

class SubstitutionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_substitution)


        supportActionBar?.hide()

        val back = this.findViewById<View>(R.id.backButton)
        back.setOnClickListener { _ ->
            finish()
        }

        val resultLabel = this.findViewById<TextView>(R.id.result_label)
        val messageLabel = this.findViewById<TextView>(R.id.text_label)

        val encode = this.findViewById<SwitchCompat>(R.id.encode)
        val analysis = this.findViewById<SwitchCompat>(R.id.analysis)

        encode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                resultLabel.text = "Cipher"
                messageLabel.text = "Message"
                analysis.isEnabled = false;
                analysis.isChecked = false;
            } else {
                resultLabel.text = "Message"
                messageLabel.text = "Cipher"
                analysis.isEnabled = true;
            }
        }

        analysis.setOnCheckedChangeListener { _, isChecked ->
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
            SubstitutionCipher.identifiers = identifiers.text.toString()
        }
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        key.setOnClickListener { _ ->
            run {
                key.setText(SubstitutionCipher.generateRandomCiphertext(SubstitutionCipher.identifiers));
            }
        }

        submit.setOnClickListener { _ ->
            if (encode.isChecked) {
                val cipher = SubstitutionCipher.encrypt(
                    message.text.toString(),
                    key.text.toString()
                )
                result.text = cipher
                clipboardManager.setPrimaryClip(ClipData.newPlainText("cipher", cipher))
                Toast.makeText(this, "Copied to clipboard.", Toast.LENGTH_SHORT).show()
            } else {
                if (analysis.isChecked) {
                    val intent = Intent(this, FrequencyAnalysis::class.java)
                    intent.putExtra("text", message.text.toString())
                    startActivity(intent)

                } else {
                    val cipher = SubstitutionCipher.decrypt(
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