package com.example.wikipedia


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wikipedia.app.adapters.ArticleCardRecyclerAdapter
import com.example.wikipedia.app.managers.WikiManager
import com.example.wikipedia.app.models.WikiPage
import com.example.wikipedia.app.repositories.WikiApplication
import org.jetbrains.anko.doAsync

class FavouriteFragment : Fragment() {
    private var wikiManager: WikiManager? = null
    var favouriteRecycler: RecyclerView?= null
    private val adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)

        favouriteRecycler= view.findViewById(R.id.favourite_article_recycler)
        favouriteRecycler!!.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        favouriteRecycler!!.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            val favouritesArticles = wikiManager?.getFavourites()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(favouritesArticles as ArrayList<WikiPage>)
            activity?.runOnUiThread { adapter.notifyDataSetChanged() }
        }
    }
}
