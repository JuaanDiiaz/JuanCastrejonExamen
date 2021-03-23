package com.example.juancastrejonexamen.Tools

class Constants {
    companion object{
        const val LOG_TAG = "UdelP"
        const val ID = "id"
        const val Position = "position"
        val PERMISSION_MICROPHONE = arrayOf<String>(android.Manifest.permission.RECORD_AUDIO)
        val PERMISSION_STORAGE = arrayOf<String>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val PERMISSIONS_LOCATION = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
}