package com.example.juancastrejonexamen.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import com.example.juancastrejonexamen.Contracts.SurveyContract
import com.example.juancastrejonexamen.Entity.EntitySurvey
import com.example.juancastrejonexamen.Tools.Constants

class SurveyDB(val context: Context) {
    val conectionDb=ConnectionDB(context)
    private lateinit var db: SQLiteDatabase

    fun add(survey: EntitySurvey):Long{
        db=conectionDb.openConnection(ConnectionDB.MODE_WRITE)
        val values = ContentValues().apply {
            put(SurveyContract.Entry.COLUMN_NAME_ID_USER,survey.id_user)
            put(SurveyContract.Entry.COLUMN_NAME_SURVEY_NAME,survey.pollName)
            put(SurveyContract.Entry.COLUMN_NAME_SURVEY_DATE,survey.polldate)
            put(SurveyContract.Entry.COLUMN_NAME_INTERVIEWED_NAME,survey.name)
            put(SurveyContract.Entry.COLUMN_NAME_INTERVIEWED_LAST_NAME,survey.lastName)
            put(SurveyContract.Entry.COLUMN_NAME_GRAPHIC_TYPE,survey.typeOfGraphic)
            put(SurveyContract.Entry.COLUMN_NAME_STORES_STOCK,survey.stockStore)
            put(SurveyContract.Entry.COLUMN_NAME_PURCHASES,survey.purchases)
            put(SurveyContract.Entry.COLUMN_NAME_SALES,survey.sales)
            put(SurveyContract.Entry.COLUMN_NAME_USER_TYPE,survey.userType)
            put(SurveyContract.Entry.COLUMN_NAME_DATE_PICK,survey.datePick)
            put(SurveyContract.Entry.COLUMN_NAME_TIME_PICK,survey.timePick)
            put(SurveyContract.Entry.COLUMN_NAME_COMMENTS,survey.comments)
        }
        return db.insert(SurveyContract.Entry.TABLE_NAME,null,values)
    }
    fun update(survey:EntitySurvey):Int{
        db=conectionDb.openConnection(ConnectionDB.MODE_WRITE)
        val values = ContentValues().apply {
            put(SurveyContract.Entry.COLUMN_NAME_ID_USER,survey.id_user)
            put(SurveyContract.Entry.COLUMN_NAME_SURVEY_NAME,survey.pollName)
            put(SurveyContract.Entry.COLUMN_NAME_SURVEY_DATE,survey.polldate)
            put(SurveyContract.Entry.COLUMN_NAME_INTERVIEWED_NAME,survey.name)
            put(SurveyContract.Entry.COLUMN_NAME_INTERVIEWED_LAST_NAME,survey.lastName)
            put(SurveyContract.Entry.COLUMN_NAME_GRAPHIC_TYPE,survey.typeOfGraphic)
            put(SurveyContract.Entry.COLUMN_NAME_STORES_STOCK,survey.stockStore)
            put(SurveyContract.Entry.COLUMN_NAME_PURCHASES,survey.purchases)
            put(SurveyContract.Entry.COLUMN_NAME_SALES,survey.sales)
            put(SurveyContract.Entry.COLUMN_NAME_USER_TYPE,survey.userType)
            put(SurveyContract.Entry.COLUMN_NAME_DATE_PICK,survey.datePick)
            put(SurveyContract.Entry.COLUMN_NAME_TIME_PICK,survey.timePick)
            put(SurveyContract.Entry.COLUMN_NAME_COMMENTS,survey.comments)
        }
        val where = "${BaseColumns._ID} =?"
        val arg = arrayOf(survey.id.toString())
        return db.update(SurveyContract.Entry.TABLE_NAME,values,where,arg)
    }
    fun delete(id:Int):Int{
        db=conectionDb.openConnection(ConnectionDB.MODE_WRITE)
        val where = "${BaseColumns._ID} =?"
        val arg = arrayOf(id.toString())
        return db.delete(SurveyContract.Entry.TABLE_NAME,where,arg)
    }
    fun getOne(id:Int):EntitySurvey{
        val survey=EntitySurvey()
        db=conectionDb.openConnection(ConnectionDB.MODE_READ)
        val projection = arrayOf(BaseColumns._ID,
            SurveyContract.Entry.COLUMN_NAME_ID_USER,
            SurveyContract.Entry.COLUMN_NAME_SURVEY_NAME,
            SurveyContract.Entry.COLUMN_NAME_SURVEY_DATE,
            SurveyContract.Entry.COLUMN_NAME_INTERVIEWED_NAME,
            SurveyContract.Entry.COLUMN_NAME_INTERVIEWED_LAST_NAME,
            SurveyContract.Entry.COLUMN_NAME_GRAPHIC_TYPE,
            SurveyContract.Entry.COLUMN_NAME_STORES_STOCK,
            SurveyContract.Entry.COLUMN_NAME_PURCHASES,
            SurveyContract.Entry.COLUMN_NAME_SALES,
            SurveyContract.Entry.COLUMN_NAME_USER_TYPE,
            SurveyContract.Entry.COLUMN_NAME_DATE_PICK,
            SurveyContract.Entry.COLUMN_NAME_TIME_PICK,
            SurveyContract.Entry.COLUMN_NAME_COMMENTS)
        val where = "${BaseColumns._ID} =?"
        val arg = arrayOf(id.toString())
        val cursor=db.query(SurveyContract.Entry.TABLE_NAME,projection,where,arg,null,null,null)
        if(cursor.moveToFirst()){

            survey.id=cursor.getInt(0)
            survey.id_user=cursor.getInt(1)
            survey.pollName=cursor.getString(2)
            survey.polldate=cursor.getString(3)
            survey.name=cursor.getString(4)
            survey.lastName=cursor.getString(5)
            survey.typeOfGraphic=cursor.getInt(6)
            survey.stockStore= cursor.getInt(7)==1
            survey.purchases= cursor.getInt(8)==1
            survey.sales= cursor.getInt(9)==1
            survey.userType=cursor.getInt(10)
            survey.datePick=cursor.getString(11)
            survey.timePick=cursor.getString(12)
            survey.comments=cursor.getString(13)
            Log.d(Constants.LOG_TAG,"$id | ${survey.id_user} | ${survey.polldate}" )
        }
        else
        {
            Log.d(Constants.LOG_TAG,"Sin valores")
        }
        return survey
    }
    fun getAllByUser(user:Int):ArrayList<EntitySurvey>{
        val list = ArrayList<EntitySurvey>()
        db=conectionDb.openConnection(ConnectionDB.MODE_READ)
        val projection = arrayOf(BaseColumns._ID,
            SurveyContract.Entry.COLUMN_NAME_ID_USER,
            SurveyContract.Entry.COLUMN_NAME_SURVEY_NAME,
            SurveyContract.Entry.COLUMN_NAME_SURVEY_DATE,
            SurveyContract.Entry.COLUMN_NAME_INTERVIEWED_NAME,
            SurveyContract.Entry.COLUMN_NAME_INTERVIEWED_LAST_NAME,
            SurveyContract.Entry.COLUMN_NAME_GRAPHIC_TYPE,
            SurveyContract.Entry.COLUMN_NAME_STORES_STOCK,
            SurveyContract.Entry.COLUMN_NAME_PURCHASES,
            SurveyContract.Entry.COLUMN_NAME_SALES,
            SurveyContract.Entry.COLUMN_NAME_USER_TYPE,
            SurveyContract.Entry.COLUMN_NAME_DATE_PICK,
            SurveyContract.Entry.COLUMN_NAME_TIME_PICK,
            SurveyContract.Entry.COLUMN_NAME_COMMENTS)
        val where = "${ SurveyContract.Entry.COLUMN_NAME_ID_USER} =?"
        val arg = arrayOf(user.toString())
        val cursor=db.query(SurveyContract.Entry.TABLE_NAME,projection,where,arg,null,null,null)
        if(cursor.moveToFirst()){
            var survey=EntitySurvey()
            survey.id=cursor.getInt(0)
            survey.id_user=cursor.getInt(1)
            survey.pollName=cursor.getString(2)
            survey.polldate=cursor.getString(3)
            survey.name=cursor.getString(4)
            survey.lastName=cursor.getString(5)
            survey.typeOfGraphic=cursor.getInt(6)
            survey.stockStore= cursor.getInt(7)==1
            survey.purchases= cursor.getInt(8)==1
            survey.sales= cursor.getInt(9)==1
            survey.userType=cursor.getInt(10)
            survey.datePick=cursor.getString(11)
            survey.timePick=cursor.getString(12)
            survey.comments=cursor.getString(13)
            Log.d(Constants.LOG_TAG,"${survey.id} | ${survey.id_user} | ${survey.pollName}")
            list.add(survey)
            while (cursor.moveToNext()) {
                survey=EntitySurvey()
                survey.id=cursor.getInt(0)
                survey.id_user=cursor.getInt(1)
                survey.pollName=cursor.getString(2)
                survey.polldate=cursor.getString(3)
                survey.name=cursor.getString(4)
                survey.lastName=cursor.getString(5)
                survey.typeOfGraphic=cursor.getInt(6)
                survey.stockStore= cursor.getInt(7)==1
                survey.purchases= cursor.getInt(8)==1
                survey.sales= cursor.getInt(9)==1
                survey.userType=cursor.getInt(10)
                survey.datePick=cursor.getString(11)
                survey.timePick=cursor.getString(12)
                survey.comments=cursor.getString(13)
                Log.d(Constants.LOG_TAG,"${survey.id} | ${survey.id_user} | ${survey.pollName}")
                list.add(survey)
            }
        }
        else
        {
            Log.d(Constants.LOG_TAG,"Sin valores")
        }
        return list
    }
}