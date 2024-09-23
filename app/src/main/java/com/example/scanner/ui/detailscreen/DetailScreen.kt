package com.example.scanner.ui.detailscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scanner.R
import data.Document
import java.net.URI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(documentId: String, modifier: Modifier = Modifier) {

    val viewModel: DetailScreenViewModel = hiltViewModel()
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
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier.padding(innerPadding)
        ) {
            Text(
                text = stringResource(R.string.title),
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = document.title,
                    fontSize = (22.sp),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                )
                Icon(
                    Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_title),
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
            Text(
                text = stringResource(R.string.extracted_bill_amount),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = document.billAmount,
                    fontSize = (22.sp),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                )
                Icon(
                    Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_title),
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
            Text(
                text = stringResource(R.string.document_scan),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.samplescan),
                contentDescription = "Sample Document"
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DocumentScreenPreview() {
    DocumentScreen(Document("22","Test Document", "5000€", URI("https://www.google.com")))
}