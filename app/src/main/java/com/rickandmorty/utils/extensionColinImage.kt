package com.rickandmorty.utils

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

//permite cargar imagenes de todos los formatos desde una url incluso svg
fun ImageView.loadSvg(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(700)

        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}