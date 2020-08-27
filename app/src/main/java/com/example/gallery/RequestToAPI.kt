package com.example.gallery

import android.os.AsyncTask
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class RequestToAPI {
    companion object {
        fun doInBackground(vararg params: String?): List<ImageModel> {
            val request =
                Request.Builder().url("https://jsonplaceholder.typicode.com/albums/1/photos")
                    .build()
            val client = OkHttpClient()
            var responses: Response? = null
            try {
                responses = client.newCall(request).execute()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val jsonData = responses!!.body()!!.string()
            val gson = GsonBuilder().create()
            return gson.fromJson(jsonData, Array<ImageModel>::class.java).toList()
        }
    }
}
