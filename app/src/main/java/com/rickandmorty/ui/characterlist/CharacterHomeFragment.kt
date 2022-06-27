package com.rickandmorty.ui.characterlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView

import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentFirstBinding
import com.rickandmorty.viewmodels.CharacterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.log

class CharacterHomeFragment : Fragment() ,SearchView.OnQueryTextListener{

    private var _binding: FragmentFirstBinding? = null
    private val mViewModelCharacter: CharacterViewModel by activityViewModels()
    private var page: Int = 1
    private val binding get() = _binding!!
    val adapter = CharactesrAdapter()
    val adapterPage = PageCharacterAdapter()
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

        //observer live data
       /* lifecycleScope.launch {
            mViewModelCharacter.characterLiveDataByName.observe(viewLifecycleOwner) {
                Log.d("listResultFirsFragment", "$it")
                adapterPage.submitData(viewLifecycleOwner.lifecycle, it)
            }

        }*/
        lifecycleScope.launch {
            mViewModelCharacter.characterPageFlow().collectLatest {
                adapterPage.submitData(viewLifecycleOwner.lifecycle, it)

            }}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        mViewModelCharacter.searchDataByName(query)
        //buscador por nombre asociar adapter

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

}