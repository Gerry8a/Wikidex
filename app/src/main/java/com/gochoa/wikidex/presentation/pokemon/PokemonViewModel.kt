package com.gochoa.wikidex.presentation.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gochoa.wikidex.data.local.entity.PokemonEntity
import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.data.remote.response.Result
import com.gochoa.wikidex.data.repositoryImp.RepositoryImp
import com.gochoa.wikidex.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repositoryImp: RepositoryImp
) : ViewModel() {

    private val _status = MutableLiveData<ApiResponseStatus<List<Pokemon>>>()
    val status: LiveData<ApiResponseStatus<List<Pokemon>>> get() = _status

    private val _pokemon = MutableLiveData<ApiResponseStatus<Pokemon>>()
    val pokemon: LiveData<ApiResponseStatus<Pokemon>> get() = _pokemon

    private val _resultDb = MutableLiveData<ApiResponseStatus<List<Result>>>()
    val resultDB: LiveData<ApiResponseStatus<List<Result>>> get() = _resultDb

    init {
        getListPokemon()
    }

    fun getPokemon(namePokemon: String) = viewModelScope.launch {
        _pokemon.value = ApiResponseStatus.Loading()
        repositoryImp.getPokemon(namePokemon).let {
            when (it) {
                is ApiResponseStatus.Error -> {
                    _pokemon.value = ApiResponseStatus.Error(it.messageID)
                }

                is ApiResponseStatus.Loading -> {}
                is ApiResponseStatus.Success -> {
                    insertPokemon(it.data)
                    _pokemon.value = ApiResponseStatus.Success(it.data)
                }
            }

        }
    }

    private fun insertPokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            val pokemonEntity = PokemonEntity(
                id = pokemon.id!!,
                name = pokemon.name!!,
                urlImage = pokemon.urlImage!!,
                fistType = pokemon.fistType!!,
                secondType = pokemon?.secondType,
                weight = pokemon.weight!!,
                height = pokemon.height
            )
            repositoryImp.insertPokemon(pokemonEntity)
        }
    }

    fun getPokemonDB() = viewModelScope.launch {
        val ggg = repositoryImp.getAllPokemon()
        _status.value = ApiResponseStatus.Success(ggg)
    }

    private fun getListPokemon() = viewModelScope.launch {
        repositoryImp.getListPokemon(25).let {
            when (it) {
                is ApiResponseStatus.Error -> _status.value = ApiResponseStatus.Error(it.messageID)
                is ApiResponseStatus.Loading -> _status.value = ApiResponseStatus.Loading()
                is ApiResponseStatus.Success -> _status.value = ApiResponseStatus.Success(it.data)
            }
        }
    }
}