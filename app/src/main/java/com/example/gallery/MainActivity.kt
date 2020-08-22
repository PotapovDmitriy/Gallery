package com.example.gallery

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import com.example.gallery.MainActivity.ImageGalleryAdapter as ImageGalleryAdapter1

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageGalleryAdapter: ImageGalleryAdapter1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView = findViewById(R.id.rv_images)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager

        var task = AsyncRequest()
        var models  = task.execute().get()
        imageGalleryAdapter = ImageGalleryAdapter(this, models)
        // доделать с возвращением своей модели, так же переделать эту модель под Parcelable
//        imageGalleryAdapter = ImageGalleryAdapter(this, SunsetPhoto.getSunsetPhotos())

    }

    override fun onStart() {
        super.onStart()
        recyclerView.adapter = imageGalleryAdapter
    }







    private inner class ImageGalleryAdapter(val context: Context, val sunsetPhotos: List<ImageModel>)
        : RecyclerView.Adapter<ImageGalleryAdapter1.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageGalleryAdapter1.MyViewHolder {
            val context = parent.context
            val inflater = LayoutInflater.from(context)
            val photoView = inflater.inflate(R.layout.item_image, parent, false)
            return MyViewHolder(photoView)
        }

        override fun onBindViewHolder(holder: ImageGalleryAdapter1.MyViewHolder, position: Int) {
            val sunsetPhoto = sunsetPhotos[position]
            val imageView = holder.photoImageView

            Picasso.get()
                .load(sunsetPhoto.url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit()
                .into(imageView)
        }

        override fun getItemCount(): Int {
            return sunsetPhotos.size
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

            var photoImageView: ImageView = itemView.findViewById(R.id.iv_photo)

            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(view: View) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val sunsetPhoto = sunsetPhotos[position]
                    val intent = Intent(context, ImageActivity::class.java).apply {
                        putExtra(ImageActivity.EXTRA_SUNSET_PHOTO, sunsetPhoto)
                    }
                    startActivity(intent)
                }
            }
        }
    }
}