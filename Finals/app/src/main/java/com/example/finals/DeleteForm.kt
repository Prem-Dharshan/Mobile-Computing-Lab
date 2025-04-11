package com.example.finals

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finals.db.Database

class DeleteForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delete_form)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getLong("userId", -1)

        val userIdTextView = findViewById<TextView>(R.id.userIdTextView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar4)
        val submissionText = findViewById<TextView>(R.id.submittedTextView)
        val deleteButton = findViewById<Button>(R.id.deleteBtn)
        val backBtn = findViewById<Button>(R.id.backBtn)

        backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        userIdTextView.text = "User Id: $userId"
        progressBar.progress = 100
        submissionText.text = "Submitted!"


        deleteButton.setOnClickListener {
            if (userId == -1L) {
                Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                .setPositiveButton("Delete") { _, _ ->
                    val db = Database(this)
                    val success = db.deleteUser(userId)
                    if (success) {
                        prefs.edit().remove("userId").apply()
                        Toast.makeText(this, "User deleted successfully!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to delete user!", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}