package com.gochoa.wikidex.domain.repository

import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.domain.model.Marker
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface FirebaseRepository {
    suspend fun getMarkers(): ApiResponseStatus<Task<QuerySnapshot>>
    suspend fun addMarker(marker: Marker)
}