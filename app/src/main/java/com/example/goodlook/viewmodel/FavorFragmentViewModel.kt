package com.example.goodlook.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.goodlook.database.CardDao
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.database.CardEntity
import com.example.goodlook.database.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavorFragmentViewModel(val database: CardDao, application: Application) : AndroidViewModel(application) {

    val allCards : LiveData<MutableList<CardEntity>>
    val repository : CardRepository


    init {
        val dao = CardDatabase.getInstance(application)!!.cardDao()
        repository = CardRepository(dao)
        allCards =  repository.allCards
    }
    private val cardsLiveData= repository.getAll()
    private val text = MutableLiveData("")
    private val _filteredCards = MediatorLiveData<List<CardEntity>>().apply {
        addSource(cardsLiveData) {
            combineLatestData()
        }
        addSource(text) {
            combineLatestData()
        }
    }
    val filteredCards: LiveData<List<CardEntity>> = _filteredCards

    private fun combineLatestData() {
        val latestCards = cardsLiveData.value ?: emptyList()
        val latestText = text.value ?: ""
        val filteredCards = latestCards.filter { it.cardName.contains(latestText, ignoreCase = true) }
        _filteredCards.value = filteredCards
    }
    fun onClickInsert(cardName:String,deadline: Int) {
        viewModelScope.launch {
            insert(cardName,deadline)
        }
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
        }
    }

    suspend fun clear() {
        repository.clear()
    }



    suspend fun insert(cardName:String,deadline: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(CardEntity(cardName,deadline))
        }
    }


    fun onSwipeDelete(card: CardEntity?){
        viewModelScope.launch(Dispatchers.IO) {
            if (card != null) {
                repository.delete(card)
            }
        }
    }

    suspend fun delete(card:CardEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(card)
        }
    }

    fun onQueryTextChange(text: String?) {
        this.text.value = text
    }
}