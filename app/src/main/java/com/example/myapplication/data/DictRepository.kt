package com.example.myapplication.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room

interface DictRepository {
    fun getWords(): LiveData<List<Word>>
    suspend fun insert(word: Word): Long
    suspend fun update(word: Word)
    suspend fun delete(word: Word)
    suspend fun deleteAll()

    class Base(context: Context) : DictRepository {
        private val room = Room.databaseBuilder(
            context,
            WordDatabase::class.java,
            DATABASE_NAME
        ).build()
        private val dao = room.wordDAO()
        override fun getWords() = dao.getAllWords()
        override suspend fun insert(word: Word) = dao.insertWord(word)
        override suspend fun update(word: Word) = dao.updateWord(word)
        override suspend fun delete(word: Word) = dao.deleteWord(word)
        override suspend fun deleteAll() = dao.deleteAll()

        companion object {
            private const val DATABASE_NAME = "word_data_database24"
        }
    }
}