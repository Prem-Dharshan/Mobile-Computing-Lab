package com.example.finals

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finals.db.Database
import com.example.finals.db.User
import java.util.Calendar

class UpdateForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_form)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getLong("userId", -1)

        val db = Database(this)
        val user = db.getUserById(userId)

        val userIdEditText = findViewById<TextView>(R.id.userIdTextView)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val dobEditText = findViewById<EditText>(R.id.dateEditText)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar3)
        val nextBtn = findViewById<Button>(R.id.nextBtn3)

        progressBar.progress = 75

        userIdEditText.text = "User Id: $userId"

        val calendar = Calendar.getInstance()
        dobEditText.setOnClickListener {
            DatePickerDialog(this, { _, y, m, d ->
                val date = "$y-${m + 1}-$d"
                if (y in 2000..2020) dobEditText.setText(date)
                else Toast.makeText(this, "Select a date between 2000-2020", Toast.LENGTH_SHORT).show()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Populate fields with existing user data
        user?.let {
            nameEditText.setText(it.name)
            emailEditText.setText(it.email)
            dobEditText.setText(it.dob)
        }

        nextBtn.setOnClickListener {
            val updatedName = nameEditText.text.toString().trim()
            val updatedEmail = emailEditText.text.toString().trim()
            val updatedDob = dobEditText.text.toString().trim()

            if (updatedName.isEmpty() || updatedEmail.isEmpty() || updatedDob.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedUser = User(userId, updatedName, updatedEmail, updatedDob)

            val success = db.updateUser(updatedUser)
            if (success) {
                Toast.makeText(this, "Updated successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DeleteForm::class.java))
            } else {
                Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}