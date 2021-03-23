/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.databinding.FragmentGameBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {
    companion object {
        const val TAG = "GameFragment"
    }

    // word, score and wordList are moved to GameViewModel
    /*private var word = ""
    private var score = 0
    private lateinit var wordList: MutableList<String>*/

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        Log.i(TAG, "ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // resetList() and nextWord() are moved to GameViewModel
        /*resetList()
        nextWord()*/

        // attach an Observer to the LiveData score. the Observer receives an event when the data
        // held by the observed LiveData object changes. so when the value of score or the word
        // changes, the score or word displayed on the screen now updates automatically
        viewModel.score.observe(viewLifecycleOwner, {
            binding.scoreText.text = it.toString()
        })
        viewModel.word.observe(viewLifecycleOwner, {
            binding.wordText.text = it
        })

        // attach an Observer to eventGameFinish
        viewModel.eventGameFinish.observe(viewLifecycleOwner, {
            if (it) {
                gameFinished()
            }
        })

        viewModel.currentTimeString.observe(viewLifecycleOwner, {
            binding.timerText.text = it
        })

        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }

        // updateScoreText and updateWordText have been replaced by the Observer for score and word above
        /*updateScoreText()
        updateWordText()*/
        return binding.root
    }

    /** Methods for buttons presses **/
    private fun onSkip() {
        /*score--
        nextWord()*/
        viewModel.onSkip()
        // updateScoreText and updateWordText have been replaced by the Observer for score and word in onCreateView()
        /*updateWordText()
        updateScoreText()*/
    }

    private fun onCorrect() {
        /*score++
        nextWord()*/
        viewModel.onSkip()
        // updateScoreText and updateWordText have been replaced by the Observer for score and word in onCreateView()
        /*updateWordText()
        updateScoreText()*/
    }

    // updateScoreText and updateWordText have been replaced by the Observer for score and word in onCreateView()
    /*private fun updateWordText() {
        // binding.wordText.text = word
        // binding.wordText.text = viewModel.word
        binding.wordText.text = viewModel.word.value
    }

    private fun updateScoreText() {
        // binding.scoreText.text = score.toString()
        // binding.scoreText.text = viewModel.score.toString()
        binding.scoreText.text = viewModel.score.value.toString()
    }*/

    private fun onEndGame() {
        gameFinished()
    }

    // Called when the game is finished
    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()

        // navigate the app to the score screen. Pass in the score as an argument using Safe Args.
        val action = GameFragmentDirections.actionGameToScore()
        // action.score = viewModel.score
        action.score = viewModel.score.value?:0
        NavHostFragment.findNavController(this).navigate(action)

        // a screen rotation causes GameFragment to be recreated, and in onCreateView(), GameFragment
        // will observe and receive the latest value of viewModel.eventGameFinish which is true,
        // and so will call gameFinished() and create and display a new Toast: a screen rotation
        // creates a new Toast. so viewModel._eventGameFinish needs to be reset to false.
        viewModel.onGameFinishComplete()
    }
}
