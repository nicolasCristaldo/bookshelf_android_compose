package com.nicolascristaldo.bookshelf

import android.app.Application
import com.nicolascristaldo.bookshelf.data.AppContainer
import com.nicolascristaldo.bookshelf.data.DefaultContainer

class BookshelfApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer()
    }
}