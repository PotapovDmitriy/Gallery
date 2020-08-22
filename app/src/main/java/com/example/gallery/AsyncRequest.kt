package com.example.gallery

import android.os.AsyncTask
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AsyncRequest : AsyncTask<String, Void, List<ImageModel>>() {

    override fun doInBackground(vararg params: String?): List<ImageModel> {
        val request = Request.Builder().url("https://jsonplaceholder.typicode.com/albums/1/photos").build()
        val client = OkHttpClient()
        var responses: Response? = null
        try {
            responses = client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val jsonData = responses!!.body()!!.string()
        println(jsonData.length)
        val gson = GsonBuilder().create()
        val models = gson.fromJson(jsonData, Array<ImageModel>::class.java).toList()
        println(models[0].url)
        return models
    }

    override fun onPostExecute(list : List<ImageModel>) {
        print("THE ENDS ")
    }
}