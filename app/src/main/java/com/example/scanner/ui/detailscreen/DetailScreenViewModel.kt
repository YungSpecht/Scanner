package com.example.scanner.ui.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.documentstore.DocumentRepository
import kotlinx.coroutines.flow.MutableStateFlow
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
            isTitleEditing = !_uiState.value.isTitleEditing
        )
    }

    fun toggleBillAmountEdit() {
        _uiState.value = _uiState.value.copy(
            isBillAmountEditing = !_uiState.value.isBillAmountEditing
        )
    }

    fun toggleShowDialog() {
        _uiState.value = _uiState.value.copy(
            showDialog = !_uiState.value.showDialog
        )
    }

    fun updateTitle(newTitle: String) {
        _uiState.value = _uiState.value.document.copy(title = newTitle).let {
            _uiState.value.copy(
                document = it
            )
        }
    }

    fun updateBillAmount(newAmount: String) {
        _uiState.value = _uiState.value.document.copy(billAmount = newAmount).let {
            _uiState.value.copy(
                document = it
            )
        }
    }

    fun saveDocument() {
        _uiState.value.let { state ->
            viewModelScope.launch {
                state.document.let { documentRepository.updateDocument(it) }
            }
        }
    }

    fun deleteDocument() {
        viewModelScope.launch {
            _uiState.value.let { state ->
                state.document.let { documentRepository.deleteDocument(it) }
            }
        }
    }
}