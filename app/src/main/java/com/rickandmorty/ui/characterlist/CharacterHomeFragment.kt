package com.rickandmorty.ui.characterlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*

import androidx.appcompat.widget.SearchView

import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentFirstBinding
import com.google.gson.Gson
import com.rickandmorty.model.database.CharacterListRoom
import com.rickandmorty.repositories.CharacterRepository
import com.rickandmorty.viewmodels.CharacterViewModel
import com.rickandmorty.viewmodels.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import kotlin.math.log

class CharacterHomeFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    private val adapterPage = PageCharacterAdapter()
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
        binding.rvCharacter.adapter = adapterPage
        binding.rvCharacter.layoutManager = LinearLayoutManager(context)
        val repository= CharacterRepository(CharacterListRoom.getDataBase(requireActivity()).getCharacterDao())
        val viewModelProviderFactory= activity?.let { ViewModelFactory(it.application, repository) }
        val viewModel= viewModelProviderFactory?.let { ViewModelProvider(this, it) }?.get(CharacterViewModel::class.java)



        //observer con flow
        lifecycleScope.launch {
            viewModel?.characterPageFlow()?.collect {
                adapterPage.submitData(viewLifecycleOwner.lifecycle, it)

            }
        }

        /**
         * OPCIONES DE observer con coroutine
         */
        //observer live data
        /* lifecycleScope.launch {
             mViewModelCharacter.characterLiveDataByName.observe(viewLifecycleOwner) {
                 Log.d("listResultFirsFragment", "$it")
                 adapterPage.submitData(viewLifecycleOwner.lifecycle, it)
             }

         }*/
       /*observer livedata query
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