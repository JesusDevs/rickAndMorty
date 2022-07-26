package com.rickandmorty.ui.characterlist

import android.app.Application
import android.os.Bundle
import android.view.*

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.databinding.FragmentFirstBinding
import com.google.gson.Gson
import com.rickandmorty.model.database.CharacterDao
import com.rickandmorty.model.database.CharacterListRoom
import com.rickandmorty.repositories.CharacterRepository
import com.rickandmorty.viewmodels.CharacterViewModel
import com.rickandmorty.viewmodels.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.converter.gson.GsonConverterFactory

class CharacterHomeFragment() : Fragment(){

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    private val adapterPage = PageCharacterAdapter()
    var viewModelFrg : Any? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //instancia de adaptador listado desde room
        val repository= CharacterRepository(CharacterListRoom.getDataBase(requireActivity()).getCharacterDao())
        val viewModelProviderFactory= activity?.let { ViewModelFactory(it.application, repository) }
        val viewModel= viewModelProviderFactory?.let { ViewModelProvider(this, it) }?.get(CharacterViewModel::class.java)!!
        binding.rvCharacter.adapter = adapterPage
        binding.rvCharacter.layoutManager =GridLayoutManager(context,2)



        //observer con flow
        /*lifecycleScope.launch{ //cuando se inicia la actividad
            viewModel?.characterPageFlow()?.collect {
                adapterPage.submitData(viewLifecycleOwner.lifecycle.whenStarted { it })

            }
        }*/
/*

    lifecycleScope.launch(Dispatchers.Main){ //cuando se inicia la actividad
            viewModel?.characterPageFlow()?.collect {
                adapterPage.submitData(viewLifecycleOwner.lifecycle.whenStarted { it })

            }
        }
*/

        //observer con livedata and flow recomendado
       lifecycleScope.launch(Dispatchers.Main){
            viewModel?.characterPageFlow()?.flowWithLifecycle(viewLifecycleOwner.lifecycle,Lifecycle.State.STARTED)
                ?.collect {
                    adapterPage.submitData(it)
                }
            }


        /**
         * OPCIONES DE observer con coroutine
         */
        //observer live data con coroutine
        /* lifecycleScope.launch {
             mViewModelCharacter.characterLiveDataByName.observe(viewLifecycleOwner) {
                 Log.d("listResultFirsFragment", "$it")
                 adapterPage.submitData(viewLifecycleOwner.lifecycle, it)
             }

         }*/
       /*observer livedata query buscador
        lifecycleScope.launch {
            viewModel?.characterSearchLiveData("")?.observe(viewLifecycleOwner) { list->

                adapterPage.submitData(viewLifecycleOwner.lifecycle, list)
                Timber.d("aca timber ok")
            }

        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}