package com.example.service.repo

import com.google.gson.Gson

class GsonInstance {

    private var INSTANCE: GsonInstance? = null
    private var gson: Gson? = null

    fun getInstance(): GsonInstance? {
        if (INSTANCE == null) {
            synchronized(GsonInstance::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = GsonInstance()
                }
            }
        }
        return INSTANCE
    }

    fun getGson(): Gson? {
        if (gson == null) {
            synchronized(GsonInstance::class.java) {
                if (gson == null) {
                    gson = Gson()
                }
            }
        }
        return gson
    }
}