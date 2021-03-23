package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    companion object {
        const val TAG = "GameViewModel"
        // Time when the game is over
        private const val DONE = 0L
        // Countdown time interval
        private const val ONE_SECOND = 1000L
        // Total time for the game
        private const val COUNTDOWN_TIME = 60000L
    }

    // word, score and wordList are moved from GameFragment
    /*var word = ""
    var score = 0*/
    private lateinit var wordList: MutableList<String>
    // public LiveData word and score are accompanied by backing properties private MutableLiveData _word and _score
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // Event which triggers the end of the game
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    // Countdown time
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // use Transformations.map() to manipulate data on currentTime, which is of type LiveData, and
    // return a LiveData object, which is the the String version of currentTime
    val currentTimeString = Transformations.map(currentTime) { time ->
        // format the time (in long, which is the number of seconds) to "MM:SS" string format
        DateUtils.formatElapsedTime(time)
    }

    private val timer: CountDownTimer

    init {
        Log.i(TAG, "init")
        // initialize LiveData word and score
        _word.value = ""
        _score.value = 0

        // resetList() and nextWord() are moved from GameFragment
        resetList()
        nextWord()

        // Creates a timer which triggers the end of the game when it finishes
        timer = object: CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            // this method is called on every interval or on every tick
            // millisUntilFinished: the amount of time until the timer is finished in milliseconds
            override fun onTick(millisUntilFinished: Long) {
                // Convert millisUntilFinished to seconds and update _currentTime with it
                _currentTime.value = millisUntilFinished / ONE_SECOND
            }

            // this method is called when the timer is finished
            override fun onFinish() {
                // update the _currentTime
                _currentTime.value = DONE
                // trigger the game finish event
                onGameFinish()
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared")
        // Cancel the timer
        timer.cancel()
    }

    // Resets the list of words and randomizes the order
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    // Moves to the next word in the list
    private fun nextWord() {
        if (wordList.isEmpty()) {
            // game finishes when all the words are exhausted
            // onGameFinish()
            // Shuffle the word list, if the list is empty
            resetList()
        } else {
            // remove the first entry from wordList and assign it to _word
            // word = wordList.removeAt(0)
            // update word as a LiveData field
            _word.value = wordList.removeAt(0)
        }
        // updateWordText and updateScoreText are UI methods so they remain in GameFragment
        /*updateWordText()
        updateScoreText()*/
    }

    // onSkip() and onCorrect() are copied (not moved) from GameFragment
    fun onSkip() {
        // score--
        // update score as a LiveData field
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        // score++
        // update score as a LiveData field
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    // Method for the game completed event
    fun onGameFinish() {
        Log.i(TAG, "onGameFinish, setting _eventGameFinish")
        // this will trigger the Observer in GameFragment.onCreateView() to call gameFinished()
        // which displays a Toast
        _eventGameFinish.value = true
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }
}