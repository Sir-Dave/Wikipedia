package com.example.wikipedia


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wikipedia.app.activities.SearchActivity
import com.example.wikipedia.app.adapters.ArticleCardRecyclerAdapter
import com.example.wikipedia.app.managers.WikiManager
import com.example.wikipedia.app.repositories.WikiApplication

class ExploreFragment : Fragment() {

  private var wikiManager: WikiManager? = null

    var searchCardView: CardView? = null
    var exploreRecycler: RecyclerView? = null
    var refresher: SwipeRefreshLayout? = null
    var adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_explore, container, false)

        searchCardView = view.findViewById(R.id.search_card_view)
        refresher= view.findViewById(R.id.refresher)
        exploreRecycler= view.findViewById(R.id.explore_article_recycler)

        searchCardView!!.setOnClickListener{
            val searchIntent= Intent(context, SearchActivity::class.java)
            context!!.startActivity(searchIntent)
        }
        exploreRecycler!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        exploreRecycler!!.adapter= adapter

        refresher!!.setOnRefreshListener {
            getRandomArticles()
        }

        getRandomArticles()

        return view
    }

    private fun getRandomArticles(){
        refresher?.isRefreshing = true
        try {
            wikiManager?.getRandom(15,{ wikiResult->
            adapter.currentResults.clear()
            adapter.currentResults.addAll(wikiResult.query!!.pages)
            activity!!.runOnUiThread{
                adapter.notifyDataSetChanged()
            refresher?.isRefreshing = false}
        })
    }
        catch (ex: Exception){
        val builder = AlertDialog.Builder(activity)
            builder.setMessage(ex.message)
            builder.setTitle("Oops")
            val dialog = builder.create()
            dialog.show()
        }
    }

}