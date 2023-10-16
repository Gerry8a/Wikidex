package com.gochoa.wikidex.presentation.location.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gochoa.wikidex.databinding.ItemLocationBinding
import com.gochoa.wikidex.domain.model.Marker

class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val binding = ItemLocationBinding.bind(view)
    
    fun render(mark: Marker){
        val context = binding.tvDate.context
        
        binding.apply { 
            tvDate.text = mark.date
            tvCordinate.text = getCoordinateString(mark.latitude, mark.longitude)
        }
    }

    private fun getCoordinateString(latitude: Double, longitude: Double): String {
        return "$latitude, $longitude"
    }
}