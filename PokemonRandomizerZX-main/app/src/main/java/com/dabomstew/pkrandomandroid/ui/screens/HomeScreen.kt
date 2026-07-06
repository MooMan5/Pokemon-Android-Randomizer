package com.dabomstew.pkrandomandroid.ui.screens

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dabomstew.pkrandomandroid.util.SettingsSummary
import com.dabomstew.pkrandomandroid.viewmodel.RandomizerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: RandomizerViewModel,
    controllerConnected: Boolean,
    onNavigateToSettings: () -> Unit,
    onNavigateToLog: () -> Unit
) {
    val context = LocalContext.current
    val inputRomName by viewModel.inputRomName.collectAsState()
    val outputRomName by viewModel.outputRomName.collectAsState()
    val isRandomizing by viewModel.isRandomizing.collectAsState()
    val log by viewModel.log.collectAsState()
    val error by viewModel.error.collectAsState()
    val message by viewModel.message.collectAsState()
    val useRandomSeed by viewModel.useRandomSeed.collectAsState()
    val manualSeed by viewModel.manualSeed.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val changedSettings = remember(settings) { SettingsSummary.getChangedSettings(settings) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val firstFocusRequester = remember { FocusRequester() }

    // Navigate to log screen when randomization completes successfully
    LaunchedEffect(log) {
        if (log != null) {
            onNavigateToLog()
        }
    }

    LaunchedEffect(controllerConnected) {
        if (controllerConnected) {
            firstFocusRequester.requestFocus()
        }
    }

    // Show snackbar for preset load/save messages
    LaunchedEffect(message) {
        message?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.clearMessage()
            }
        }
    }

    // ROM file picker
    val romPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                context.contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.setInputRom(context, uri)
            }
        }
    }

    // Output file creator
    val outputFileLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                viewModel.setOutputRom(context, uri)
            }
        }
    }

    // Preset load picker
    val presetLoadLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri -> viewModel.loadPreset(context, uri) }
        }
    }

    // Preset save creator
    val presetSaveLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri -> viewModel.savePreset(context, uri) }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "PokemonrandomizerAC",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            "Universal Randomizer",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Error snackbar area
            error?.let { msg ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            msg,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = { viewModel.clearError() }) {
                            Text("Dismiss")
                        }
                    }
                }
            }

            // Input ROM card
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Input ROM", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        inputRomName ?: "No ROM selected",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (inputRomName != null)
                            MaterialTheme.colorScheme.onSurface
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                addCategory(Intent.CATEGORY_OPENABLE)
                                type = "*/*"
                                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(
                                    "application/octet-stream",
                                    "application/x-gameboy-rom",
                                    "*/*"
                                ))
                            }
                            romPickerLauncher.launch(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(firstFocusRequester)
                    ) {
                        Icon(Icons.Default.FileOpen, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Pick ROM")
                    }
                }
            }

            // Output file card
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Output ROM", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        outputRomName ?: "No output file set",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (outputRomName != null)
                            MaterialTheme.colorScheme.onSurface
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val ext = inputRomName?.substringAfterLast('.', "") ?: "gba"
                            val defaultName = "randomized_rom.$ext"
                            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                                addCategory(Intent.CATEGORY_OPENABLE)
                                type = "application/octet-stream"
                                putExtra(Intent.EXTRA_TITLE, defaultName)
                            }
                            outputFileLauncher.launch(intent)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Set Output")
                    }
                }
            }

            // Preset card
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Preset", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                    addCategory(Intent.CATEGORY_OPENABLE)
                                    type = "*/*"
                                }
                                presetLoadLauncher.launch(intent)
                            }
                        ) {
                            Text("Load Preset")
                        }
                        OutlinedButton(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                                    addCategory(Intent.CATEGORY_OPENABLE)
                                    type = "application/octet-stream"
                                    putExtra(Intent.EXTRA_TITLE, "settings.rnqs")
                                }
                                presetSaveLauncher.launch(intent)
                            }
                        ) {
                            Text("Save Preset")
                        }
                    }
                }
            }

            // Seed row
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Seed", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = useRandomSeed,
                            onCheckedChange = { viewModel.setUseRandomSeed(it) }
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Use random seed")
                    }
                    if (!useRandomSeed) {
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = manualSeed,
                            onValueChange = { viewModel.setManualSeed(it) },
                            label = { Text("Seed value (number)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }
            }

            // Active settings summary
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Active Settings", style = MaterialTheme.typography.titleMedium)
                        if (changedSettings.isNotEmpty()) {
                            Text(
                                "${changedSettings.size} modified",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    if (changedSettings.isEmpty()) {
                        Text(
                            "All settings at default",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    } else {
                        val visible = changedSettings.take(8)
                        val overflow = changedSettings.size - visible.size
                        visible.forEach { entry ->
                            Text(
                                "• $entry",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        if (overflow > 0) {
                            Text(
                                "… and $overflow more",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToSettings
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Settings")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    enabled = inputRomName != null && outputRomName != null && !isRandomizing,
                    onClick = { viewModel.randomize(context) }
                ) {
                    if (isRandomizing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Randomizing...")
                    } else {
                        Icon(Icons.Default.Casino, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("Randomize!")
                    }
                }
            }
        }
    }
}
