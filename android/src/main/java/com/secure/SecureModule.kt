package com.secure

import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread

class SecureModule internal constructor(private val reactContext: ReactApplicationContext) :
  SecureSpec(reactContext) {

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  override fun canAuthenticate() {
    val biometricManager = BiometricManager.from(reactContext)
    when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
      BiometricManager.BIOMETRIC_SUCCESS -> {
        Log.d("MY_APP_TAG", "App can authenticate using biometrics")
      }
      BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
        Log.d("MY_APP_TAG", "Biometrics features are currently unavailable")
      }
      BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
        Log.d("MY_APP_TAG", "Biometrics features are currently not enrolled")
      }
    }
  }

  @ReactMethod
  override fun requireLocalAuth(options: ReadableMap, promise: Promise) {
    val options = options.toHashMap()

    val keys = arrayOf("title", "subtitle", "negativeButtonText")

    val containsAllKeys = keys.all { options.containsKey(it) }

    if (!containsAllKeys) {
      promise.reject("missing required value on options", "make sure to include all values")
    }

    val title = options["title"] as? String ?: ""
    val subtitle = options["subtitle"] as? String ?: ""
    val negativeButtonText = options["negativeButtonText"] as? String ?: ""

    val executor = ContextCompat.getMainExecutor(reactContext)

    val promptInfo: BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder()
      .setTitle(title)
      .setSubtitle(subtitle)
      .setNegativeButtonText(negativeButtonText)
      .build()

    val biometricPrompt = BiometricPrompt(currentActivity as FragmentActivity, executor,
      object : BiometricPrompt.AuthenticationCallback(){
      override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        super.onAuthenticationError(errorCode, errString)
        Log.d("MY_APP_TAG", "Authentication error")
      }

      override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        Log.d("MY_APP_TAG", "Authentication Success")
      }

      override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        Log.d("MY_APP_TAG", "Authentication FAILED")
      }
    })

    runOnUiThread {
      biometricPrompt.authenticate(promptInfo)
    }
  }

  companion object {
    const val NAME = "Secure"
  }
}
