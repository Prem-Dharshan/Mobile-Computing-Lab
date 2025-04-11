package com.example.finals

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ImageForm : AppCompatActivity() {

    private var isOriginalImage = true  // Tracks which image is showing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image_form)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val foregroundImage = findViewById<ImageView>(R.id.foregroundImage)
        val changeImageBtn = findViewById<Button>(R.id.changeImageBtn)

        changeImageBtn.setOnClickListener {
            if (isOriginalImage) {
                foregroundImage.setImageResource(R.drawable.ic_launcher_foreground)
            } else {
                foregroundImage.setImageResource(R.drawable.foreground_img)
            }
            isOriginalImage = !isOriginalImage  // Toggle the flag
        }
    }
}
