package com.example.wikipedia.app.managers

import com.example.wikipedia.app.models.WikiPage
import com.example.wikipedia.app.models.WikiResult
import com.example.wikipedia.app.providers.ArticleDataProvider
import com.example.wikipedia.app.repositories.FavouritesRepository
import com.example.wikipedia.app.repositories.HistoryRepository

class WikiManager(private val provider: ArticleDataProvider,
                  private val favouritesRepository: FavouritesRepository,
                  private val historyRepository: HistoryRepository) {

    private var historyCache: ArrayList<WikiPage>? = null
    private var favouriteCache: ArrayList<WikiPage>? = null

    fun search(term: String, skip: Int, take: Int, handler: (result: WikiResult)->Unit?){
        return provider.search(term, skip, take, handler)

    }

    fun getRandom(take: Int, handler: (result: WikiResult) -> Unit?){
        return provider.getRandom(take, handler)
    }

    fun getHistory(): ArrayList<WikiPage>?{
        if (historyCache == null){
            historyCache = historyRepository.getAllHistory()
        }
        return historyCache
    }

    fun getFavourites(): ArrayList<WikiPage>?{
        if (favouriteCache == null){
            favouriteCache = favouritesRepository.getAllFavourites()
        }
        return favouriteCache
    }

    fun addFavourite(page: WikiPage){
         favouriteCache?.add(page)
         favouritesRepository.addFavourite(page)
     }

    fun removeFavourite(pageId: Int){
        favouritesRepository.removeFavouritesbyId(pageId)
        favouriteCache = favouriteCache!!.filter { it.pageid != pageId } as ArrayList<WikiPage>
    }

    fun getIsFavourite(pageId: Int): Boolean{
        return favouritesRepository.isArticleFavourite(pageId)
    }

    fun addHistory(page: WikiPage){
        historyCache?.add(page)
        historyRepository.addFavourite(page)
    }
    fun clearHistory(){
        historyCache?.clear()
        val allHistory = historyRepository.getAllHistory()
        allHistory.forEach{historyRepository.removePagebyId(it.pageid!!)}

    }

}