package com.example.finalquote.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalquote.domain.usecases.QuoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(

    private val quoteUseCase: QuoteUseCase,
//    private val quoteRepository: QuoteRepository
) : ViewModel() {




    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories : StateFlow<List<String>> = _categories.asStateFlow()


    init {

        viewModelScope.launch(Dispatchers.IO) {
            fetchCategory()
        }
    }

    suspend fun fetchCategory() {

        val category  = quoteUseCase.getRemoteCategory()
        _categories.emit(category)

    }

}