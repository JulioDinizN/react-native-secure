package com.secure

import android.content.Intent
import android.provider.Settings
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
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread
import java.util.concurrent.Executor

class SecureModule internal constructor(context: ReactApplicationContext) :
  SecureSpec(context) {

  override fun getName(): String {
    return NAME
  }

  private var executor: Executor = ContextCompat.getMainExecutor(this.reactApplicationContext)
  private var biometricPrompt: BiometricPrompt = BiometricPrompt(currentActivity as FragmentActivity, executor, object : BiometricPrompt.AuthenticationCallback(){
      override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        super.onAuthenticationError(errorCode, errString)
        Log.d("MY_APP_TAG", "Authentication error")
      }

      override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        Log.d("MY_APP_TAG", "Authentication Sucesss")
      }

      override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        Log.d("MY_APP_TAG", "Authentication FAILED")
      }
    })
  private var promptInfo: BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder()
      .setTitle("Sample title")
      .setSubtitle("Sample Subtitle")
      .setNegativeButtonText("Sample negative button text")
      .build()

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  override fun multiply(a: Double, b: Double, promise: Promise) {
    promise.resolve(a * b)
  }

  @ReactMethod
  override fun canAuthenticate() {
    val biometricManager = BiometricManager.from(this.reactApplicationContext)
    when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
      BiometricManager.BIOMETRIC_SUCCESS -> {
        Log.d("MY_APP_TAG", "App can authenticate using biometrics")
      }
      BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
        Log.d("MY_APP_TAG", "Biometrics features are currently unavailable")
      }
      BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
          putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        }
      }
    }
  }

  @ReactMethod
  override fun requireLocalAuth() {
    Log.d("MY_APP_TAG", "Called func")

    runOnUiThread(Runnable(function = {
      biometricPrompt.authenticate(promptInfo)
    }))
  }



  companion object {
    const val NAME = "Secure"
  }
}
