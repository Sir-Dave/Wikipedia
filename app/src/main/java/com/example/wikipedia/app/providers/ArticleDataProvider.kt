package com.example.wikipedia.app.providers

import android.util.Log
import com.example.wikipedia.app.models.Urls
import com.example.wikipedia.app.models.WikiResult
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import java.io.Reader

class ArticleDataProvider {
    private val TAG = "ArticleDataProvider"

    init {
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Pluralsight Wikipedia")
    }

    fun search(term: String,skip: Int,take: Int,responseHandler: (result: WikiResult)-> Unit?){
        Urls.getSearchUrl(term, skip, take).httpGet().
            responseObject(WikipediaDataDeserializer()){ _,response,result->

                if (response.statusCode !=200){
                    //throw Exception("Unable to get articles.")

                }
                val (data,_) = result
                responseHandler.invoke(data as WikiResult)
            }
    }
    fun getRandom(take: Int,responseHandler: (result: WikiResult)-> Unit?){
        Urls.getRandomUrl(take).httpGet().responseObject(WikipediaDataDeserializer()){_,response, result->

            if (response.statusCode!=200){

                //throw Exception("Unable to get articles.")

            }

            val (data,_) = result
            responseHandler.invoke(data as WikiResult)
        }
    }

    class WikipediaDataDeserializer: ResponseDeserializable<WikiResult> {
        override fun deserialize(reader: Reader): WikiResult? = Gson().fromJson(reader, WikiResult::class.java)
    }
}