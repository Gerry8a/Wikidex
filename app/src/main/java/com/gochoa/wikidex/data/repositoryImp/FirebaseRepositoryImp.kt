package com.gochoa.wikidex.data.repositoryImp

import android.util.Log
import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.data.remote.makeNetworkCall
import com.gochoa.wikidex.data.remote.mapper.Mapper
import com.gochoa.wikidex.domain.model.Marker
import com.gochoa.wikidex.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRepositoryImp @Inject constructor(
    private val db: FirebaseFirestore
) : FirebaseRepository {
    override suspend fun getMarkers(): ApiResponseStatus<Task<QuerySnapshot>> = makeNetworkCall {
        db.collection("markers")
            .get()
            .addOnSuccessListener { result ->
                Log.d("ggg", "getMarkers:")
                for (documen in result) {
                    val grg = documen.toObject(Marker::class.java)
//                    Log.d("ggg", "${grg.longitude}")
//                    ggg = Mapper().fromSnapShotToModel(result) as MutableList<Marker>
                }
                result
            }
            .addOnFailureListener {
                Log.d("ggg", "getMarkers: ${it.message.toString()}")
                it.message
            }
    }

    override suspend fun addMarker(marker: Marker) {
        db.collection("markers").add(marker)
            .addOnSuccessListener {
                Log.d("ggg", "addMarker: added")
            }
            .addOnFailureListener {
                Log.d("ggg", it.message!!)
            }
    }
}