package com.gochoa.wikidex.presentation.location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gochoa.wikidex.R
import com.gochoa.wikidex.domain.model.Marker


class LocationAdapter(
    private var markerList: List<Marker> = emptyList(),
): RecyclerView.Adapter<LocationViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        )
    }

    override fun getItemCount(): Int = markerList.size

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.render(markerList[position])
    }
}