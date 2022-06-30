package com.rickandmorty.ui.searchCharacter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.CharItemListBinding
import com.rickandmorty.model.pojo.ResultCharacter
import com.rickandmorty.utils.loadSvg


class PageCharacterSearchAdapter : PagingDataAdapter<ResultCharacter,
        PageCharacterSearchAdapter.ImageViewHolder>(diffCallback) {


    inner class ImageViewHolder(val binding: CharItemListBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        fun bind(itemChar: ResultCharacter){
            binding.edName.text = itemChar.name
            binding.imageView.loadSvg(itemChar.image)
            binding.imageView.setOnClickListener{
                Bundle().apply {
                    putString("img", itemChar.image)
                    findNavController(it).navigate(R.id.action_searchNewsFragment_to_detailfrg, this)
                }
            }
            itemView.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {

        }
    }



    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ResultCharacter>() {
            override fun areItemsTheSame(oldItem: ResultCharacter, newItem: ResultCharacter): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultCharacter, newItem: ResultCharacter): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(CharItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currChar = getItem(position)
        if (currChar != null) {
            holder.bind(currChar)
        }

    }


}