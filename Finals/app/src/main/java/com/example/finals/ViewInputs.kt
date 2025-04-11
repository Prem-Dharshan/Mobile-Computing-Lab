package com.example.finals

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ViewInputs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_inputs)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvStoredData: TextView = findViewById(R.id.tvStoredData)
        val btnBack: Button = findViewById(R.id.btnBack)

        // Load stored preferences
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        val checkBox1 = if (prefs.getBoolean("checkBox1", false)) "✔ Option 1" else "✘ Option 1"
        val checkBox2 = if (prefs.getBoolean("checkBox2", false)) "✔ Option 2" else "✘ Option 2"
        val checkBox3 = if (prefs.getBoolean("checkBox3", false)) "✔ Option 3" else "✘ Option 3"

        val selectedRadioId = prefs.getInt("radioButton", -1)
        val radioChoice = when (selectedRadioId) {
            R.id.radioButton1 -> "Choice A"
            R.id.radioButton2 -> "Choice B"
            R.id.radioButton3 -> "Choice C"
            else -> "No Selection"
        }

        val spinnerSelection = when (prefs.getInt("spinner", 0)) {
            1 -> "Item 1"
            2 -> "Item 2"
            3 -> "Item 3"
            else -> "No Selection"
        }

        val rating = prefs.getFloat("ratingBar", 0.0f)

        // Display stored values
        val storedData = """
            Checkboxes:
            $checkBox1
            $checkBox2
            $checkBox3
            
            Selected Radio Button: $radioChoice
            
            Dropdown Selection: $spinnerSelection
            
            Rating: ⭐ $rating/5
        """.trimIndent()

        tvStoredData.text = storedData

        // Navigate back to MainActivity
        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}