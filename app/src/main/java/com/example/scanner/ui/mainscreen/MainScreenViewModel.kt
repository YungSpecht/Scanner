package com.example.scanner.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.DocumentRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState = _uiState.asStateFlow()

    private val _scannerEvent = MutableStateFlow(false)
    val scannerEvent: StateFlow<Boolean> = _scannerEvent.asStateFlow()

    init {
        loadDocuments()
    }

    private fun loadDocuments() {
        viewModelScope.launch {
            try {
                val documents = documentRepository.getDocuments()
                _uiState.update { it.copy(documents = documents) }
            } catch (_: Exception) {
            }
        }
    }

    fun triggerDocumentScan() {
        _scannerEvent.value = true
    }

    fun scanEventHandled() {
        _scannerEvent.value = false
    }

}