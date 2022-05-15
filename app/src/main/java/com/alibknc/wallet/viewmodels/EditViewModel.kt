package com.alibknc.wallet.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.alibknc.wallet.models.Cards
import com.alibknc.wallet.services.DBHelper

class EditViewModel(application: Application) : AndroidViewModel(application)  {

    fun saveCard(card: Cards): Long {
        return DBHelper(getApplication<Application>().applicationContext).insertData(card)
    }

    fun updateCard(card: Cards, pcn: String): Int {
        return DBHelper(getApplication<Application>().applicationContext).updateData(card, pcn)
    }

    fun getCard(id: String): Cards {
        return DBHelper(getApplication<Application>().applicationContext).getCardWithID(id)
    }
}