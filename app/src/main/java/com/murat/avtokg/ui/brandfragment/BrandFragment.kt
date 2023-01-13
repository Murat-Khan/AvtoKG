package com.murat.avtokg.ui.brandfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.findNavController
import com.murat.avtokg.R

import com.murat.avtokg.adapter.BrandAdapter
import com.murat.avtokg.databinding.FragmentBrandBinding
import com.murat.avtokg.db.CarDatabase
import com.murat.avtokg.db.CarEntity
import com.murat.avtokg.utils.Constants.BRAND
import com.murat.avtokg.utils.Constants.BRAND_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BrandFragment : Fragment(),BrandAdapter.OnItemClickListener {
    lateinit var binding: FragmentBrandBinding
    @Inject
    lateinit var noteEntity: CarEntity

    @Inject
    lateinit var db : CarDatabase

    private lateinit var brandAdapter : BrandAdapter
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBrandBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        brandAdapter = BrandAdapter(this)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvBrand.adapter = brandAdapter
    }

    override fun onItemClick(brand: Brand) {
        setFragmentResult(BRAND_REQUEST_CODE, bundleOf(BRAND to brand))
        requireActivity().supportFragmentManager.popBackStack()
    }


}