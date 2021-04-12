package com.example.juancastrejonexamen.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.juancastrejonexamen.Contracts.SurveyContract
import com.example.juancastrejonexamen.Contracts.UserContract

class ConnectionDB(val context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_USERS)
        db?.execSQL(CREATE_TABLE_SURVEYS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE_USERS)
        db?.execSQL(DROP_TABLE_SURVEYS)
        onCreate(db)
    }
    fun openConnection(typeConnection:Int): SQLiteDatabase {
        return when(typeConnection){
            MODE_WRITE->{
                writableDatabase
            }
            MODE_READ->{
                readableDatabase
            }
            else->{
                readableDatabase
            }
        }
    }
    companion object{
        const val DATABASE_NAME="EXAM_DB"
        const val DATABASE_VERSION=1
        const val CREATE_TABLE_USERS = "CREATE TABLE ${UserContract.Entry.TABLE_NAME} " +
                "( ${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${UserContract.Entry.COLUMN_NAME_USER_EMAIL} VARCHAR(50), " +
                "${UserContract.Entry.COLUMN_NAME_PASSWORD} VARCHAR(50), " +
                "${UserContract.Entry.COLUMN_NAME_TEL} VARCHAR(50), " +
                "${UserContract.Entry.COLUMN_NAME_GENDER} INTEGER )"

        const val CREATE_TABLE_SURVEYS = "CREATE TABLE ${SurveyContract.Entry.TABLE_NAME} " +
                "( ${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${SurveyContract.Entry.COLUMN_NAME_ID_USER} INTEGER, " +
                "${SurveyContract.Entry.COLUMN_NAME_SURVEY_NAME} VARCHAR(50), " +
                "${SurveyContract.Entry.COLUMN_NAME_SURVEY_DATE} DATE, " +
                "${SurveyContract.Entry.COLUMN_NAME_INTERVIEWED_NAME} VARCHAR(50), " +
                "${SurveyContract.Entry.COLUMN_NAME_INTERVIEWED_LAST_NAME} VARCHAR(100), " +
                "${SurveyContract.Entry.COLUMN_NAME_GRAPHIC_TYPE} INTEGER, " +
                "${SurveyContract.Entry.COLUMN_NAME_STORES_STOCK} INTEGER, " +
                "${SurveyContract.Entry.COLUMN_NAME_PURCHASES} INTEGER, " +
                "${SurveyContract.Entry.COLUMN_NAME_SALES} INTEGER, " +
                "${SurveyContract.Entry.COLUMN_NAME_USER_TYPE} INTEGER, " +
                "${SurveyContract.Entry.COLUMN_NAME_DATE_PICK} DATE, " +
                "${SurveyContract.Entry.COLUMN_NAME_TIME_PICK} VARCHAR(50), " +
                "${SurveyContract.Entry.COLUMN_NAME_COMMENTS} VARCHAR(500), " +
                "FOREIGN KEY(${SurveyContract.Entry.COLUMN_NAME_ID_USER}) REFERENCES ${UserContract.Entry.TABLE_NAME}(${BaseColumns._ID}))"

        const val DROP_TABLE_USERS="DROP TABLE IF EXISTS ${UserContract.Entry.TABLE_NAME}"
        const val DROP_TABLE_SURVEYS="DROP TABLE IF EXISTS ${SurveyContract.Entry.TABLE_NAME}"
        const val MODE_WRITE=1
        const val MODE_READ=2
    }
}