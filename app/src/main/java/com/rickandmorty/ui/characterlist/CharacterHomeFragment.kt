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
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentFirstBinding
import com.rickandmorty.viewmodels.CharacterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.math.log

class CharacterHomeFragment : Fragment() ,SearchView.OnQueryTextListener{

    private var _binding: FragmentFirstBinding? = null
    private val mViewModelCharacter: CharacterViewModel by activityViewModels()
    private var page: Int = 1
    private val binding get() = _binding!!
    val adapter = CharactesrAdapter()
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

        binding.rvCharacter.adapter = adapter
        binding.rvCharacter.layoutManager = LinearLayoutManager(context)

            //getList DataBase
            mViewModelCharacter.characterLiveDataFromDataBase.observe(viewLifecycleOwner){
            it?.let {
                Log.d("listResultFirsFragment", "$it")
                adapter.update(it.toMutableList())
            }
        }
        mViewModelCharacter.characterLiveDataByName.observe(viewLifecycleOwner) {
            adapter.update(it.toMutableList())
        }


        //capturar el onjeto al que se le dio click
        adapter.selectedItem().observe(viewLifecycleOwner) {
            it.let {

                val bundle = Bundle()
                bundle.putInt("id", it.id)
                bundle.putString("name", it.name)
                bundle.putString("specie", it.species)
                bundle.putString("created", it.created)
                bundle.putString("img", it.image)
                bundle.putString("genre", it.gender)

                Log.d("tagselec", it.id.toString())
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }

        }}

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