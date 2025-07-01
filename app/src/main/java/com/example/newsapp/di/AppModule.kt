package com.example.newsapp.di

import com.example.newsapp.data.api.NewsApiService
import com.example.newsapp.data.db.AppDatabase
import com.example.newsapp.data.repository.BookmarkRepositoryImpl
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.domain.repository.BookmarkRepository
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.usecase.BookmarkUseCase
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.ui.bookmarks.BookmarkViewModel
import com.example.newsapp.ui.main.MainViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.room.Room

val appModule = module {

    // Network
    single {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    // Database
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration() // ini biar tidak crash saat struktur DB berubah
            // .addMigrations(MIGRATION_1_2) // Kalau nanti pakai Migration manual, tinggal aktifkan ini
            .build()
    }

    // DAO
    single { get<AppDatabase>().articleDao() }
    single { get<AppDatabase>().bookmarkDao() }
    single { get<AppDatabase>().favouriteDao() }

    // Repository
    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }
    single<BookmarkRepository> { BookmarkRepositoryImpl(get()) }

    // UseCase
    factory { GetTopHeadlinesUseCase(get()) }
    factory { BookmarkUseCase(get()) }

    // ViewModel
    viewModel { MainViewModel(get()) }
    viewModel { BookmarkViewModel(get()) }
}