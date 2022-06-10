package com.rickandmorty.ui.characterlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class CharacterHomeFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val mViewModelCharacter: CharacterViewModel by activityViewModels()
    private var page: Int = 1
    private val binding get() = _binding!!

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
        val adapter = CharactesrAdapter()
        binding.rvCharacter.adapter = adapter
        binding.rvCharacter.layoutManager = LinearLayoutManager(context)

            //getList DataBase
            mViewModelCharacter.characterLiveDataFromDataBase.observe(viewLifecycleOwner){
            it?.let {
                Log.d("listResultFirsFragment", "$it")
                adapter.update(it.toMutableList())
            }
        }

        //capturar el onjeto al que se le dio click
        adapter.selectedItem().observe(viewLifecycleOwner){
            it.let {

                val bundle = Bundle()
                bundle.putInt("id",it.id)
                bundle.putString("name",it.name)
                bundle.putString("specie",it.species)
                bundle.putString("created",it.created)
                bundle.putString("img",it.image)
                bundle.putString("genre",it.gender)



                Log.d("tagselec", it.id.toString())
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
            }

        }



     /*  //buscador por nombre asociar adapter
        mViewModelCharacter.searchDataByName("Doofus")

         mViewModelCharacter.allCharacterDatafromNet.observe(viewLifecycleOwner) {

    Log.d("DATOSFirstBuscador", "$it")
}

       *//* binding.buttonFirst.setOnClickListener {

        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}