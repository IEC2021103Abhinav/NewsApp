package com.example.newshorts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val  listener:NewsClickedItem):RecyclerView.Adapter<ShortViewHolder>() {
    private val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortViewHolder {
        val newsView =LayoutInflater.from(parent.context).inflate(R.layout.news_items,parent,false)
        val viewHolder=ShortViewHolder(newsView)
        newsView.setOnClickListener{
            listener.onClickedItem(items[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ShortViewHolder, position: Int) {
        val currItem=items[position]
        holder.titleView.text=currItem.title
        holder.kavi.text=currItem.author
        Glide.with(holder.itemView.context).load(currItem.urlToImage).into(holder.pic)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updatedNews(updatedNews:ArrayList<News>)
    {
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }

}
class ShortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val titleView:TextView=itemView.findViewById(R.id.titleofnews)
    val pic:ImageView=itemView.findViewById(R.id.image)
    val kavi:TextView=itemView.findViewById(R.id.authornews)

}
interface NewsClickedItem{
    fun onClickedItem(item:News)
}