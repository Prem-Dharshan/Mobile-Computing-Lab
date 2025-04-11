package com.example.finals

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var isLoading = false
        val startBtn: Button = findViewById(R.id.startBtn)
        val ctrlPanelBtn: Button = findViewById(R.id.ctrlPanelBtn)
        val imgBtn: Button = findViewById(R.id.imgBtn)
        val loader: ProgressBar = findViewById(R.id.loader)
        val handler = Handler(Looper.getMainLooper())

        ctrlPanelBtn.setOnClickListener {
            startActivity(Intent(this, ControlPanelActivity::class.java))
        }

        startBtn.setOnClickListener {
            if (!isLoading) {
                isLoading = true
                startBtn.text = "Loading"
                loader.visibility = ProgressBar.VISIBLE

                handler.postDelayed({
                    startActivity(Intent(this, Form1::class.java))
                    isLoading = false
                    startBtn.text = "Forms"
                    loader.visibility = ProgressBar.INVISIBLE
                }, 2000)
            }
        }

        imgBtn.setOnClickListener {
            startActivity(Intent(this, ImageForm::class.java))
        }
    }
}