package com.example.newshorts
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), NewsClickedItem {
    private lateinit var madapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NewShorts)
        setContentView(R.layout.activity_main)
        val recyclerView=findViewById<RecyclerView>(R.id.recycle)
        recyclerView.layoutManager=LinearLayoutManager(this)
        fetchData()
        madapter=NewsAdapter(this)
        recyclerView.adapter=madapter


    }
    private fun fetchData()
    {
        val url="https://saurav.tech/NewsAPI/everything/cnn.json"
        val jsonObjectRequest=JsonObjectRequest(
            Request.Method.GET,url,
            null,
            {
                val newsJsonArray=it.getJSONArray("articles")
                val newsArray=ArrayList<News>()
                for(i in 0 until newsJsonArray.length())
                {
                    val newsJsonObject=newsJsonArray.getJSONObject(i)
                    val news=News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                madapter.updatedNews(newsArray)

            },
            {
                Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()

            }
        )

        NewsApi.getInstance(this).applyToRequestQueue(jsonObjectRequest)

    }
     override fun onClickedItem(item: News) {

         val building=CustomTabsIntent.Builder()
         val customTabsIntent=building.build()
         val anotherCustom=CustomTabsIntent.Builder().build()
         val colorInt: Int = Color.parseColor("#FF0000") //red
         val defaultColors = CustomTabColorSchemeParams.Builder()
             .setToolbarColor(colorInt)
             .build()
         customTabsIntent.launchUrl(this@MainActivity, Uri.parse(item.url))
    }
}

