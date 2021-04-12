package com.example.juancastrejonexamen.Contracts

import android.provider.BaseColumns

object SurveyContract {

    object Entry: BaseColumns {
        const val TABLE_NAME = "CTL_SURVEYS"
        const val  COLUMN_NAME_ID ="Id_Survey"
        const val  COLUMN_NAME_ID_USER ="Id_Survey"
        const val  COLUMN_NAME_SURVEY_NAME ="Survey_Name"
        const val  COLUMN_NAME_SURVEY_DATE ="Survey_Date"
        const val  COLUMN_NAME_INTERVIEWED_NAME="Interviewed_Name"
        const val  COLUMN_NAME_INTERVIEWED_LAST_NAME="Interviewed_Name_Last_Name"
        const val  COLUMN_NAME_GRAPHIC_TYPE ="Graphic_Type"
        const val  COLUMN_NAME_STORES_STOCK ="Stores_Stock"
        const val  COLUMN_NAME_PURCHASES ="Purchases"
        const val  COLUMN_NAME_SALES ="Sales"
        const val  COLUMN_NAME_USER_TYPE ="User_Type"
        const val  COLUMN_NAME_DATE_PICK ="Date_Pick"
        const val  COLUMN_NAME_TIME_PICK ="Time_Pick"
        const val  COLUMN_NAME_COMMENTS ="Comments"
    }
}