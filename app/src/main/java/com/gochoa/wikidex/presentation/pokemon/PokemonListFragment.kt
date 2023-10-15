package com.gochoa.wikidex.presentation.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.databinding.FragmentPokemonListBinding
import com.gochoa.wikidex.domain.Pokemon
import com.gochoa.wikidex.presentation.pokemon.adapter.PokemonAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private val viewModel: PokemonViewModel by viewModels()
    private lateinit var binding: FragmentPokemonListBinding
    private lateinit var pokemonAdapter: PokemonAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.status.observe(requireActivity()) {
            when (it) {
                is ApiResponseStatus.Error -> Toast.makeText(
                    requireContext(),
                    "Error",
                    Toast.LENGTH_SHORT
                ).show()

                is ApiResponseStatus.Loading -> TODO()
                is ApiResponseStatus.Success -> {
                    fillData(it.data)
                }
            }
        }
    }

    private fun fillData(listPokemon: List<Pokemon>) {
        pokemonAdapter = PokemonAdapter(listPokemon, onItemSelected = {
            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
        })
        binding.rvPokemon.apply {
            adapter = pokemonAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}