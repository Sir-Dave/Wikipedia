package com.example.wikipedia.app.holders

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wikipedia.app.activities.ArticleDetailClass
import com.example.wikipedia.app.models.WikiPage
import com.example.wikipedia.R
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class CardHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val articleImageView: ImageView = itemView.findViewById(R.id.article_image)
    private val titleTextView: TextView = itemView.findViewById(R.id.article_title)

    private var currentPage: WikiPage? = null

    init{
        itemView.setOnClickListener{
            val detailPageIntent = Intent(itemView.context, ArticleDetailClass::class.java)
            var pageJson = Gson().toJson(currentPage)
            detailPageIntent.putExtra("page", pageJson)
            itemView.context.startActivity(detailPageIntent)

        }
    }

    fun updatewithPage(page: WikiPage){
        currentPage = page
        titleTextView.text = page.title
        if (page.thumbnail!= null){
            Picasso.with(itemView.context).load(page.thumbnail!!.source).into(articleImageView)
        }
    }
}