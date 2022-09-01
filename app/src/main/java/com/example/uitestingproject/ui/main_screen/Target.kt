package com.example.uitestingproject.ui.main_screen

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.ref.WeakReference

class MyTarget(
    imageView: ImageView,
    progressBar: ProgressBar,
): Target {

    private val weakImage = WeakReference(imageView)
    private val weakProgress = WeakReference(progressBar)

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        weakProgress.get()?.visibility = View.GONE
        bitmap?.let { weakImage.get()?.setImageBitmap(bitmap) }
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        weakProgress.get()?.visibility = View.GONE
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        weakProgress.get()?.visibility = View.VISIBLE
    }
}