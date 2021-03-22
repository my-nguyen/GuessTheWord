package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int): ViewModel() {
    companion object {
        const val TAG = "ScoreViewModel"
    }
    var score = finalScore

    init {
        Log.i(TAG, "Final score is $finalScore")
    }
}