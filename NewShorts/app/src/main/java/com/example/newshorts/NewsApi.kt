package com.example.newshorts

import android.content.Context
import android.graphics.Bitmap
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.bumptech.glide.util.LruCache

class NewsApi constructor(context: Context) {
    companion object{
        @Volatile
        private  var INSTANCE:NewsApi?=null
        fun getInstance(context: Context)=
            INSTANCE?: synchronized(this){
                INSTANCE?:NewsApi(context).also {
                    INSTANCE=it
                }
            }
    }
    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue,
            object : ImageLoader.ImageCache {
                private val cache = LruCache<String, Bitmap>(20)
                override fun getBitmap(url: String): Bitmap? {
                    return cache.get(url)
                }
                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }
            })
    }
    private  val requestQueue:RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    fun<T>applyToRequestQueue(request: Request<T>){
        requestQueue.add(request)
    }

}