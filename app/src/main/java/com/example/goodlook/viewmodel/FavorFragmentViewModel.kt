package com.example.goodlook.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.*
import com.example.goodlook.AlarmReceiver
import com.example.goodlook.database.CardDao
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.database.CardEntity
import com.example.goodlook.database.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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


    fun onClickInsert(cardName:String,deadline:Long,cardCategory:String, requestCode: Int) {
        viewModelScope.launch {
            insert(cardName,deadline,cardCategory,requestCode)
        }
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
        }
    }

    suspend fun clear() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clear()
        }
    }








    suspend fun insert(cardName:String,deadline:Long, cardCategory: String, requestCode: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(CardEntity(cardName,deadline,cardCategory,requestCode))
        }
    }


    private val usedRequestIds = mutableSetOf<Int>()

    fun generateUniqueRequestId(): Int {
        var requestId: Int
        do {
            requestId = (0..Int.MAX_VALUE).random() // Generate a random request ID
        } while (usedRequestIds.contains(requestId))

        // Add the generated ID to the set of used IDs
        usedRequestIds.add(requestId)

        return requestId
    }

    fun onDone(card: CardEntity?){
        viewModelScope.launch(Dispatchers.IO) {

            if (card != null) {
                repository.delete(card)
                cancelNotificationForCard(card)
                usedRequestIds.remove(card.requestCode)
            }

        }
    }

    private fun cancelNotificationForCard(card: CardEntity) {
        // Create an intent for the cancel operation
        val cancelIntent = Intent(getApplication<Application>().applicationContext, AlarmReceiver::class.java)

        // Create a PendingIntent for the cancel operation with the card's requestCode
        val cancelPendingIntent = PendingIntent.getBroadcast(
            getApplication<Application>().applicationContext,
            card.requestCode,
            cancelIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Cancel the alarm/notification associated with this card
        val alarmManager = getApplication<Application>().applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(cancelPendingIntent)
    }

    //DeleteByDeadline
    fun deleteByDeadline() {
        viewModelScope.launch {

                val currentTime = System.currentTimeMillis() / 1000L // Convert to seconds
                repository.deleteByDeadline(currentTime)


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