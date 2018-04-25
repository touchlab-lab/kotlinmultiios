package co.touchlab.kurgan.util
import kotlinx.cinterop.*
import objcsrc.*

actual fun currentTimeMillis():Long = JavaLangSystem.currentTimeMillis()