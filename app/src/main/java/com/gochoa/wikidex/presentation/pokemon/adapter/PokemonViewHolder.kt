package com.gochoa.wikidex.presentation.pokemon.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gochoa.wikidex.databinding.ItemPokemonBinding
import com.gochoa.wikidex.domain.model.Pokemon
import io.getstream.avatarview.coil.loadImage

/**
 * Considerar que la librería de avatar no permite dejar sólo una letra.
 */
class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemPokemonBinding.bind(view)

    fun render(
        pokemon: Pokemon,
        onItemSelected: (Pokemon) -> Unit,
    ) {
        val context = binding.tvName.context

        binding.apply {
            ivPokemon.loadImage(
                data = pokemon.urlImage,
                onError = { _, _ ->
                    ivPokemon.avatarInitials = getInitials(pokemon.name!!)
                }
            )
        }

        binding.ivPokemon.loadImage(pokemon.urlImage)

        binding.tvName.text = pokemon.name
        binding.parent.setOnClickListener {
            onItemSelected(pokemon)
        }
    }

    private fun getInitials(name: String): String? {
        var iniciales = name[0].uppercase()
        for (i in 1 - 0 until name.length) {

            if (name[i - 1] == ' ' || i.toInt() == 0) {
                iniciales += name[i].uppercase()
            } else {
                iniciales += name[i]
            }
        }
        return iniciales
    }
}