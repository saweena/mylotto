package com.example.thailotto2021_1.other

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions


object QrCodeUtility {
    fun hasCameraPermission(context: Context) =
        EasyPermissions.hasPermissions(context,Manifest.permission.CAMERA)
}