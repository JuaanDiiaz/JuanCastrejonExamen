package com.example.juancastrejonexamen.Contracts

import android.provider.BaseColumns

object UserContract {
    object Entry: BaseColumns {
        const val TABLE_NAME = "CTL_USERS"
        const val  COLUMN_NAME_ID ="Id_User"
        const val  COLUMN_NAME_USER_EMAIL ="User_Email"
        const val  COLUMN_NAME_PASSWORD ="Password"
        const val  COLUMN_NAME_TEL ="Tel"
        const val  COLUMN_NAME_GENDER ="gender"
    }
}