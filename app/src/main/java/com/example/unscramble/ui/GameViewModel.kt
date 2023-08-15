package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState>
        get() = _uiState.asStateFlow()

    private var usedWords: MutableSet<String> = mutableSetOf()

    private lateinit var currentWord: String

    init{
        resetGame()
    }

    private fun pickRandomWordAndShuffle() : String{
        currentWord = allWords.random()

        if(usedWords.contains(currentWord))
            return pickRandomWordAndShuffle()

        usedWords.add(currentWord)
        return shuffleWord(currentWord)
    }

    private fun shuffleWord(word: String): String{
        val wordAsArray = word.toCharArray()
        wordAsArray.shuffle()

        while(wordAsArray.toString() == word)
            wordAsArray.shuffle()

        return wordAsArray.toString()
    }

    private fun resetGame(){
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }
}