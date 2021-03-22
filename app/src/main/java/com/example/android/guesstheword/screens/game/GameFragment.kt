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

        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }

        updateScoreText()
        updateWordText()
        return binding.root
    }

    /** Methods for buttons presses **/
    private fun onSkip() {
        /*score--
        nextWord()*/
        viewModel.onSkip()
        updateWordText()
        updateScoreText()
    }

    private fun onCorrect() {
        /*score++
        nextWord()*/
        viewModel.onSkip()
        updateWordText()
        updateScoreText()
    }

    /** Methods for updating the UI **/

    private fun updateWordText() {
        // binding.wordText.text = word
        binding.wordText.text = viewModel.word
    }

    private fun updateScoreText() {
        // binding.scoreText.text = score.toString()
        binding.scoreText.text = viewModel.score.toString()
    }

    private fun onEndGame() {
        gameFinished()
    }

    // Called when the game is finished
    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()

        // navigate the app to the score screen. Pass in the score as an argument using Safe Args.
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score
        NavHostFragment.findNavController(this).navigate(action)
    }
}
