package com.chhabi.countrycapital

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintStream

class MainActivity : AppCompatActivity(){

    private lateinit var et1: EditText
    private lateinit var et2:EditText
    private lateinit var btn: Button
    private lateinit var lst: ListView
    private var countryCapitalMap= mutableMapOf<String,String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        btn = findViewById(R.id.btn)
        lst = findViewById(R.id.lst)

        loadCountriesFromText()

        btn.setOnClickListener {
        addCountryToText()
            loadCountriesFromText()
            et1.text.clear()
            et2.text.clear()

        }
    }

        private fun addCountryToText(){
            try{
                val et1= et1.text.toString()
                val et2= et2.text.toString()
                val printStream= PrintStream(
                    openFileOutput(
                        "Country.txt",
                MODE_APPEND
                    )
                )
                printStream.println("$et1 -> $et2")
                Toast.makeText(this,"$et1 saved", Toast.LENGTH_SHORT).show()
            } catch (e:IOException){
                Toast.makeText(this,"Error${e.localizedMessage}",Toast.LENGTH_SHORT).show()
            }
        }



    private fun loadCountriesFromText()
    {
        try {
            val fileInputStream= openFileInput("Country.txt")
            val inputStream= InputStreamReader(fileInputStream)
            val bufferedReader=BufferedReader(inputStream)
            for(line in bufferedReader.lines())
            {
                val countryCapital= line.split("->")
                val et1= countryCapital[0]
                val et2= countryCapital[1]
                countryCapitalMap[et1]= et2
            }
            displayCountries(countryCapitalMap)
        }catch (e:IOException){
            Toast.makeText(this,"Error ${e.localizedMessage}",Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayCountries( countryCapitalMap:MutableMap<String,String>){
        val adapter= ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            countryCapitalMap.keys.toTypedArray()
        )
        lst.adapter= adapter

    }



    }





