package com.example.service.repo

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataItemConverter {


    @TypeConverter
    fun objectToString(list:List<Any>):String {
        val gson = Gson()
        return gson.toJson(list)

    }
    @TypeConverter
    fun stringToObject(value: String): List<Any> {
        val listType = object : TypeToken<List<Any>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

}