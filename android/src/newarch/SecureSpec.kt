package com.secure

import com.facebook.react.bridge.ReactApplicationContext

abstract class SecureSpec internal constructor(context: ReactApplicationContext) :
  NativeSecureSpec(context) {
}
