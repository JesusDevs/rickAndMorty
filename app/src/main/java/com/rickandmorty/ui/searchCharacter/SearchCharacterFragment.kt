package com.rickandmorty.ui.searchCharacter

import android.os.Bundle
import android.view.*

import androidx.appcompat.widget.SearchView

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.FragmentSearchNewsBinding
import com.rickandmorty.model.database.CharacterListRoom
import com.rickandmorty.repositories.CharacterRepository
import com.rickandmorty.ui.characterlist.CharactesrAdapter
import com.rickandmorty.ui.characterlist.PageCharacterAdapter
import com.rickandmorty.viewmodels.CharacterViewModel
import com.rickandmorty.viewmodels.ViewModelFactory
import kotlinx.coroutines.*
import timber.log.Timber

class SearchCharacterFragment : Fragment() {

    private var _binding: FragmentSearchNewsBinding? = null
    private val mViewModelCharacter: CharacterViewModel by activityViewModels()

    private val binding get() = _binding!!
    val adapter = CharactesrAdapter()
    val adapterPage = PageCharacterSearchAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //instancia de adaptador listado desde room
        binding.rvSearchNews.adapter = adapterPage
        binding.rvSearchNews.layoutManager = LinearLayoutManager(context)
        val repository= CharacterRepository(CharacterListRoom.getDataBase(requireActivity()).getCharacterDao())
        val viewModelProviderFactory= activity?.let { ViewModelFactory(it.application, repository) }
        val viewModel= viewModelProviderFactory?.let { ViewModelProvider(this, it) }?.get(CharacterViewModel::class.java)


        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //si query es mayor a 2 characteres
                if (query?.length!! > 2) {
                    //llamamos al metodo de busqueda
                    lifecycleScope.launch {
                        viewModel?.characterSearchLiveData("")
                            ?.observe(viewLifecycleOwner) { list ->

                                adapterPage.submitData(viewLifecycleOwner.lifecycle, list)
                                Timber.d("aca timber ok")
                            }
                    }
                }
                    return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    if (newText != null && newText.length > 2) {
                        viewModel?.characterSearchLiveData(newText)?.observe(viewLifecycleOwner) { list ->
                            adapterPage.submitData(viewLifecycleOwner.lifecycle, list)
                            Timber.d("aca timber ok")
                        }
                    }
                }
                return false
            }
        })
        //observer live data
       /* lifecycleScope.launch {
            mViewModelCharacter.characterLiveDataByName.observe(viewLifecycleOwner) {
                Log.d("listResultFirsFragment", "$it")
                adapterPage.submitData(viewLifecycleOwner.lifecycle, it)
            }

        }*/

        //observer con flow
        /*lifecycleScope.launch {
            mViewModelCharacter.characterPageFlow().collect {
                adapterPage.submitData(viewLifecycleOwner.lifecycle, it)

            }}
*/

        /*lifecycleScope.launch {
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