package com.sebastianmarschall.glidesvgtransformation

import android.content.ContentResolver
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class DetailActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var requestBuilder: RequestBuilder<PictureDrawable>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        setContentView(R.layout.activity_detail)

        imageView = findViewById(R.id.imageView)
        imageView.setOnClickListener { finish() }

        requestBuilder = Glide.with(this)
                .`as`(PictureDrawable::class.java)
                .apply(RequestOptions().transform(CenterCrop()))
                .listener(SvgSoftwareLayerSetter())
                .listener(object : RequestListener<PictureDrawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<PictureDrawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: PictureDrawable?, model: Any?, target: Target<PictureDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                })
    }

    override fun onStart() {
        super.onStart()
        loadRes()
    }

    private fun loadRes() {
        val uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + "/" + R.raw.android_toy_h)
        requestBuilder.load(uri).into(imageView)
    }
}
