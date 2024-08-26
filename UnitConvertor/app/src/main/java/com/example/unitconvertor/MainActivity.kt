package com.example.unitconvertor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainSpinner: Spinner
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var adapterMainSpinner: ArrayAdapter<CharSequence>
    private lateinit var adapterSpinner1: ArrayAdapter<String>
    private lateinit var adapterSpinner2: ArrayAdapter<String>

    private var previousSelectedItemSpinner1: Int = 0
    private var previousSelectedItemSpinner2: Int = 0
    private var isSpinner1Active: Boolean = false
    private var isSpinner2Active: Boolean = false
    private var userChange: Boolean = false

    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate")
        setContentView(R.layout.activity_main)

        // Initialize the views
        mainSpinner = findViewById(R.id.spinner)
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        editText1.isVerticalScrollBarEnabled = false
        editText2.isVerticalScrollBarEnabled = false
        // Initialize the adapters
        adapterMainSpinner = ArrayAdapter.createFromResource(this, R.array.your_array_resource, R.layout.color_spinner_layout)
        adapterSpinner1 = ArrayAdapter(this, R.layout.color_spinner_layout)
        adapterSpinner2 = ArrayAdapter(this, R.layout.color_spinner_layout)


        // Set the adapters
        mainSpinner.adapter = adapterMainSpinner
        spinner1.adapter = adapterSpinner1
        spinner2.adapter = adapterSpinner2

        // Set the listeners
        setListeners()
    }

    private fun setListeners() {
        mainSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()

                val entriesArraySpinner1 = when (selectedItem) {
                    "Area" -> R.array.Area
                    "Length" -> R.array.Length
                    "Volume" -> R.array.Volume
                    "Time" -> R.array.Time
                    else -> R.array.Area
                }

                val newEntriesArraySpinner1 = resources.getStringArray(entriesArraySpinner1)
                adapterSpinner1.clear()
                adapterSpinner1.addAll(newEntriesArraySpinner1.toList())
                adapterSpinner1.notifyDataSetChanged()

                val entriesArraySpinner2 = when (selectedItem) {
                    "Area" -> R.array.Area
                    "Length" -> R.array.Length
                    "Volume" -> R.array.Volume
                    "Time" -> R.array.Time
                    else -> R.array.Area
                }

                val newEntriesArraySpinner2 = resources.getStringArray(entriesArraySpinner2)
                adapterSpinner2.clear()
                adapterSpinner2.addAll(newEntriesArraySpinner2.toList())
                adapterSpinner2.notifyDataSetChanged()

                spinner1.adapter = adapterSpinner1
                spinner2.adapter = adapterSpinner2

                spinner2.setSelection(spinner1.selectedItemPosition + 1)
                editText1.text.clear()
                editText2.text.clear()
                previousSelectedItemSpinner1 = spinner1.selectedItemPosition
                previousSelectedItemSpinner2 = spinner2.selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                if (spinner1.selectedItem.toString() == spinner2.selectedItem.toString()) {
                    spinner2.setSelection(previousSelectedItemSpinner1)
                }

                isSpinner1Active = true
                editText1.setText(calculate(editText2.text.toString(), false))
                isSpinner1Active = false

                previousSelectedItemSpinner1 = spinner1.selectedItemPosition
                previousSelectedItemSpinner2 = spinner2.selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (spinner1.selectedItem.toString() == spinner2.selectedItem.toString()) {
                    spinner1.setSelection(previousSelectedItemSpinner2)
                }

                isSpinner2Active = true
                editText2.setText(calculate(editText1.text.toString(), true))
                isSpinner2Active = false

                previousSelectedItemSpinner1 = spinner1.selectedItemPosition
                previousSelectedItemSpinner2 = spinner2.selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        editText1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!userChange && !isSpinner1Active) {
                    userChange = true
                    editText2.setText(calculate(editText1.text.toString(), true))
                    userChange = false
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        editText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!userChange && !isSpinner2Active) {
                    userChange = true
                    editText1.setText(calculate(editText2.text.toString(), false))
                    userChange = false
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun calculate(inputText: String, fromEdit1: Boolean): String {
        val inputValue: Double = inputText.trim().toDoubleOrNull() ?: return ""
        val fromUnit = spinner1.selectedItem.toString()
        val toUnit = spinner2.selectedItem.toString()

        return if (fromEdit1) {
            Calculation.convert1(inputValue, fromUnit, toUnit)
        } else {
            Calculation.convert2(inputValue, fromUnit, toUnit)
        }
    }
}















