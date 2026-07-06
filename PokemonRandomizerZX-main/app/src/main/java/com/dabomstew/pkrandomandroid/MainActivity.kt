package com.dabomstew.pkrandomandroid

import android.hardware.input.InputManager
import android.os.Bundle
import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dabomstew.pkrandomandroid.ui.navigation.AppNavigation
import com.dabomstew.pkrandomandroid.ui.theme.PokemonRandomizerTheme
import com.dabomstew.pkrandomandroid.util.ControllerDetector

class MainActivity : ComponentActivity(), InputManager.InputDeviceListener {
    private lateinit var inputManager: InputManager
    private var controllerConnected by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inputManager = getSystemService(InputManager::class.java)
        controllerConnected = ControllerDetector.isControllerConnected()
        enableEdgeToEdge()
        setContent {
            PokemonRandomizerTheme {
                AppNavigation(controllerConnected = controllerConnected)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        inputManager.registerInputDeviceListener(this, null)
        refreshControllerState()
    }

    override fun onPause() {
        inputManager.unregisterInputDeviceListener(this)
        super.onPause()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (ControllerDetector.isControllerEvent(event)) {
            controllerConnected = true
        }
        return super.dispatchKeyEvent(event)
    }

    override fun dispatchGenericMotionEvent(event: MotionEvent): Boolean {
        if (ControllerDetector.isControllerEvent(event)) {
            controllerConnected = true
        }
        return super.dispatchGenericMotionEvent(event)
    }

    override fun onInputDeviceAdded(deviceId: Int) = refreshControllerState()

    override fun onInputDeviceRemoved(deviceId: Int) = refreshControllerState()

    override fun onInputDeviceChanged(deviceId: Int) = refreshControllerState()

    private fun refreshControllerState() {
        controllerConnected = ControllerDetector.isControllerConnected()
    }
}
