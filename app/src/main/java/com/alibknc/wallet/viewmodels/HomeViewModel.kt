package com.alibknc.wallet.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.alibknc.wallet.models.Cards
import com.alibknc.wallet.services.DBHelper

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    fun getCardList(): MutableList<Cards> {
        return DBHelper(getApplication<Application>().applicationContext).getCardList()
    }

}