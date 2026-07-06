package com.dabomstew.pkrandomandroid.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dabomstew.pkrandomandroid.viewmodel.RandomizerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogScreen(
    viewModel: RandomizerViewModel,
    controllerConnected: Boolean,
    onDone: () -> Unit
) {
    val context = LocalContext.current
    val log by viewModel.log.collectAsState()
    val doneFocusRequester = remember { FocusRequester() }

    LaunchedEffect(controllerConnected) {
        if (controllerConnected) {
            doneFocusRequester.requestFocus()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Randomization Log") },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.clearLog()
                        onDone()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, log ?: "")
                                putExtra(Intent.EXTRA_SUBJECT, "Pokemon Randomizer ZX Log")
                            }
                            context.startActivity(
                                Intent.createChooser(shareIntent, "Share Log")
                            )
                        }
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share Log")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    onClick = {
                        viewModel.clearLog()
                        onDone()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(doneFocusRequester)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Done")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (log != null) {
                Card(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = log ?: "",
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(12.dp),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            } else {
                Text(
                    "No log available.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
