@file:Suppress("unused")

package com.xenon.quicksettings

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Suppress("PrivatePropertyName")
class MainActivity : AppCompatActivity() {

    // Request Codes
    private val REQUEST_CODE_LOCATION = 1001
    private val REQUEST_CODE_CAMERA_FLASHLIGHT = 1002
    private val REQUEST_CODE_STORAGE = 1003
    private val REQUEST_CODE_PHONE_STATE = 1004
    private val REQUEST_CODE_BLUETOOTH = 1005
    private val REQUEST_CODE_NOTIFICATION_POLICY = 1006
    private val REQUEST_CODE_QUERY_ALL_PACKAGES = 1007
    private val REQUEST_CODE_CLOSE_SYSTEM_DIALOGS = 1008
    private val REQUEST_CODE_WIFI_STATE = 1009
    private val REQUEST_CODE_NETWORK_STATE = 1010
    private val REQUEST_CODE_SYNC_SETTINGS = 1011
    private val REQUEST_CODE_BOOT_COMPLETED = 1012
    private val REQUEST_CODE_ALARM = 1013
    private val REQUEST_CODE_INSTALL_SHORTCUT = 1014
    private val REQUEST_CODE_EXPAND_STATUS_BAR = 1015
    private val REQUEST_CODE_KEYBOARD_SHORTCUT = 1016
    private val REQUEST_CODE_CALL_PHONE = 1017
    private val REQUEST_CODE_INTERACT_ACROSS_USERS = 1018
    private val REQUEST_CODE_WRITE_SETTINGS = 1019
    private val REQUEST_CODE_WRITE_SECURE_SETTINGS = 1020
    private val REQUEST_CODE_POST_NOTIFICATIONS = 1021
    private val REQUEST_CODE_POPUP_BACKGROUND_WINDOW = 1022
    private val REQUEST_CODE_READ_MEDIA_AUDIO = 1023
    private val REQUEST_CODE_READ_MEDIA_IMAGES = 1024
    private val REQUEST_CODE_READ_MEDIA_VIDEO = 1025
    private val REQUEST_CODE_NOTIFICATION_ACCESS = 1026
    private val REQUEST_CODE_ACCESSIBILITY = 1027

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showBottomSheetButton = findViewById<Button>(R.id.showBottomSheetButton)
        showBottomSheetButton.setOnClickListener {
            showBottomSheet()
        }

        // Request all permissions
        requestAllPermissions()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_SETTINGS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_SETTINGS),
                REQUEST_CODE_WRITE_SETTINGS
            )
        }
    }
    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            // If the overlay permission is not granted, request it
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS)
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestAllPermissions() {
        val permissionsToRequest = arrayOf(
            Manifest.permission.QUERY_ALL_PACKAGES,
//            Manifest.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS,
            Manifest.permission.VIBRATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_SYNC_SETTINGS,
            Manifest.permission.WRITE_SYNC_SETTINGS,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.SET_ALARM,
            Manifest.permission.INSTALL_SHORTCUT,
            Manifest.permission.EXPAND_STATUS_BAR,
            Manifest.permission.CAMERA,
//            Manifest.permission.FLASHLIGHT,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.INTERACT_ACROSS_USERS_FULL,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.WRITE_SECURE_SETTINGS,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,

            )

        // Filter out already granted permissions
        val permissionsNeeded = permissionsToRequest.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsNeeded,
                REQUEST_CODE_LOCATION
            ) // You can use a single request code or separate ones
        } else {
            // All permissions are already granted
        }
        if (!Settings.System.canWrite(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            @Suppress("DEPRECATION")
            startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_SETTINGS), REQUEST_CODE_WRITE_SETTINGS)
        }
        checkOverlayPermission()

        // Request Accessibility Access
        val accessibilityIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        @Suppress("DEPRECATION")
        startActivityForResult(accessibilityIntent, REQUEST_CODE_ACCESSIBILITY)

        // Request Notification Access
        val notificationIntent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        @Suppress("DEPRECATION")
        startActivityForResult(notificationIntent, REQUEST_CODE_NOTIFICATION_ACCESS)

    }


    @SuppressLint("InflateParams")
    fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.XenonBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.quickpanel_layout, null)
        bottomSheetDialog.setContentView(view)

        val dateTextView = view.findViewById<TextView>(R.id.date)

        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd.MM.yy EE", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)

        dateTextView.text = formattedDate

        bottomSheetDialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                // ... (handle other permission results) ...
            }

            REQUEST_CODE_WRITE_SETTINGS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, you can now access and modify system settings
                    // (e.g., brightness)
                } else {
                    // Permission denied, handle accordingly (e.g., show a message)
                }
            }
            // ... (handle other request codes) ...
        }
    }

    object PermissionUtils {
        fun hasPermissions(context: Context, vararg permissions: String): Boolean {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
            return true
        }

        fun requestPermissions(activity: Activity, requestCode: Int, vararg permissions: String) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        }
    }
}