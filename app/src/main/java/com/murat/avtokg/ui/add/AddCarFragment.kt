package com.murat.avtokg.ui.add
import  android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.murat.avtokg.R
import com.murat.avtokg.databinding.FragmentAddCarBinding
import com.murat.avtokg.db.CarEntity
import com.murat.avtokg.repository.DbRepository
import com.murat.avtokg.ui.brandfragment.Brand
import com.murat.avtokg.utils.Constants.BRAND
import com.murat.avtokg.utils.Constants.BRAND_REQUEST_CODE
import com.murat.avtokg.utils.Constants.CAR_ENTITY
import com.murat.avtokg.utils.Constants.CAR_MODEL
import com.murat.avtokg.utils.Constants.CAR_MODELS
import com.murat.avtokg.utils.Constants.CAR_MODEL_REQUEST_CODE
import com.murat.avtokg.utils.loadImage
import com.murat.avtokg.viewmodel.CarViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddCarFragment : Fragment() {

    private lateinit var binding: FragmentAddCarBinding
    var entity: CarEntity = CarEntity()
    private val viewModel: CarViewModel by viewModels()

    @Inject
    lateinit var repository: DbRepository
    private var brand: Brand? = null

    private var galleryActivityLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { result ->
            if (result != null) {
                binding.imgCar.loadImage(result.toString())
                entity.image = result.toString()
            } else {
                Log.d("lol", "onActivityResult: the result is null for some reason")
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCarBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentResult()
        setupView()


          with(binding) {
              arguments?.let {
                  val data = it.getSerializable(CAR_ENTITY)
                  if (data != null){
                     entity = data  as CarEntity
                  }
                  imgCar.loadImage(entity.image)
                  tvModel.text = entity.model
                  tvBrand.text = entity.brand
                  etBody.setText(entity.body)
                  etPrice.setText(entity.price)
                  etSeriesYear.setText(entity.seriesYear)
                  binding.saveCar.text = "Update"
                  viewModel.updateCar(entity)
              }
          } }

    override fun onResume() {
        super.onResume()
            binding.apply {
                etBody.setText(entity.body)
                tvBrand.text = entity.brand
                tvModel.text = entity.model
                imgCar.loadImage(entity.image)
                etPrice.setText(entity.price)
                etSeriesYear.setText(entity.seriesYear)
            }
    }


    private fun setupView() {

        binding.apply {
            saveCar.setOnClickListener {
                saveCar()
            }

            tvBrand.setOnClickListener {
                findNavController().navigate(R.id.brandFragment)
            }

            tvModel.setOnClickListener {
                if (brand != null) {
                    findNavController().navigate(
                        R.id.modelFragment,
                        bundleOf(CAR_MODELS to brand?.models)
                    )
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.brand_empty_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            imgCar.setOnClickListener { galleryActivityLauncher.launch(arrayOf("image/*")) }

        }
    }



    private fun saveCar(){
        if (isActiveButton()) {
            entity.body = binding.etBody.text.toString()
            entity.price = binding.etPrice.text.toString()
            entity.seriesYear = binding.etSeriesYear.text.toString()
            repository.saveCar(entity)
            requireActivity().supportFragmentManager.popBackStack()
        }else{
            Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
        }

    }
    private fun fragmentResult() {
        setFragmentResultListener(BRAND_REQUEST_CODE) { _, bundle ->
            if (bundle.containsKey(BRAND)) {
                brand = bundle.get(BRAND) as Brand
                binding.tvBrand.text = brand?.name
                binding.tvModel.text = null
                entity.model = null
                entity.brand = brand?.name
            }
        }

        setFragmentResultListener(CAR_MODEL_REQUEST_CODE) { _, bundle ->
            if (bundle.containsKey(CAR_MODEL)) {
                val model = bundle.get(CAR_MODEL) as String
                binding.tvModel.text = model
                entity.model = model
            }
        }
    }

    private fun isActiveButton() : Boolean{
        return !entity.brand.isNullOrEmpty() && !entity.model.isNullOrEmpty() && !entity.image.isNullOrEmpty()
                && !binding.etBody.text.isNullOrEmpty() && !binding.etPrice.text.isNullOrEmpty()
                && !binding.etSeriesYear.text.isNullOrEmpty()
    }

}