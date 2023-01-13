package com.murat.avtokg.ui.homefragment
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.murat.avtokg.R
import com.murat.avtokg.adapter.CarAdapter
import com.murat.avtokg.databinding.FragmentHomeBinding
import com.murat.avtokg.db.CarDatabase
import com.murat.avtokg.db.CarEntity
import com.murat.avtokg.repository.DbRepository
import com.murat.avtokg.utils.Constants.ALL_CARS
import com.murat.avtokg.utils.Constants.CAR_ENTITY
import com.murat.avtokg.utils.Constants.EDIT
import com.murat.avtokg.utils.Constants.MERCEDES_BENZ
import com.murat.avtokg.utils.Constants.TOYOTA
import com.murat.avtokg.utils.Constants.VOLKSWAGEN
import com.murat.avtokg.viewmodel.CarViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment()  {
    lateinit var binding: FragmentHomeBinding
    @Inject
    lateinit var repository: DbRepository

    @Inject
    lateinit var db : CarDatabase
    var carAdapter = CarAdapter()
    private var selectedItem = 0

    @Inject
    lateinit var carEntity: CarEntity

    private val carViewModel: CarViewModel by viewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate( inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        initClicker()
        editCar()
        setupViewModel()

    }

    private fun setupViewModel(){
        carViewModel.allCarData.observe(viewLifecycleOwner){
            binding.listEmptyLayout.isVisible = it.isNullOrEmpty()
            carAdapter.setCarList(it)
        }
        carViewModel.getAllCars()
    }

    private fun editCar() {
       carAdapter.setOnItemClickListener { carEntity, type ->

           when(type){
               EDIT ->{
                   findNavController().navigate(R.id.addCarFragment,bundleOf(CAR_ENTITY to carEntity))
               }

           }
       }
    }

    private fun filterByBrand() {
        val builder = AlertDialog.Builder(requireContext())
         val brand = arrayOf(ALL_CARS, MERCEDES_BENZ, VOLKSWAGEN,TOYOTA)
         builder.setSingleChoiceItems(brand,selectedItem){ dialog, item ->
             when(item){
                 0->{
                    carViewModel.getAllCars()
                 }
                 in 1..3 ->{
                     carViewModel.getFilterCar(brand[item])
                 }
             }
             selectedItem = item
             dialog.dismiss()
         }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun setupRecyclerView() {
        binding.rvCarList.adapter = carAdapter
    }

    private fun initClicker() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.addCarFragment)
        }
    }
    private fun setupToolbar() {
        requireActivity().addMenuProvider(object :MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.brandFilter -> {
                        filterByBrand()
                        true
                    }else -> false
                }

            }},viewLifecycleOwner,Lifecycle.State.RESUMED)
    }



}