package com.example.guardiangrab.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel()  {


    public var _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index)
    {
        "Details Section: $it"
    }


    fun setIndex(index: Int) {
        _index.value = index
    }

}