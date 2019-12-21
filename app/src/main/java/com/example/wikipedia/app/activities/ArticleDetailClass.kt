package com.example.wikipedia.app.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.wikipedia.app.managers.WikiManager
import com.example.wikipedia.app.models.WikiPage
import com.example.wikipedia.app.repositories.WikiApplication
import com.example.wikipedia.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.activity_article_detail.toolbar
import org.jetbrains.anko.toast
import java.lang.Exception

class ArticleDetailClass: AppCompatActivity() {

    private var wikiManager: WikiManager? = null
    private var currentPage: WikiPage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        wikiManager = (applicationContext as WikiApplication).wikiManager
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val wikiPageJson = intent.getStringExtra("page")
        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)

        supportActionBar?.title = currentPage?.title

        article_detail_webview?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: String?): Boolean {
                view?.loadUrl(request)
                return true
            }
        }
        article_detail_webview.loadUrl(currentPage!!.fullurl)
        wikiManager?.addHistory(currentPage!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        else if (item.itemId == R.id.action_favourite) {
            try {
                //determine if the article is a favourite or not
                if (wikiManager!!.getIsFavourite(currentPage!!.pageid!!)) {
                    wikiManager!!.removeFavourite(currentPage!!.pageid!!)
                    toast("Article removed from favourites")
                }
                else {
                    wikiManager!!.addFavourite(currentPage!!)
                    toast(("Article added to favourites"))
                }
            }
            catch (ex: Exception) {
                toast("Unable to update this article")
            }
        }
        return true
    }
}

