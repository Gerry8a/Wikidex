package com.gochoa.wikidex.presentation.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.data.remote.RepositoryImp
import com.gochoa.wikidex.data.remote.dto.PokemonDTO
import com.gochoa.wikidex.domain.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repositoryImp: RepositoryImp
): ViewModel() {

    private val _status = MutableLiveData<ApiResponseStatus<List<Pokemon>>>()
    val status: LiveData<ApiResponseStatus<List<Pokemon>>> get() = _status

    init {
        getPokemon()
    }

    private fun getPokemon() = viewModelScope.launch {
        repositoryImp.getListPokemon(10).let {
            when (it) {
                is ApiResponseStatus.Error -> _status.value = ApiResponseStatus.Error(it.messageID)
                is ApiResponseStatus.Loading -> _status.value = ApiResponseStatus.Loading()
                is ApiResponseStatus.Success -> _status.value = ApiResponseStatus.Success(it.data)
            }
        }
    }

}