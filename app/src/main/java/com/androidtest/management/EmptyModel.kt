package com.androidtest.management

import androidx.databinding.BaseObservable

data class EmptyModel(
        var empty: String
) : BaseObservable() {
    constructor() : this("")
}