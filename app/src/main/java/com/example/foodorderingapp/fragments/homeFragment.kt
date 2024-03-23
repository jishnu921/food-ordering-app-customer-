package com.example.foodorderingapp.fragments

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.populearAdapter
import com.example.foodorderingapp.databinding.FragmentCartfragmentBinding
import com.example.foodorderingapp.databinding.FragmentHomeBinding
import com.example.foodorderingapp.datamodel.menuitemModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class homeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems:MutableList<menuitemModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        retrieveData()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3,ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList,ScaleTypes.FIT)

        imageSlider.setItemClickListener(object : ItemClickListener{
            override fun doubleClick(position: Int) {

            }

            override fun onItemSelected(position: Int) {
                val itemPostion = position
                Toast.makeText(requireContext(),""+itemPostion,Toast.LENGTH_SHORT).show()
            }

        })
        binding.ViewMenuHomeFragment.setOnClickListener(){
            val bottomSlideMenu = BottomSlideMenu()
            bottomSlideMenu.show(parentFragmentManager,"Test")
        }
    }
    private fun retrieveData() {
        //get retrieve to the data base
        database = FirebaseDatabase.getInstance()
        val foodRef : DatabaseReference= database.reference.child("menu")
        menuItems =  mutableListOf()

        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapShot in snapshot.children){
                    val menuitem = foodSnapShot.getValue(menuitemModel::class.java)
                    menuitem?.let {menuItems.add(it)}
                }
                //display random popular item
                randompopularItem()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun randompopularItem() {
        //creating a mixed up list from a ordered list of menu
        val index = menuItems.indices.toList().shuffled()
        val numitem =  5
        val subsetMenuItems = index.take(numitem).map { menuItems[it] }

        setPopulearItemAdapter(subsetMenuItems)
    }

    private fun setPopulearItemAdapter(subsetMenuItems: List<menuitemModel>) {
        val adapter = populearAdapter(subsetMenuItems,requireContext())
        binding.RecyclerViewHomeFragment.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerViewHomeFragment.adapter = adapter
    }

    companion object {
    }
}

