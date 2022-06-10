package com.rickandmorty.ui.characterlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentDetailBinding
import com.rickandmorty.utils.loadSvg
import com.rickandmorty.viewmodels.CharacterViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetalleFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val viewModel: CharacterViewModel by activityViewModels()
    var getId: Int = 0
    var url: String = ""
    var nameC: String = ""
    var specie: String = ""
    var created: String = ""
    var img: String = ""
    var genre: String = ""

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            getId = it.getInt("id", 0)
            url = it.getString("url", "")
            genre = it.getString("genre", "")
            nameC = it.getString("name", "")
            specie = it.getString("specie", "")
            created = it.getString("created", "")
            img = it.getString("img", "")

        }}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.titleEd.text = nameC
        binding.imageView.loadSvg(img)
        binding.created.text=created
        binding.specie.text = specie
        binding.urlr.text = genre
        binding.id.text= getId.toString()


        binding.volver.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}