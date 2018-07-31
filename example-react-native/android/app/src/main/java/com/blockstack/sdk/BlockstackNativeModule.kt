package com.blockstack.sdk

import android.support.annotation.Nullable
import android.util.Log
import com.facebook.react.bridge.*
import org.blockstack.android.sdk.BlockstackSession
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.ReactContext




class BlockstackNativeModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName() = "BlockstackNativeModule"

    override fun getConstants(): MutableMap<String, Any> {
        val constants = HashMap<String, Any>()
        return constants;
    }

    private lateinit var session: BlockstackSession

    @ReactMethod
    fun createSession(promise: Promise) {
        val activity = getReactApplicationContext().currentActivity
        if (activity is ConfigProvider) {
            activity.runOnUiThread {
                Log.d("BlockstackNativeModule", "create session")
                session = BlockstackSession(activity, activity.getConfig()) {
                    Log.d("BlockstackNativeModule", "created session")
                    val map = Arguments.createMap()
                    map.putBoolean("loaded", true)
                    promise.resolve(map)
                    currentSession = session
                }
            }
        } else {
            promise.reject(IllegalStateException("must be called from an Activity that implements ConfigProvider"))
        }
    }

    @ReactMethod
    fun signIn(promise: Promise) {
        if (session.loaded) {
            BlockstackNativeModule.currentSignInPromise = promise
            getReactApplicationContext().currentActivity!!.runOnUiThread {
                session.redirectUserToSignIn {
                    // never called
                }
            }
        }
    }

    @ReactMethod
    fun signUserOut(promise: Promise) {
        if (session.loaded) {
           getReactApplicationContext().currentActivity!!.runOnUiThread {
               session.signUserOut {
                   val map = Arguments.createMap()
                   map.putBoolean("signedOut", true)
                   promise.resolve(map)
               }
           }
        }
    }

    companion object {
        var currentSession: BlockstackSession? = null
        var currentSignInPromise: Promise? = null
    }
}

