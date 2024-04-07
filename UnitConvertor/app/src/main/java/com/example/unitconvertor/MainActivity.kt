package com.example.unitconvertor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ArrayAdapter.createFromResource
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private lateinit var mainSpinner: Spinner
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var adapterMainSpinner: ArrayAdapter<CharSequence>
    private lateinit var adapterSpinner1: ArrayAdapter<String>
    private lateinit var adapterSpinner2: ArrayAdapter<String>
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private var previousSelectedItemSpinner1: Int = 0
    private var previousSelectedItemSpinner2: Int = 0
    private var userChange:Boolean = false
    private var isSpinner1Active: Boolean = false
    private var isSpinner2Active: Boolean = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate")
        setContentView(R.layout.activity_main)
        mainSpinner = findViewById(R.id.spinner)
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        adapterMainSpinner = createFromResource(
            this,
            R.array.your_array_resource,

            android.R.layout.simple_spinner_item
        )
        adapterSpinner1 = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapterSpinner2 = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        mainSpinner.adapter = adapterMainSpinner
        spinner1.adapter = adapterSpinner1
        spinner2.adapter = adapterSpinner2

        mainSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()


                    val entriesArraySpinner1: Int = when (selectedItem) {
                        "Area" -> R.array.Area
                        "Length" -> R.array.Length
                        "Volume" -> R.array.Volume
                        "Time" -> R.array.Time
                        else -> R.array.Area // Default or handle other cases as needed
                    }

                    val newEntriesArraySpinner1 = resources.getStringArray(entriesArraySpinner1)
                    adapterSpinner1.clear()
                    adapterSpinner1.addAll(newEntriesArraySpinner1.toList())
                    adapterSpinner1.notifyDataSetChanged()

                    val entriesArraySpinner2: Int = when (selectedItem) {
                        "Area" -> R.array.Area
                        "Length" -> R.array.Length
                        "Volume" -> R.array.Volume
                        "Time" -> R.array.Time
                        else -> R.array.Area // Default or handle other cases as needed
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


                override fun onNothingSelected(parent: AdapterView<*>?) {

                }


            }
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {

                Log.d("Spinner1", "onItemSelected1: position=$position")
                if (spinner1.selectedItem?.toString() == spinner2.selectedItem?.toString()) {

                    spinner2.setSelection(previousSelectedItemSpinner1)
                    Log.i(tag, "spinner2: ")

                }

                    Log.i(tag, "spinner1_inside " + editText2.text.toString())
                    isSpinner1Active=true
                    editText1.setText(calculate(editText2.text.toString(), false))
                    isSpinner1Active=false
                    Log.i(tag, "output by using spinner1_editText1" + editText1.text.toString())
                    Log.i(tag, "output by using spinner1_editText2" + editText1.text.toString())




                previousSelectedItemSpinner1 = spinner1.selectedItemPosition
                previousSelectedItemSpinner2 = spinner2.selectedItemPosition

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("Spinner1", "onNothingSelected")
            }


        }
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                Log.d("Spinner2", "onItemSelected2: position=$position")
                if (spinner1.selectedItem?.toString() == spinner2.selectedItem?.toString()) {

                    spinner1.setSelection(previousSelectedItemSpinner2)
                    Log.i(tag, "spinner1: ")

                }
                Log.i(tag,"spinner2_inside "+editText1.text.toString())
                isSpinner2Active=true
                editText2.setText(calculate(editText1.text.toString(),true))
                isSpinner2Active=false
                Log.i(tag,"output by using spinner2_editText2 " +editText2.text.toString())
                Log.i(tag,"output by using spinner2_editTex1 " +editText1.text.toString())
                previousSelectedItemSpinner1 = spinner1.selectedItemPosition
                previousSelectedItemSpinner2 = spinner2.selectedItemPosition




            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("Spinner2", "onNothingSelected")
            }

        }


        editText1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                Log.i(tag, "Spinner1 is Active $isSpinner1Active")
                Log.i(tag, "Enitit ${editText2.text}")
                if (!userChange&&!isSpinner1Active) {
                         Log.d(tag,"Entered in editText1 " )
                     userChange=true
                    editText2.setText(calculate(editText1.text.toString(), true))
                    Log.d(tag,"Entered in editText1_1 "+editText2.text.toString())
                    userChange=false
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        editText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i(tag, "Spinner2 is Active $isSpinner2Active")
                if (!userChange&& !isSpinner2Active ) {
                    Log.d(tag,"Entered in editText2 " )
                       userChange=true
                    editText1.setText(calculate(editText2.text.toString(), false))
                    Log.d(tag,"Entered in editText2_1 "+editText1.text.toString() )
                    userChange=false
                }
            }

            override fun afterTextChanged(s: Editable?) {


            }
        })
    }



            fun calculate(inputText: String, fromEdit1: Boolean): String {

                Log.d(tag, "input Text_calculate() $inputText")


                var inputValue: Double = inputText.trim().toDoubleOrNull() ?:  return " "
                Log.d(tag, "Entered value $inputValue")

                val fromUnit = spinner1.selectedItem.toString()
                val toUnit = spinner2.selectedItem.toString()
                Log.d(tag,"Enter in calculate")

                return if (fromEdit1) {
                    Log.d(tag, "Enter in Edit1 $inputValue")
                    Calculation.convert1(inputValue, fromUnit, toUnit)

                } else {
                    Log.d(tag, "Enter in Edit2 $inputValue")
                    Calculation.convert2(inputValue, fromUnit, toUnit)
                }
            }


}










