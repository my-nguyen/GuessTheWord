package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int): ViewModel() {
    companion object {
        const val TAG = "ScoreViewModel"
    }
    // var score = finalScore
    // change score type to LiveData and add backing property
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // _eventPlayAgain is used to save the LiveData event to navigate from the score screen to the game screen
    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    init {
        Log.i(TAG, "Final score is $finalScore")
        _score.value = finalScore
    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }
}