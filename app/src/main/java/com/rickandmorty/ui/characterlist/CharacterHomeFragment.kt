package com.rickandmorty.ui.characterlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.FragmentFirstBinding
import com.rickandmorty.viewmodels.CharacterViewModel
class CharacterHomeFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val mViewModelCharacter: CharacterViewModel by activityViewModels()
    private var page: Int = 1
    private val binding get() = _binding!!
    val adapter = CharactesrAdapter()
    val adapterSearch = SearchAdapter()
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

        binding.search.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.length>0) {
                    binding.rvsearch.visibility =View.VISIBLE
                    binding.rvCharacter.visibility =View.GONE
                    mViewModelCharacter.searchDataByName(s)

                } else {
                    binding.rvCharacter.visibility =View.VISIBLE
                    binding.rvsearch.visibility =View.GONE
                }
            }
        })


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
                findNavController().navigate(com.example.rickandmorty.R.id.action_FirstFragment_to_SecondFragment, bundle)
            }

        }}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}