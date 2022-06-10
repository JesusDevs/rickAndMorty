package com.rickandmorty.ui.characterlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.FragmentFirstBinding
import com.rickandmorty.viewmodels.CharacterViewModel

class CharacterHomeFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val mViewModelCharacter: CharacterViewModel by activityViewModels()

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


        //get desde DB roomh
        mViewModelCharacter.characterLiveDataFromDataBase.observe(viewLifecycleOwner){
            it?.let {
                Log.d("LISTADO", "$it")
                adapter.update(it)
            }
        }


       //buscador por nombre asociar adapter
        mViewModelCharacter.searchDataByName("Doofus")

         mViewModelCharacter.allCharacterDatafromNet.observe(viewLifecycleOwner) {

    Log.d("DATOSFirstBuscador", "$it")
}

       /* binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}