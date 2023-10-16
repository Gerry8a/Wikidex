package com.gochoa.wikidex.presentation.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.load
import com.gochoa.wikidex.R
import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.databinding.FragmentDetailPokemonBinding
import com.gochoa.wikidex.domain.model.Pokemon
import com.gochoa.wikidex.utils.Utils.getTypes
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPokemonFragment : Fragment() {

    private lateinit var binding: FragmentDetailPokemonBinding
    private val viewModel: PokemonViewModel by viewModels()
    private var namePokemon: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailPokemonBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            namePokemon = it.getString("pokemonName", "")
        }

        initUI()
        onClickEvent()
        viewModel.getPokemon(namePokemon)
        viewModel.pokemon.observe(requireActivity()) {
            when (it) {
                is ApiResponseStatus.Error -> {
                    binding.loading.root.visibility = View.GONE
                    Toast.makeText(requireContext(), it.messageID, Toast.LENGTH_SHORT).show()
                }

                is ApiResponseStatus.Loading -> {

                }

                is ApiResponseStatus.Success -> {
                    binding.loading.root.visibility = View.GONE
                    fillData(it.data)
                }
            }
        }
    }

    private fun fillData(data: Pokemon) {
        binding.apply {
            tvPokemonName.isVisible = true
            tvId.isVisible = true
            tvInfo.isVisible = true
            ivPokemon.isVisible = true
            tvType.isVisible = true
            tvPokemonName.text = data.name
            tvId.text = getString(R.string.pokemon_number, data.id.toString())
            tvInfo.text =
                getString(R.string.detail_pokemon, data.weight.toString(), data.height.toString())
            ivPokemon.load(data.urlImage)
            tvInfo.text = getTypes(data.fistType, data.secondType)
        }
    }

    private fun initUI() {
        hideBottomNav()
    }

    private fun onClickEvent() {
        binding.ivBack.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_detailPokemonFragment_to_pokemonListFragment)
        }
    }

    private fun hideBottomNav() {
        val navBar = requireActivity()!!.findViewById<BottomNavigationView>(R.id.bottomNavView)
        navBar.visibility = View.GONE
    }
}