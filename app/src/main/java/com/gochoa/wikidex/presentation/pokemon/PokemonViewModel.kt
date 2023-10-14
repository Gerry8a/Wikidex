package com.gochoa.wikidex.presentation.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.data.remote.RepositoryImp
import com.gochoa.wikidex.data.remote.dto.PokemonDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repositoryImp: RepositoryImp
): ViewModel() {

    private val _status = MutableLiveData<ApiResponseStatus<PokemonDTO>>()
    val status: LiveData<ApiResponseStatus<PokemonDTO>> get() = _status

    init {
        getPokemon()
    }

    private fun getPokemon() = viewModelScope.launch {
        repositoryImp.getPokemon("23").let {
            when (it) {
                is ApiResponseStatus.Error -> _status.value = ApiResponseStatus.Error("SDF")
                is ApiResponseStatus.Loading -> _status.value = ApiResponseStatus.Loading()
                is ApiResponseStatus.Success -> _status.value = ApiResponseStatus.Success(it.data)
            }
        }
    }

}