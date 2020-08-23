package com.example.gallery

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import com.squareup.picasso.Picasso


class ImageActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PHOTO = "ImageActivity.EXTRA_PHOTO"
    }

    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var photo: ImageModel
    private var mShareActionProvider: ShareActionProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        photo = intent.getParcelableExtra(EXTRA_PHOTO)
        imageView = findViewById(R.id.image)
        textView = findViewById(R.id.tv_title)
    }

    override fun onStart() {
        super.onStart()
        Picasso.get()
            .load(photo.url)
            .placeholder(R.drawable.ic_download)
            .error(R.drawable.ic_download)
            .fit()
            .into(imageView)
        textView.text = photo.title
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.image_menu, menu)
        val item = menu.findItem(R.id.menu_item_share)
        mShareActionProvider = MenuItemCompat.getActionProvider(item) as ShareActionProvider?
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // TODO Auto-generated method stub
        when (item.itemId) {
            R.id.menu_item_share -> {
                println("Start of share")
                val intent = Intent("android.intent.action.SEND")
                intent.type = "plain/text"
                intent.putExtra("android.intent.extra.TEXT", photo.url)
                startActivity(Intent.createChooser(intent, "Поделиться"))


//                val shareIntent: Intent = Intent().apply {
//                    action = Intent.ACTION_SEND
//                    putExtra(Intent.EXTRA_STREAM, photo.url)
//                    type = "image/jpeg"
//                }
//                startActivity(Intent.createChooser(shareIntent, "Поделиться"))

                println("END of share")
            }

            else -> println("ERRORS")
        }
        return super.onOptionsItemSelected(item)
    }
}
