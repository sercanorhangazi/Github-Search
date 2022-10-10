package com.sercanorhangazi.githubsearch.pojo

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sercanorhangazi.githubsearch.model.User


class SearchUserConverter {
    @TypeConverter
    fun fromString(value: String?): List<User>? {
        val listType = object : TypeToken<List<User?>?>() {}.type
        return Gson().fromJson<List<User>>(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<User?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}

