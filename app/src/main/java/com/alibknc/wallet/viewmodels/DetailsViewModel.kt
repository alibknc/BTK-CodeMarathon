package com.alibknc.wallet.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.alibknc.wallet.models.Card
import com.alibknc.wallet.services.DBHelper

class DetailsViewModel(application: Application) : AndroidViewModel(application)  {
    fun getCard(id: String): Card {
        return DBHelper(getApplication<Application>().applicationContext).getCardWithID(id)
    }

    fun deleteCard(id: String): Int {
        return DBHelper(getApplication<Application>().applicationContext).deleteCard(id)
    }
}