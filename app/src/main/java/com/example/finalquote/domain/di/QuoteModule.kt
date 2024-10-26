package com.example.finalquote.domain.di

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import com.example.finalquote.ui.theme.util.BASE_URL
import com.example.finalquote.data.Local.LocalQuoteDao
import com.example.finalquote.data.Local.QuoteDao
import com.example.finalquote.data.Local.QuoteDataBase
import com.example.finalquote.data.remote.QuoteApi
import com.example.finalquote.data.repository.QuoteRepositoryImp
import com.example.finalquote.domain.repository.QuoteRepository
import com.example.finalquote.domain.usecases.AddSearchQuote
import com.example.finalquote.domain.usecases.DeleteQuote
import com.example.finalquote.domain.usecases.DeleteSearchQuote
import com.example.finalquote.domain.usecases.GetAllLocalQuote
import com.example.finalquote.domain.usecases.GetAllQuotes
import com.example.finalquote.domain.usecases.GetLocalQuote
import com.example.finalquote.domain.usecases.GetLocalQuotes
import com.example.finalquote.domain.usecases.GetRemoteCategory
import com.example.finalquote.domain.usecases.GetRemoteQuote
import com.example.finalquote.domain.usecases.GetSearchLocalQuotes
import com.example.finalquote.domain.usecases.InsertQuote
import com.example.finalquote.domain.usecases.QuoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object QuoteModule {

    const val QUOTE_DATABASE_NAME = "quote_db"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun provideQuoteApi(retrofit: Retrofit): QuoteApi {
        return retrofit.create(QuoteApi::class.java)
    }


    @Provides
    @Singleton
    fun provideQuoteRepository(
        quoteApi: QuoteApi,
        quoteDao: QuoteDao,
        localQuoteDao: LocalQuoteDao
    ): QuoteRepository= QuoteRepositoryImp(quoteApi, quoteDao, localQuoteDao)


    @Provides
    @Singleton
    fun provideQuoteUseCase(quoteRepository: QuoteRepository): QuoteUseCase {
        return QuoteUseCase(
            getRemoteQuote = GetRemoteQuote(quoteRepository),
            getRemoteCategory = GetRemoteCategory(quoteRepository),
            getLocalQuotes = GetLocalQuotes(quoteRepository),
            insertQuote = InsertQuote((quoteRepository)),
            deleteQuote = DeleteQuote(quoteRepository),
            getLocalQuote = GetLocalQuote(quoteRepository),
            addSearchQuote = AddSearchQuote(quoteRepository),
            deleteSearchQuote = DeleteSearchQuote(quoteRepository),
            getAllLocalQuote = GetAllLocalQuote(quoteRepository),
            getSearchLocalQuotes = GetSearchLocalQuotes(quoteRepository),
            getAllQuotes = GetAllQuotes(quoteRepository),
        )
    }

    @Provides
    @Singleton
    fun provideQuoteDataBase(
        @ApplicationContext context: Context
    ): QuoteDataBase {
        return Room.databaseBuilder(
            context = context,
            klass = QuoteDataBase::class.java,
            name = QUOTE_DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideQuoteDao(quoteDataBase: QuoteDataBase): QuoteDao{
        return quoteDataBase.quoteDao
    }


    @Singleton
    @Provides
    fun provideLocalQuoteDao(quoteDataBase: QuoteDataBase): LocalQuoteDao{
        return quoteDataBase.localQuoteDao
    }

    //
    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManagerCompat {
        return NotificationManagerCompat.from(context)
    }
}