package com.androidtest.myapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidtest.myapp.interfaces.ITowerBlackTest

@Suppress("UNCHECKED_CAST")
class TowerBlackTestViewModelFactory(private val act: ITowerBlackTest.View) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TowerBlockTestViewModel(act) as T
    }

}