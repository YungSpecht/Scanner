package com.example.scanner.ui.addscreen

import android.content.Context
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
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
                // Call MLKit to process document
                mlKitProcessor.processDocument(imageUri, context) { recognizedText ->
                    _uiState.value = _uiState.value.copy(
                        recognizedText = recognizedText,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                // Handle any errors during processing
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            }
        }
    }

    // Handle title changes
    fun onTitleChanged(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
    }

    // Handle bill amount changes
    fun onBillAmountChanged(newAmount: String) {
        _uiState.value = _uiState.value.copy(billAmount = newAmount)
    }

    // Save the document to the repository
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