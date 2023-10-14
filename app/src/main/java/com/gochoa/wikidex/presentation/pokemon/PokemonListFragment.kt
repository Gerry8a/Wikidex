package com.gochoa.wikidex.presentation.pokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.gochoa.wikidex.R
import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.databinding.FragmentPokemonListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private val viewModel: PokemonViewModel by viewModels()
    private lateinit var binding: FragmentPokemonListBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.status.observe(requireActivity()){
            when(it){
                is ApiResponseStatus.Error -> Toast.makeText(
                    requireContext(),
                    "Error",
                    Toast.LENGTH_SHORT
                ).show()
                is ApiResponseStatus.Loading -> TODO()
                is ApiResponseStatus.Success -> Toast.makeText(
                    requireContext(),
                    it.data.name.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}