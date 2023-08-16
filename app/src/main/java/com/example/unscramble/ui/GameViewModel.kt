package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState>
        get() = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

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

        return String(wordAsArray)
    }

    private fun resetGame(){
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }
}