package com.cryptogamia.ui.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cryptogamia.data.model.BruteforceResultItem

class BruteforceItemViewModel() : ViewModel() {
    private val itemList = MutableLiveData<List<BruteforceResultItem>>()

    fun setItemList(items: List<BruteforceResultItem>) {
        itemList.value = items
    }

    fun getItemList(): LiveData<List<BruteforceResultItem>> {
        return itemList
    }
}