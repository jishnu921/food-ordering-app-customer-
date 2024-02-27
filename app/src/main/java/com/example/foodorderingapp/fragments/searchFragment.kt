package com.example.foodorderingapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.populearAdapter
import com.example.foodorderingapp.databinding.FragmentSearchBinding

class searchFragment : Fragment() {
    lateinit var binding:FragmentSearchBinding
    val filteredMenuName = mutableListOf<String>()
    val filteredMenuPrice = mutableListOf<String>()
    val filterMenuImage = mutableListOf<Int>()
    val SearchFoodName = listOf("burger","sandwich","fry","cake","burger","sandwich","fry","cake","cake")
    val SearchFoodPrice = listOf("Rs110","Rs60","Rs65","Rs60","Rs110","Rs60","Rs65","Rs60","80")
    val SearchFoodImage = listOf(
        R.drawable.burger, R.drawable.sandwich, R.drawable.frys, R.drawable.cake,
        R.drawable.burger, R.drawable.sandwich, R.drawable.frys, R.drawable.cake,R.drawable.cake)
    val adapter = populearAdapter(filteredMenuName,filteredMenuPrice,filterMenuImage,requireContext())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)

        binding.reyclerViewSearchFragment.layoutManager = LinearLayoutManager(requireContext())
        binding.reyclerViewSearchFragment.adapter=adapter

        searnchPanel()

        showallMenu()
        return binding.root
    }

    private fun showallMenu() {
        filteredMenuName.clear()
        filterMenuImage.clear()
        filteredMenuPrice.clear()

        filteredMenuName.addAll(SearchFoodName)
        filteredMenuPrice.addAll(SearchFoodPrice)
        filterMenuImage.addAll(SearchFoodImage)

        adapter.notifyDataSetChanged()
    }

    fun searnchPanel(){
        binding.searchViewSearchFragment.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuitem(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuitem(newText)
                return true
            }
        })
    }
    fun filterMenuitem(query: String) {
        filteredMenuName.clear()
        filterMenuImage.clear()
        filteredMenuPrice.clear()

        SearchFoodName.forEachIndexed{index, foodName ->
            if (foodName.contains(query,ignoreCase = true)){
                filteredMenuName.add(foodName)
                filteredMenuPrice.add(SearchFoodPrice[index])
                filterMenuImage.add(SearchFoodImage[index])
            }
        }
        adapter.notifyDataSetChanged()
    }
    companion object {

    }
}