package com.example.foodorderingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.adapters.populearAdapter
import com.example.foodorderingapp.databinding.FragmentSearchBinding
import com.example.foodorderingapp.datamodel.menuitemModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class searchFragment : Fragment() {
    lateinit var binding:FragmentSearchBinding

    private lateinit var database: FirebaseDatabase
    private lateinit var adapter : populearAdapter
    private val originalMenuItem = mutableListOf<menuitemModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)

        //retrieve menu item
        retrieveMenuItem()
        searnchPanel()

        return binding.root
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        // reference to the menu node
        val foodReference:DatabaseReference = database.reference.child("menu")
        foodReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapShot in snapshot.children){
                    val menuItem = foodSnapShot.getValue(menuitemModel::class.java)
                    menuItem?.let {
                        originalMenuItem.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun showAllMenu() {
        val filteredMenuItem = ArrayList(originalMenuItem)
        setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filtermenuItem: List<menuitemModel>) {
        adapter = populearAdapter(filtermenuItem,requireContext())
        binding.reyclerViewSearchFragment.layoutManager = LinearLayoutManager(requireContext())
        binding.reyclerViewSearchFragment.adapter = adapter
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
        val filteredMenuItem = originalMenuItem.filter {
            it.foodName?.contains(query, ignoreCase = true) == true
        }
        setAdapter(filteredMenuItem)
    }
    companion object {
    }
}