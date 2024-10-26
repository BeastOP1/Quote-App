package com.example.finalquote.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalquote.domain.model.Quote
import com.example.finalquote.domain.usecases.QuoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val useCase: QuoteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookmarkState())
    val state: StateFlow<BookmarkState> = _state.asStateFlow()


    init {

        getLocalQuotes()
    }

    fun getLocalQuotes(){

        viewModelScope.launch {
            try {
                //fetch All the Bookmark Quotes
                useCase.getLocalQuotes().collect {

                    _state.value =_state.value.copy(
                        quotes =it.asReversed() // reverse the quote latest
                    )
                }
            } catch (e:Exception){
                e.printStackTrace()
            }


        }
    }
    fun toggleBookmark(quote: Quote ) {
        viewModelScope.launch(Dispatchers.IO) {

            val isBookMarked = _state.value.bookmarkedQuotes.contains(quote)

            if(isBookMarked){

                _state.value = _state.value.copy(
                    bookmarkedQuotes =_state.value.bookmarkedQuotes - quote
                )
            }else {
                _state.value = _state.value.copy(
                    bookmarkedQuotes =_state.value.bookmarkedQuotes + quote
                )
            }
        }
    }

    fun isBookmarked(quote: Quote): Boolean {
        return _state.value.bookmarkedQuotes.contains(quote)
    }


}