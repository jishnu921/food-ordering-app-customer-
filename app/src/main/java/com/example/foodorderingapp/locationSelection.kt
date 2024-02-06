package com.example.foodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.foodorderingapp.databinding.ActivityLocationSelectionBinding

class locationSelection : AppCompatActivity() {
    private val binding:ActivityLocationSelectionBinding by lazy {
        ActivityLocationSelectionBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val loctionList : Array<String> = arrayOf("agra","mysore","banglore","goa")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,loctionList)
        val autoCompleteTextView : AutoCompleteTextView = binding.locationListofcity
        autoCompleteTextView.setAdapter(adapter)
    }
}