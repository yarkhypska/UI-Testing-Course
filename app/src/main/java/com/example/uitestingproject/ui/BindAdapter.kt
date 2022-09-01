package com.example.uitestingproject.ui

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("app:setVisibilityDependOn")
fun setVisibilityDependedOnText(view: TextView, @IdRes dependedTextId: Int) {
    val dependedTextView = (view.parent as ViewGroup).findViewById<TextView>(dependedTextId)
    fun updateVisibility(text: CharSequence?) {
        val visibility = if (text.isNullOrBlank()) View.GONE else View.VISIBLE
        dependedTextView.visibility = visibility
        view.visibility = visibility
    }
    updateVisibility(dependedTextView.text)
    dependedTextView.doOnTextChanged { text, start, before, count ->
        updateVisibility(text)
    }
}

@BindingAdapter("app:imageUrl")
fun loadImage(image: ImageView, imageUrl: String?) {
    Picasso.get().load(imageUrl ?: return).into(image)
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}