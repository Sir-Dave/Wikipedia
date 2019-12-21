package com.example.wikipedia.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wikipedia.app.models.WikiPage
import com.example.wikipedia.R
import com.example.wikipedia.app.holders.CardHolder

class ArticleCardRecyclerAdapter: RecyclerView.Adapter<CardHolder>() {

    val currentResults= ArrayList<WikiPage>()
    override fun getItemCount(): Int {
        return currentResults.size

    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        var page = currentResults[position]
        holder.updatewithPage(page)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        var cardItem= LayoutInflater.from(parent.context).inflate(R.layout.article_card_item, parent, false)
        return CardHolder(cardItem)
    }
}