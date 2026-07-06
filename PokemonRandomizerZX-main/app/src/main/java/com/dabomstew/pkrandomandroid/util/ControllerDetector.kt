package com.dabomstew.pkrandomandroid.util

import android.view.InputDevice
import android.view.InputEvent

object ControllerDetector {
    fun isControllerConnected(): Boolean =
        InputDevice.getDeviceIds()
            .asIterable()
            .mapNotNull(InputDevice::getDevice)
            .any(::isController)

    fun isControllerEvent(event: InputEvent): Boolean =
        event.device?.let(::isController) == true || hasControllerSource(event.source)

    private fun isController(device: InputDevice): Boolean =
        !device.isVirtual && hasControllerSource(device.sources)

    private fun hasControllerSource(sources: Int): Boolean {
        val controllerSources = listOf(
            InputDevice.SOURCE_GAMEPAD,
            InputDevice.SOURCE_JOYSTICK,
            InputDevice.SOURCE_DPAD
        )
        return controllerSources.any { source -> sources and source == source }
    }
}
