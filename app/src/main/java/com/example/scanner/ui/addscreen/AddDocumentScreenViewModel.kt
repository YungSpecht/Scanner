package com.example.scanner.ui.addscreen

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.documentstore.Document
import data.documentstore.DocumentRepository
import data.textproc.MLKitProcessor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDocumentScreenViewModel @Inject constructor(
    private val documentRepository: DocumentRepository,
    private val mlKitProcessor: MLKitProcessor
) : ViewModel()
{
    private val _uiState = MutableStateFlow(AddDocumentScreenState())
    val uiState = _uiState.asStateFlow()

    fun processDocument(imageUri: Uri, context: Context) {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                mlKitProcessor.processDocument(imageUri, context) { recognizedText ->
                    val extracted = recognizedText.split("|")
                    _uiState.value = _uiState.value.copy(
                        title = extracted[0],
                        billAmount = extracted[1],
                        recognizedText = recognizedText,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            }
        }
    }

    fun onTitleChanged(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
    }

    fun onBillAmountChanged(newAmount: String) {
        _uiState.value = _uiState.value.copy(billAmount = newAmount)
    }

    fun saveDocument(imageUri: Uri) {
        viewModelScope.launch {
            val newDocument = Document(
                id = 0L,
                title = _uiState.value.title,
                billAmount = _uiState.value.billAmount,
                imageUri = imageUri.toString()
            )
            documentRepository.addDocument(newDocument)
        }
    }
}