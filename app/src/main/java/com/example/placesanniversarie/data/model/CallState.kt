package com.example.placesanniversarie.data.model

 class CallState {
    var msg = ""
    var state = CallState.Faild

    enum class CallState {
        SUCCESS, Faild
    }
}