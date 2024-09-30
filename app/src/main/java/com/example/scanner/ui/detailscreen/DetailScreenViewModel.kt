package com.example.scanner.ui.detailscreen

import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.documentstore.Document
import data.documentstore.DocumentRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenState())
    val uiState = _uiState.asStateFlow()

    fun loadDocument(documentId: Long) {
        viewModelScope.launch {
            val document = documentRepository.getDocumentById(documentId)
            document?.let {
                _uiState.value = DetailScreenState(document = it)
            }
        }
    }

    fun toggleTitleEdit() {
        _uiState.value = _uiState.value.copy(
            isTitleEditing = !(_uiState.value.isTitleEditing ?: false)
        )
    }

    fun toggleBillAmountEdit() {
        _uiState.value = _uiState.value.copy(
            isBillAmountEditing = !(_uiState.value.isBillAmountEditing ?: false)
        )
    }

    fun updateTitle(newTitle: String) {
        _uiState.value = _uiState.value.document?.copy(title = newTitle)?.let {
            _uiState.value.copy(
                document = it
            )
        }!!
    }

    fun updateBillAmount(newAmount: String) {
        _uiState.value = _uiState.value.document?.copy(billAmount = newAmount)?.let {
            _uiState.value.copy(
                document = it
            )
        }!!
    }
    fun saveDocument(document: Document) {
        viewModelScope.launch {
            documentRepository.addDocument(document)
        }
    }


    // Save the updated title or bill amount in the repository
    fun saveDocument() {
        _uiState.value.let { state ->
            viewModelScope.launch {
                state.document?.let { documentRepository.updateDocument(it) }
            }
        }
    }

    fun deleteDocument() {
        viewModelScope.launch {
            _uiState.value.let { state ->
                state.document?.let { documentRepository.deleteDocument(it) }
            }
        }
    }

}