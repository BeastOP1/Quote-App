package com.example.finalquote.data.repository

import android.util.Log
import com.example.finalquote.data.Local.LocalQuoteDao
import com.example.finalquote.data.Local.QuoteDao
import com.example.finalquote.data.remote.QuoteApi
import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.model.Quote
import com.example.finalquote.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteRepositoryImp @Inject constructor(
    private val quoteApi: QuoteApi,
    private val quoteDao: QuoteDao,
    private val localQuoteDao: LocalQuoteDao
) : QuoteRepository{


    //Logic for Getting All the List of List of Quotes and then add save one by one
    override suspend fun getAllRemoteQuotes(): List<List<LocalQuote>> {

        return try {
            val quoteResponse: Response<List<List<LocalQuote>>> = quoteApi.getAllQuotes()

            if(quoteResponse.isSuccessful){

                quoteResponse.body()?.let {  quotesList->
                    //apply loop forEach one by one
                    quotesList.forEach { quoteList ->

                        quoteList.forEach { quote ->
                            try {
                                // Insert each quote into the local database
                                localQuoteDao.addLocalQuote(quote)
                                Log.d("Quote", "Quote added to LocalSearch: ${quote.quote.length}")
                            } catch (e: Exception) {
                                Log.e("Quote", "Error adding quote to LocalSearch: ${e.message}")
                            }
                        }
                    }
                    return quotesList
                }

            }

            emptyList()
        }catch (e: Exception){
            Log.e("Quote", "Error fetching quotes: ${e.message}", e)
            emptyList() // Return empty list in case of an exception
        }
    }


    //Fetch the Quotes on the given category
    override suspend fun getRemoteQuotes(category: String) : List<Quote>{
        return try {
            val quoteResponse: Response<List<Quote>> = quoteApi.getQuotes("quotes[?(@.category==\"$category\")]")

            if (quoteResponse.isSuccessful) {

                quoteResponse.body()?.let { quote ->
                    Log.d("Quote", "Response body: $quote") // Log the full response
                    val validQuotes = quote.filter { it.category.isNotEmpty() && it.quote != null }
                    Log.d("Quote", "Quotation fetched successfully: ${validQuotes.size} quotes") // Log the valid quotes count
                    // Return the valid quotes
                    validQuotes

                } ?: run {
                    Log.d("Quote", "No quotations available for category: $category")
                    emptyList()
                    // Return an empty list if the body is null
                }
            } else {
                Log.d("Quote", "HTTP Error: ${quoteResponse.code()} - ${quoteResponse.message()}")

                emptyList()
                // Return an empty list for unsuccessful responses
            }
        } catch (e: Exception) {
            Log.e("Quote", "Error fetching quotes: ${e.message}", e)

            emptyList()
            // Return an empty list in case of an exception
        }
    }



    //Fetching All the list of Categories
    override suspend fun getRemoteCategory()  : List<String>{
        return try {
            val response: Response<List<String>> = quoteApi.getCategory()

            if (response.isSuccessful) {
                response.body()?.let { category ->
                    Log.d("Quote", "Response body: $category") // Log the full response
                    val validQuotes = category.filter { it.isNotEmpty() && it!= null }
                    Log.d("Quote", "Category  fetched successfully: ${validQuotes.size} category") // Log the valid quotes count
                    // Return the valid quotes
                    validQuotes

                } ?: run {
                    Log.d("Quote", "No Category : $response")

                    emptyList()
                    // Return an empty list if the body is null
                }
            } else {
                Log.d("Quote", "HTTP Error: ${response.code()} - ${response.message()}")

                emptyList()
                // Return an empty list for unsuccessful responses
            }
        }catch (e:Exception){

            Log.e("Quote", "Error fetching category: ${e.message}", e)
            emptyList()
            // Return an empty list in case of an exception
        }

    }





    //for getting All the Quotes present in Bookmark
    override  fun getLocalQuotes(): Flow<List<Quote>> {
        val list = quoteDao.getAllQuotes()
        return  list
    }


    //adding quote in bookmark
    override suspend fun insertQuote(quote: Quote) {
        try{
            quoteDao.addQuote(quote)
            Log.d("Quote","added ${quote} successfully")

        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    //deleteing quote in bookmark
    override suspend fun deleteQuote(quote: Quote) {

        try {
            quoteDao.deleteQuote(quote)
            Log.d("Quote","Delete ${quote} successfully")
        } catch (e:Exception){
            e.printStackTrace()
        }
    }

    //getting share quote detail
    override suspend fun getLocalQuote(category: String): Quote? {
        val quote =quoteDao.getQuote(category)
        return quote
        Log.d("Quote","Share Quote Detail ${quote?.quote } \n And Author ${quote?.author}")
    }

    //this for fetching all quotes in that directly saved in db
    override fun getAllLocalQuotes(): Flow<List<LocalQuote>> {

        val localQuotes =localQuoteDao.getAllLocalQuotes()
        return  localQuotes
        Log.d("Quote","All Quotes Fetched Successfully of Second Room Table")

    }


    //add in another room Table
    override suspend fun addSearchQuote(quote: LocalQuote) {
        return localQuoteDao.addLocalQuote(quote)
        Log.d("Quote","Quote Added in Local Database")
    }
    //delete in another room Table
    override suspend fun deleteSearchQuote(quote: LocalQuote) {
        return localQuoteDao.deleteQuote(quote)
        Log.d("Quote","Quote Deleted in Local Database")

    }

    //this for fetching all quotes by query in that directly saved in db
    override fun searchLocalQuotes(query: String): Flow<List<LocalQuote>> {
        return localQuoteDao.searchLocalQuotes(query)
        Log.d("Quote"," Search Result is Fetched Successfully ")
    }


}


