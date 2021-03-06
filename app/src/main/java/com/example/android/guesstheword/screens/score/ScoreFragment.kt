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

package com.example.android.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.guesstheword.databinding.FragmentScoreBinding

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate view and obtain an instance of the binding class.
        val binding = FragmentScoreBinding.inflate(inflater, container, false)

        // retrieve final score from the argument bundle
        val score = ScoreFragmentArgs.fromBundle(requireArguments()).score
        // initialize the viewModelFactory with the score
        viewModelFactory = ScoreViewModelFactory(score)

        // create ScoreViewModel object using the factory method defined in ScoreViewModelFactory
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)

        // set the text in scoreText to the final score in the ScoreViewModel
        // binding.scoreText.text = viewModel.score.toString()
        // use an Observer to observe changes to viewModel.score
        viewModel.score.observe(viewLifecycleOwner, {
            binding.scoreText.text = it.toString()
        })

        // an observer for eventPlayAgain
        viewModel.eventPlayAgain.observe(viewLifecycleOwner, {
            if (it) {
                // navigate back to the game screen
                val directions = ScoreFragmentDirections.actionRestart()
                findNavController().navigate(directions)
                // reset eventPlayAgain
                viewModel.onPlayAgainComplete()
            }
        })

        binding.playAgainButton.setOnClickListener {  viewModel.onPlayAgain()  }

        return binding.root
    }
}
