package com.example.wikipedia.app.fragments


import android.content.Context
import com.example.wikipedia.app.adapters.ArticleListItemRecyclerAdapter
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wikipedia.app.managers.WikiManager
import com.example.wikipedia.app.models.WikiPage
import com.example.wikipedia.app.repositories.WikiApplication

import com.example.wikipedia.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton


class HistoryFragment : Fragment() {
    private var wikiManager: WikiManager? = null
    var historyRecycler: RecyclerView? = null
    private val adapter: ArticleListItemRecyclerAdapter = ArticleListItemRecyclerAdapter()

    init{
        setHasOptionsMenu(true)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.history_menu, menu)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        historyRecycler = view.findViewById(R.id.history_article_recycler)
        historyRecycler!!.layoutManager= LinearLayoutManager(context)
        historyRecycler!!.adapter= adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            val historyArticles = wikiManager?.getHistory()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(historyArticles as ArrayList<WikiPage>)
            activity?.runOnUiThread { adapter.notifyDataSetChanged() }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_clear_history){
            //show alert
            activity!!.alert ( "Are you sure you want to clear the history", "Confirm"){
                yesButton {
                    //yes was hit
                    //clear history async
                    adapter.currentResults.clear()
                    doAsync {
                        wikiManager?.clearHistory()
                    }
                    activity!!.runOnUiThread{ adapter.notifyDataSetChanged()}
                }
                noButton {
                    //do nothing here
                }
            }.show()
        }
        return true
    }
}