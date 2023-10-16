package com.gochoa.wikidex.presentation.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gochoa.wikidex.R
import com.gochoa.wikidex.domain.model.Pokemon

class PokemonAdapter (private var taskList: List<Pokemon> = emptyList(),
                      private val onItemSelected: (Pokemon) -> Unit,
) : RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        )
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.render(taskList[position], onItemSelected)
    }
}