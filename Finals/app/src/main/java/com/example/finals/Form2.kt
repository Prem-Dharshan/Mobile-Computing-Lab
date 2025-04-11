package com.example.finals

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Form2 : AppCompatActivity() {

    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var radioGroup: RadioGroup
    private lateinit var spinner: Spinner
    private lateinit var ratingBar: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI components
        checkBox1 = findViewById(R.id.checkBox1)
        checkBox2 = findViewById(R.id.checkBox2)
        checkBox3 = findViewById(R.id.checkBox3)
        radioGroup = findViewById(R.id.radioGroup)
        spinner = findViewById(R.id.spinner)
        ratingBar = findViewById(R.id.ratingBar)

        // Setup Spinner
        val options = arrayOf("Select an option", "Item 1", "Item 2", "Item 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)
        spinner.adapter = adapter

        // Load saved preferences
        loadPreferences()

        // Listeners to save data
        checkBox1.setOnCheckedChangeListener { _, _ -> savePreferences() }
        checkBox2.setOnCheckedChangeListener { _, _ -> savePreferences() }
        checkBox3.setOnCheckedChangeListener { _, _ -> savePreferences() }
        radioGroup.setOnCheckedChangeListener { _, _ -> savePreferences() }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                savePreferences()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        ratingBar.setOnRatingBarChangeListener { _, _, _ -> savePreferences() }

        val submitBtn: Button = findViewById(R.id.inpSubmit)

        submitBtn.setOnClickListener {
            startActivity(Intent(this, ViewInputs::class.java))
        }

    }

    private fun savePreferences() {
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE).edit()
        prefs.putBoolean("checkBox1", checkBox1.isChecked)
        prefs.putBoolean("checkBox2", checkBox2.isChecked)
        prefs.putBoolean("checkBox3", checkBox3.isChecked)

        val selectedRadioId = radioGroup.checkedRadioButtonId
        prefs.putInt("radioButton", selectedRadioId)

        prefs.putInt("spinner", spinner.selectedItemPosition)
        prefs.putFloat("ratingBar", ratingBar.rating)

        prefs.apply()
    }

    private fun loadPreferences() {
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        checkBox1.isChecked = prefs.getBoolean("checkBox1", false)
        checkBox2.isChecked = prefs.getBoolean("checkBox2", false)
        checkBox3.isChecked = prefs.getBoolean("checkBox3", false)

        val selectedRadioId = prefs.getInt("radioButton", -1)
        if (selectedRadioId != -1) {
            radioGroup.check(selectedRadioId)
        }

        spinner.setSelection(prefs.getInt("spinner", 0))
        ratingBar.rating = prefs.getFloat("ratingBar", 0.0f)
    }
}