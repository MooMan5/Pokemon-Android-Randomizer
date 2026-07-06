package com.dabomstew.pkrandomandroid.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabomstew.pkrandom.FileFunctions
import com.dabomstew.pkrandom.Randomizer
import com.dabomstew.pkrandom.RandomSource
import com.dabomstew.pkrandom.Settings
import com.dabomstew.pkrandom.romhandlers.Gen1RomHandler
import com.dabomstew.pkrandom.romhandlers.Gen2RomHandler
import com.dabomstew.pkrandom.romhandlers.Gen3RomHandler
import com.dabomstew.pkrandom.romhandlers.Gen4RomHandler
import com.dabomstew.pkrandom.romhandlers.Gen5RomHandler
import com.dabomstew.pkrandom.romhandlers.Gen6RomHandler
import com.dabomstew.pkrandom.romhandlers.Gen7RomHandler
import com.dabomstew.pkrandom.romhandlers.RomHandler
import com.dabomstew.pkrandomandroid.util.FileUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import java.util.ResourceBundle

class RandomizerViewModel : ViewModel() {

    private val _inputRomUri = MutableStateFlow<Uri?>(null)
    val inputRomUri: StateFlow<Uri?> = _inputRomUri.asStateFlow()

    private val _inputRomName = MutableStateFlow<String?>(null)
    val inputRomName: StateFlow<String?> = _inputRomName.asStateFlow()

    private val _outputRomUri = MutableStateFlow<Uri?>(null)
    val outputRomUri: StateFlow<Uri?> = _outputRomUri.asStateFlow()

    private val _outputRomName = MutableStateFlow<String?>(null)
    val outputRomName: StateFlow<String?> = _outputRomName.asStateFlow()

    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> = _settings.asStateFlow()

    private val _useRandomSeed = MutableStateFlow(true)
    val useRandomSeed: StateFlow<Boolean> = _useRandomSeed.asStateFlow()

    private val _manualSeed = MutableStateFlow("")
    val manualSeed: StateFlow<String> = _manualSeed.asStateFlow()

    private val _isRandomizing = MutableStateFlow(false)
    val isRandomizing: StateFlow<Boolean> = _isRandomizing.asStateFlow()

    private val _log = MutableStateFlow<String?>(null)
    val log: StateFlow<String?> = _log.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    fun setInputRom(context: Context, uri: Uri) {
        _inputRomUri.value = uri
        _inputRomName.value = FileUtil.getFileNameFromUri(context, uri)
    }

    fun setOutputRom(context: Context, uri: Uri) {
        _outputRomUri.value = uri
        _outputRomName.value = FileUtil.getFileNameFromUri(context, uri)
    }

    fun setUseRandomSeed(value: Boolean) {
        _useRandomSeed.value = value
    }

    fun setManualSeed(value: String) {
        _manualSeed.value = value
    }

    /** Returns a deep copy of current settings for editing in the SettingsScreen. */
    fun createEditableCopy(): Settings {
        return try {
            Settings.fromString(_settings.value.toString())
        } catch (e: Exception) {
            Settings()
        }
    }

    /** Replaces the active settings with the edited copy. Called only when the user taps Save. */
    fun commitSettings(edited: Settings) {
        _settings.value = edited
    }

    fun clearError() {
        _error.value = null
    }

    fun clearLog() {
        _log.value = null
    }

    fun randomize(context: Context) {
        val inputUri = _inputRomUri.value ?: return
        val outputUri = _outputRomUri.value ?: return

        viewModelScope.launch(Dispatchers.IO) {
            _isRandomizing.value = true
            _error.value = null
            _log.value = null
            var outputFile: File? = null
            try {
                // 1. Copy input ROM to temp file
                val inputFile = FileUtil.copyUriToTempFile(context, inputUri, "input_rom")
                outputFile = File(context.cacheDir, "output_rom.tmp")

                // 2. Set up ResourceBundle
                val bundle = ResourceBundle.getBundle("com/dabomstew/pkrandom/newgui/Bundle")

                // 3. Detect ROM handler
                val factories: Array<RomHandler.Factory> = arrayOf(
                    Gen1RomHandler.Factory(),
                    Gen2RomHandler.Factory(),
                    Gen3RomHandler.Factory(),
                    Gen4RomHandler.Factory(),
                    Gen5RomHandler.Factory(),
                    Gen6RomHandler.Factory(),
                    Gen7RomHandler.Factory()
                )
                val romHandler: RomHandler = factories.firstNotNullOfOrNull { factory ->
                    if (factory.isLoadable(inputFile.absolutePath)) {
                        factory.create(RandomSource.instance()).also { handler ->
                            handler.loadRom(inputFile.absolutePath)
                        }
                    } else null
                } ?: throw java.io.IOException("Unsupported or unrecognized ROM format")

                // 4. Set custom names
                val currentSettings = _settings.value
                currentSettings.customNames = FileFunctions.getCustomNames()

                // 5. Determine seed
                val seed: Long = if (_useRandomSeed.value) {
                    RandomSource.pickSeed()
                } else {
                    _manualSeed.value.trim().toLongOrNull()
                        ?: throw IllegalArgumentException("Invalid seed value: '${_manualSeed.value}'")
                }

                // 6. Run randomizer
                val baos = ByteArrayOutputStream()
                val logStream = PrintStream(baos, false, "UTF-8")
                val randomizer = Randomizer(currentSettings, romHandler, bundle, false)
                randomizer.randomize(outputFile.absolutePath, logStream, seed)
                logStream.close()

                // 7. Copy output to SAF URI
                context.contentResolver.openOutputStream(outputUri)?.use { out ->
                    outputFile.inputStream().use { input -> input.copyTo(out) }
                } ?: throw java.io.IOException("Cannot open output stream")

                _log.value = baos.toString("UTF-8")
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                outputFile?.delete()
                _isRandomizing.value = false
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }

    fun loadPreset(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                    ?: throw java.io.IOException("Cannot read preset file")
                val base64 = String(bytes, Charsets.UTF_8).trim()
                val newSettings = Settings.fromString(base64)
                _settings.value = newSettings
                _message.value = "Preset loaded successfully"
            } catch (e: Exception) {
                _error.value = "Failed to load preset: ${e.message}"
            }
        }
    }

    fun savePreset(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val settingsString = _settings.value.toString()
                context.contentResolver.openOutputStream(uri)?.use { out ->
                    out.write(settingsString.toByteArray(Charsets.UTF_8))
                } ?: throw java.io.IOException("Cannot write preset file")
                _message.value = "Preset saved successfully"
            } catch (e: Exception) {
                _error.value = "Failed to save preset: ${e.message}"
            }
        }
    }
}
