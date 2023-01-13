package com.murat.avtokg.ui.modelfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.murat.avtokg.adapter.ModelAdapter
import com.murat.avtokg.databinding.FragmentModelBinding
import com.murat.avtokg.utils.Constants.CAR_MODEL
import com.murat.avtokg.utils.Constants.CAR_MODELS
import com.murat.avtokg.utils.Constants.CAR_MODEL_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModelFragment : Fragment(),ModelAdapter.OnItemClickListener {

    private lateinit var binding: FragmentModelBinding
    private var modelList = ArrayList<String>()
    private lateinit var modelAdapter : ModelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModelBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val brands : ArrayList<String>? = arguments?.getSerializable(CAR_MODELS) as ArrayList<String>?

        if (brands != null) {
            modelList = brands
            if (modelList.isNotEmpty()){
                modelAdapter = ModelAdapter(modelList,this)
                binding.rvModel.adapter = modelAdapter
            }
        }


    }
    override fun onItemClick(carModel : String) {
        setFragmentResult(CAR_MODEL_REQUEST_CODE, bundleOf(CAR_MODEL to carModel))
        requireActivity().supportFragmentManager.popBackStack()


    }

}