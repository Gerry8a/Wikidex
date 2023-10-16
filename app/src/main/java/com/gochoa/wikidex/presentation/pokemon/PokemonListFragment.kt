package com.gochoa.wikidex.presentation.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gochoa.wikidex.R
import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.databinding.FragmentPokemonListBinding
import com.gochoa.wikidex.domain.model.Pokemon
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
        buildObservers()
    }

    private fun buildObservers() {
        viewModel.status.observe(requireActivity()) {
            when (it) {
                is ApiResponseStatus.Error -> {
                    if (it.messageID == "501") {
                        Toast.makeText(requireContext(),
                            getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                        viewModel.getPokemonDB()
                    } else {
                        Toast.makeText(requireContext(), it.messageID, Toast.LENGTH_SHORT).show()
                    }
                }

                is ApiResponseStatus.Loading -> TODO()
                is ApiResponseStatus.Success -> {
                    fillData(it.data)
                }
            }
        }
    }

    private fun fillData(listPokemon: List<Pokemon>) {
        if (listPokemon.isEmpty()){
            Toast.makeText(requireContext(), getString(R.string.no_data_db), Toast.LENGTH_SHORT).show()
        } else {
            pokemonAdapter = PokemonAdapter(listPokemon, onItemSelected = {
                val bundle = bundleOf("pokemonName" to it.name)
                view?.findNavController()
                    ?.navigate(R.id.action_pokemonListFragment_to_detailPokemonFragment, bundle)
            })
            binding.rvPokemon.apply {
                adapter = pokemonAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

    }
}