package com.cryptogamia.ui.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cryptogamia.data.model.FrequencyItem

class FrequencyAnalysisViewModel : ViewModel() {

    private val itemList: MutableLiveData<List<FrequencyItem>> =
        MutableLiveData<List<FrequencyItem>>()

    fun getItemList(): MutableLiveData<List<FrequencyItem>> {
        return itemList
    }

    fun setItemList(itemList: List<FrequencyItem>) {
        this.itemList.value = itemList
    }
}