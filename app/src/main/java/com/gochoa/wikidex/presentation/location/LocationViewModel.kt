package com.gochoa.wikidex.presentation.location

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.data.repositoryImp.FirebaseRepositoryImp
import com.gochoa.wikidex.domain.model.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val firebaseRepositoryImp: FirebaseRepositoryImp
) : ViewModel() {

    private val _status = MutableLiveData<ApiResponseStatus<Task<QuerySnapshot>>>()
    val status: LiveData<ApiResponseStatus<Task<QuerySnapshot>>> get() = _status

    init{
//        getMarker()
    }

    fun addMarker(marker: Marker) = viewModelScope.launch {
        firebaseRepositoryImp.addMarker(marker)
    }

    fun getMarker() = viewModelScope.launch {
        _status.value = ApiResponseStatus.Loading()
        firebaseRepositoryImp.getMarkers().let {
            when(it){
                is ApiResponseStatus.Error -> {}
                is ApiResponseStatus.Loading -> {}
                is ApiResponseStatus.Success -> {
                    viewModelScope.launch{
                        for (documen in it.data.result) {
                            val grg = documen.toObject(Marker::class.java)
                            Log.d("ggg", "${grg.longitude}")
//                    ggg = Mapper().fromSnapShotToModel(result) as MutableList<Marker>
                        }
                    }

                    _status.postValue(ApiResponseStatus.Success(it.data))
                }
            }
        }
    }

}