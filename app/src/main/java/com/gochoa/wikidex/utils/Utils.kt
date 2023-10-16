package com.gochoa.wikidex.utils

import android.content.Context
import com.gochoa.wikidex.R
import java.text.SimpleDateFormat
import java.util.Date

object Utils {
    fun getDate(context: Context): String {
        val date = Date()

        val formatted = SimpleDateFormat(context.getString(R.string.date_format))
        return context.getString(R.string.date_created, formatted.format(date))
    }

    fun getTypes(firstType: String?, secondType: String?): String{
        return if(secondType == null){
            "Tipo: $firstType"
        } else {
            "Tipos: $firstType / $secondType"
        }
    }
}