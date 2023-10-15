package com.gochoa.wikidex.presentation.pokemon.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gochoa.wikidex.R
import com.gochoa.wikidex.databinding.ItemPokemonBinding
import com.gochoa.wikidex.domain.Pokemon

class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemPokemonBinding.bind(view)

    fun render(
        pokemon: Pokemon,
        onItemSelected: (Pokemon) -> Unit,
    ) {
        val context = binding.tvName.context

        binding.tvName.text = pokemon.name
        binding.parent.setOnClickListener {
            onItemSelected(pokemon)
        }

    }
}