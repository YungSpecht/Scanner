package com.example.scanner.ui.detailscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.scanner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(documentId: Long, onDelete: () -> Unit, modifier: Modifier = Modifier) {

    val viewModel: DetailScreenViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.loadDocument(documentId)
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.document_details),
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier.padding(innerPadding)
        ) {
            Text(
                text = stringResource(R.string.title),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (uiState.isTitleEditing) {
                    TextField(
                        value = uiState.document.title,
                        onValueChange = viewModel::updateTitle,
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )
                    Button(
                        onClick = { viewModel.toggleTitleEdit(); viewModel.saveDocument() },
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { viewModel.toggleTitleEdit() }
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                } else {
                    Text(
                        text = uiState.document.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_title),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { viewModel.toggleTitleEdit() }
                    )
                }
            }
            Spacer(modifier = Modifier.height(26.dp))
            Text(
                text = stringResource(R.string.extracted_bill_amount),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (uiState.isBillAmountEditing) {
                    TextField(
                        value = uiState.document.billAmount,
                        onValueChange = viewModel::updateBillAmount,
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )
                    Button(
                        onClick = { viewModel.toggleBillAmountEdit(); viewModel.saveDocument() },
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { viewModel.toggleBillAmountEdit() }
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                } else {
                    Text(
                        text = "${uiState.document.billAmount} €",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_bill_amount),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { viewModel.toggleBillAmountEdit() }
                    )
                }
            }
            Spacer(modifier = Modifier.height(26.dp))
            Text(
                text = stringResource(R.string.document_scan),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(model = uiState.document.imageUri),
                contentDescription = "Scanned Document",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
            )
            Button(
                onClick = { viewModel.toggleShowDialog() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = stringResource(R.string.delete_document))
            }
            if (uiState.showDialog) {
                AlertDialog(
                    onDismissRequest = { viewModel.toggleShowDialog() },
                    title = { Text(stringResource(R.string.confirm_deletion)) },
                    text = { Text(stringResource(R.string.delete_document_confirmation)) },
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.deleteDocument()
                                viewModel.toggleShowDialog()
                                onDelete()
                            }
                        ) {
                            Text(stringResource(R.string.delete))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { viewModel.toggleShowDialog() }
                        ) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                )
            }
        }
    }
}

