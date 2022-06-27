package com.rickandmorty.ui.characterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.CharItemListBinding
import com.rickandmorty.model.pojo.ResultCharacter
import com.rickandmorty.utils.loadSvg

class CharactesrAdapter :RecyclerView.Adapter<CharactesrAdapter.CharactersVH>(){

    private var listCharacterItem = mutableListOf<ResultCharacter>()
    private var selectedItem = MutableLiveData<ResultCharacter>()
    fun selectedItem()=selectedItem

    fun update(list: MutableList<ResultCharacter>){
        list.addAll(listCharacterItem)
        listCharacterItem=list
        notifyDataSetChanged()

    }

    inner class CharactersVH(private val binding: CharItemListBinding ):
        RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        fun bind(itemChar: ResultCharacter){
            binding.edName.text = itemChar.name
            binding.imageView.loadSvg(itemChar.image)
            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            selectedItem.value=listCharacterItem[adapterPosition]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersVH {
        return CharactersVH(CharItemListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CharactersVH, position: Int) {
        val gameFreeItem = listCharacterItem[position]
        holder.bind(gameFreeItem)
    }

    override fun getItemCount(): Int =
        listCharacterItem.size

}