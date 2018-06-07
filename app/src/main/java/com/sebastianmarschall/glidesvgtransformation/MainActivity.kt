package com.sebastianmarschall.glidesvgtransformation

import android.content.ContentResolver
import android.content.Intent
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions


class MainActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var requestBuilder: RequestBuilder<PictureDrawable>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        imageView.setOnClickListener { view ->
            val intent = Intent(this, DetailActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair(view, "svgTransition"))
            startActivity(intent, options.toBundle())
        }

        requestBuilder = Glide.with(this)
                .`as`(PictureDrawable::class.java)
                .apply(RequestOptions().circleCrop())
                .listener(SvgSoftwareLayerSetter())
    }

    override fun onStart() {
        super.onStart()
        loadRes()
    }

    private fun loadRes() {
        val uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + "/" + R.raw.android)
        requestBuilder.load(uri).into(imageView)
    }
}
