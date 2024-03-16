package com.example.myapplication.ui

import androidx.databinding.Observable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DictRepository
import com.example.myapplication.data.Word
import kotlinx.coroutines.launch

class DictViewModel(private val repository: DictRepository) : ViewModel(), Observable {

    val inputWord = MutableLiveData<String>()


    val inputTranslate = MutableLiveData<String>()


    val saveOrUpdateButtonText = MutableLiveData<String>()


    val clearAllOrDeleteButtonText = MutableLiveData<String>()


    private var isUpdateOrDelete = false
    private lateinit var wordToUpdateOrDelete: Word

    init {
        addText()
    }

    fun addText() {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    fun observeWords(owner: LifecycleOwner, observer: Observer<List<Word>>) =
        repository.getWords().observe(owner, observer)


    fun saveOrUpdate() {
        if (!dataIsEmpty(inputWord) || !dataIsEmpty(inputTranslate)) {
            if (isUpdateOrDelete) {
                wordToUpdateOrDelete.apply {
                    word = inputWord.value ?: ""
                    translate = inputTranslate.value ?: ""
                }
                viewModelScope.launch {
                    repository.update(wordToUpdateOrDelete)
                    refreshUI()

                }
            } else {
                viewModelScope.launch {
                    repository.insert(
                        Word(0, inputWord.value ?: "", inputTranslate.value ?: "")
                    )
                }
                clearText()
            }
        }
    }

    private fun dataIsEmpty(data: MutableLiveData<String>) = data.value == null || data.value == ""

    private fun refreshUI() {
        clearText()
        isUpdateOrDelete = false
        addText()
    }

    private fun clearText() {
        inputWord.value = ""
        inputTranslate.value = ""
    }

    fun initUpdateAndDelete(word: Word) {
        inputWord.value = word.word
        inputTranslate.value = word.translate

        isUpdateOrDelete = true
        wordToUpdateOrDelete = word

        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    fun clearAllOrDelete() = if (isUpdateOrDelete)
        viewModelScope.launch {
            repository.delete(wordToUpdateOrDelete)
            refreshUI()
        } else viewModelScope.launch {
        repository.deleteAll()

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}
