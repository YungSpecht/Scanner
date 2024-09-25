package com.example.scanner.ui.addscreen

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
import data.Document
import java.util.UUID
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.TextField
import androidx.compose.foundation.Image
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDocumentScreen(
    imageUri: Uri,
    onSave: () -> Unit
) {

    val viewModel: DetailScreenViewModel = hiltViewModel()

    // TODO Tzvetan fragen ob hier VM mit UIstate gebraucht wird
    var title by remember { mutableStateOf("") }
    var billAmount by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.document_details)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // TODO Tzvetan fragen ob verlagern auf VM
                    val newDocument = Document(
                        id = 0L,
                        title = title,
                        billAmount = billAmount,
                        imageUri = imageUri.toString()
                    )
                    viewModel.saveDocument(newDocument)
                    onSave()
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = stringResource(R.string.save_document))
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.title)) },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            TextField(
                value = billAmount,
                onValueChange = { billAmount = it },
                label = { Text(stringResource(R.string.extracted_bill_amount)) },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = "Scanned Document",
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddDocumentScreenPreview() {
    AddDocumentScreen(Uri.EMPTY, {})
}