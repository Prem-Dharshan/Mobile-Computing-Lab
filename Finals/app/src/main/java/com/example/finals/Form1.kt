package com.example.finals

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finals.db.Database
import java.util.Calendar


class Form1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form1)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = Database(this)
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        val nameInput = findViewById<EditText>(R.id.nameEditText)
        val emailInput = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val dobInput = findViewById<EditText>(R.id.dateEditText)
        val nextBtn = findViewById<Button>(R.id.nextBtn1)

        findViewById<ProgressBar>(R.id.progressBar1).progress = 25

        // Date picker setup
        val calendar = Calendar.getInstance()
        dobInput.setOnClickListener {
            DatePickerDialog(this, { _, y, m, d ->
                val date = "$y-${m + 1}-$d"
                if (y in 2000..2020) dobInput.setText(date)
                else showToast("Select a date between 2000-2020")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .show()
        }

        nextBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val dob = dobInput.text.toString().trim()

            when {
                name.isEmpty() || email.isEmpty() || dob.isEmpty() -> showToast("Please fill all fields")
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> showToast("Enter a valid email")
                else -> {
                    val userId = db.addUser(name, email, dob)
                    prefs.edit().putLong("userId", userId).apply()
                    startActivity(Intent(this, Form2::class.java))
                }
            }
        }
    }

    private fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

}