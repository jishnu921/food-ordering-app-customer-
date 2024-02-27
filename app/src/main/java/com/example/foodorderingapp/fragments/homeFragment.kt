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


class homeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
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
        val PopularFoodName = listOf("burger","sandwich","fry","cake")
        val PopulearFoodPrice = listOf("Rs110","Rs60","Rs65","Rs60")
        val PopulearFoodImage = listOf(R.drawable.burger, R.drawable.sandwich, R.drawable.frys, R.drawable.cake)
        val adapter = populearAdapter(PopularFoodName as MutableList<String>,PopulearFoodPrice as MutableList<String>,PopulearFoodImage as MutableList<Int>,requireContext())
        binding.RecyclerViewHomeFragment.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerViewHomeFragment.adapter = adapter
    }

    companion object {
    }
}

