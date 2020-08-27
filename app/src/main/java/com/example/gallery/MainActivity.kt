package com.example.gallery

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageGalleryAdapter: ImageGalleryAdapter
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView = findViewById(R.id.rv_images)
        recyclerView.setHasFixedSize(true)
        try {
            recyclerView.layoutManager = layoutManager
            val models = asyncRequest()
            runBlocking { imageGalleryAdapter = ImageGalleryAdapter(mContext, models.await()) }
            recyclerView.adapter = imageGalleryAdapter

        } catch (e: Exception) {
            val intent = Intent(this, ErrorActivity::class.java)
            startActivity(intent)
        }
    }

    fun asyncRequest() = GlobalScope.async {
        RequestToAPI.doInBackground()
    }


    private inner class ImageGalleryAdapter(
        val context: Context,
        val sunsetPhotos: List<ImageModel>
    ) : RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ImageGalleryAdapter.MyViewHolder {
            val context = parent.context
            val inflater = LayoutInflater.from(context)
            val photoView = inflater.inflate(R.layout.item_image, parent, false)
            return MyViewHolder(photoView)
        }

        override fun onBindViewHolder(holder: ImageGalleryAdapter.MyViewHolder, position: Int) {
            val sunsetPhoto = sunsetPhotos[position]
            val imageView = holder.photoImageView

            Picasso.get()
                .load(sunsetPhoto.url)
                .placeholder(R.drawable.ic_download)
                .error(R.drawable.ic_download)
                .fit()
                .into(imageView)
        }

        override fun getItemCount(): Int = sunsetPhotos.size

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

            var photoImageView: ImageView = itemView.findViewById(R.id.iv_photo)

            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(view: View) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val sunsetPhoto = sunsetPhotos[position]
                    val intent = Intent(context, ImageActivity::class.java).apply {
                        putExtra(ImageActivity.EXTRA_PHOTO, sunsetPhoto)
                    }
                    startActivity(intent)
                }
            }
        }
    }
}