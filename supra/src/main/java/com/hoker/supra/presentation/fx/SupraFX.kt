package com.hoker.supra.presentation.fx

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.VibrationEffect
import android.os.VibratorManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import com.hoker.supra.R
import com.hoker.supra.di.SupraModule.SupraSharedPrefs
import com.hoker.supra.domain.Consts
import com.hoker.supra.domain.OverlayState
import androidx.core.content.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@SuppressLint("MissingPermission")
@Singleton
class SupraFX @Inject constructor(
    @ApplicationContext private val context: Context,
    @SupraSharedPrefs private val sharedPreferences: SharedPreferences
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var mediaPlayer = MediaPlayer.create(context, R.raw.connect)
    private val vibratorManager: VibratorManager? = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager

    private val _oneshotChannel = Channel<Color>(Channel.BUFFERED)
    val oneshotChannel: ReceiveChannel<Color> = _oneshotChannel

    private val _snackbarChannel = Channel<String>(Channel.BUFFERED)
    val snackbarChannel: ReceiveChannel<String> = _snackbarChannel

    private val _overlayState = MutableStateFlow(OverlayState.INACTIVE)
    val overlayState = _overlayState.asStateFlow()

    private val _overlayCustomContent = MutableStateFlow<(@Composable () -> Unit)?>(null)
    val overlayCustomContent = _overlayCustomContent.asStateFlow()

    val isLoading: StateFlow<Boolean> = overlayState
        .map { it != OverlayState.INACTIVE }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = false
        )

    private val _isAudioEnabled = MutableStateFlow(sharedPreferences.getBoolean(Consts.SETTINGS_AUDIO_ENABLED, true))
    val isAudioEnabled = _isAudioEnabled.asStateFlow()

    private val _isVibrationEnabled = MutableStateFlow(sharedPreferences.getBoolean(Consts.SETTINGS_VIBRATION_ENABLED, true))
    val isVibrationEnabled = _isVibrationEnabled.asStateFlow()

    fun setLoadingState(state: OverlayState) {
        if (state == OverlayState.INACTIVE) {
            _overlayCustomContent.value = null
        }
        _overlayState.value = state
    }

    fun showScanPromptWithCustomContent(content: @Composable () -> Unit) {
        _overlayCustomContent.value = content
        _overlayState.value = OverlayState.SCAN_PROMPT_CUSTOM_CONTENT
    }

    fun showOverlayWithCustomContent(content: @Composable () -> Unit) {
        _overlayCustomContent.value = content
        _overlayState.value = OverlayState.CUSTOM_CONTENT
    }

    fun setAudioSettingState(enabled: Boolean) {
        sharedPreferences.edit {
            putBoolean(Consts.SETTINGS_AUDIO_ENABLED, enabled)
        }
        _isAudioEnabled.value = enabled
    }

    fun setVibrationSettingState(enabled: Boolean) {
        sharedPreferences.edit {
            putBoolean(Consts.SETTINGS_VIBRATION_ENABLED, enabled)
            apply()
        }
        _isVibrationEnabled.value = enabled
    }

    fun firePulse(color: Color) {
        _oneshotChannel.trySend(color)
    }

    private fun playAudioFile(audioFile: Int) {
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer = MediaPlayer.create(context, audioFile)
        if (sharedPreferences.getBoolean(Consts.SETTINGS_AUDIO_ENABLED, true)) {
            mediaPlayer.setVolume(1f,1f)
        } else {
            mediaPlayer.setVolume(0f,0f)
        }
        mediaPlayer.start()
    }

    fun isPlaying(): Boolean {
        return try {
            mediaPlayer.isPlaying
        } catch(e: Exception) {
            Log.i("MediaPlayer", e.toString())
            false
        }
    }

    fun playWriteSound() {
        playAudioFile(R.raw.write)
    }

    fun playErrorSound() {
        playAudioFile(R.raw.error)
        vibrate()
    }

    fun playConnectSound() {
        if(!mediaPlayer.isPlaying) {
            playAudioFile(R.raw.connect)
            vibrate()
        }
    }

    fun playSuccessSound(
        interrupt: Boolean = false,
        callback: (() -> Unit)? = null
    ) {
        if(!mediaPlayer.isPlaying || interrupt) {
            playAudioFile(R.raw.success)
            callback?.invoke()
            vibrate()
        } else {
            mediaPlayer.setOnCompletionListener {
                playAudioFile(R.raw.success)
                callback?.invoke()
                vibrate()
            }
        }
    }

    fun success(
        interrupt: Boolean = false,
        callback: (() -> Unit)? = null
    ) {
        if(!mediaPlayer.isPlaying || interrupt) {
            playAudioFile(R.raw.success)
            callback?.invoke()
            vibrate()
            firePulse(Color(context.getColor(R.color.valid_green)))
        } else {
            mediaPlayer.setOnCompletionListener {
                playAudioFile(R.raw.success)
                callback?.invoke()
                vibrate()
                firePulse(Color(context.getColor(R.color.valid_green)))
            }
        }
    }

    fun error(
        interrupt: Boolean = false,
        callback: (() -> Unit)? = null
    ) {
        if(!mediaPlayer.isPlaying || interrupt) {
            playAudioFile(R.raw.error)
            callback?.invoke()
            vibrate()
            firePulse(Color(context.getColor(R.color.error_red)))
        } else {
            mediaPlayer.setOnCompletionListener {
                playAudioFile(R.raw.error)
                callback?.invoke()
                vibrate()
                firePulse(Color(context.getColor(R.color.error_red)))
            }
        }
    }

    private fun vibrate() {
        if (sharedPreferences.getBoolean(Consts.SETTINGS_VIBRATION_ENABLED, true)) {
            try {
                val vibrator = vibratorManager?.defaultVibrator
                vibrator?.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } catch (e: Exception) {
                Log.i("Vibration", e.toString())
            }
        }
    }

    fun showSnackbar(message: String) {
        _snackbarChannel.trySend(message)
    }
}