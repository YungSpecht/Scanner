package com.example.scanner.ui.addscreen

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scanner.R
import com.example.scanner.ui.detailscreen.DetailScreenViewModel
import data.documentstore.Document
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.TextField
import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDocumentScreen(
    imageUri: Uri,
    onSave: () -> Unit
) {

    val viewModel: AddDocumentScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()


    val con: Context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.processDocument(imageUri, con)
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.document_details)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveDocument(imageUri)
                    onSave()
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = stringResource(R.string.save_document))
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TextField(
                value = uiState.title,
                onValueChange = viewModel::onTitleChanged,
                label = { Text(stringResource(R.string.title)) },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            TextField(
                value = uiState.billAmount,
                onValueChange = viewModel::onBillAmountChanged,
                label = { Text(stringResource(R.string.extracted_bill_amount)) },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = "Scanned Document",
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else {
                Text(
                    text = uiState.recognizedText,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
