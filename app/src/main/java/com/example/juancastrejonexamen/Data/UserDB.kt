package com.example.juancastrejonexamen.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import com.example.juancastrejonexamen.Contracts.UserContract
import com.example.juancastrejonexamen.Entity.EntityUser
import com.example.juancastrejonexamen.Tools.Constants

class UserDB(val context: Context) {
    val conectionDb=ConnectionDB(context)
    private lateinit var db: SQLiteDatabase
    fun add(user:EntityUser):Long{
        db=conectionDb.openConnection(ConnectionDB.MODE_WRITE)
        val values = ContentValues().apply {
            put(UserContract.Entry.COLUMN_NAME_USER_EMAIL,user.userMail)
            put(UserContract.Entry.COLUMN_NAME_PASSWORD,user.pass)
            put(UserContract.Entry.COLUMN_NAME_GENDER,user.gender)
            put(UserContract.Entry.COLUMN_NAME_TEL,user.tel)
        }
        return db.insert(UserContract.Entry.TABLE_NAME,null,values)
    }
    fun getOne(email:String,pass:String):Long{
        db=conectionDb.openConnection(ConnectionDB.MODE_READ)
        val projection = arrayOf(BaseColumns._ID,
                UserContract.Entry.COLUMN_NAME_USER_EMAIL,
                UserContract.Entry.COLUMN_NAME_PASSWORD,
                UserContract.Entry.COLUMN_NAME_GENDER,
                UserContract.Entry.COLUMN_NAME_TEL)
        val where = "${UserContract.Entry.COLUMN_NAME_USER_EMAIL}=? AND ${UserContract.Entry.COLUMN_NAME_PASSWORD}=?"
        val arg = arrayOf(email,pass)
        val cursor=db.query(UserContract.Entry.TABLE_NAME,projection,where,arg,null,null,null)
        if(cursor.moveToFirst()){
            val id:Long=cursor.getLong(0)
            val email2=cursor.getString(1)
            val pass2=cursor.getString(2)
            val tel=cursor.getInt(3)
            val gender=cursor.getString(4)
            Log.d(Constants.LOG_TAG,"$id | $email2 | $pass2 | $tel | $gender" )
            return id
        }
        else
        {
            Log.d(Constants.LOG_TAG,"Sin valores")
            return -1
        }
    }
    fun getAll(){
        db=conectionDb.openConnection(ConnectionDB.MODE_READ)
        val projection = arrayOf(BaseColumns._ID,
                UserContract.Entry.COLUMN_NAME_USER_EMAIL,
                UserContract.Entry.COLUMN_NAME_PASSWORD,
                UserContract.Entry.COLUMN_NAME_GENDER,
                UserContract.Entry.COLUMN_NAME_TEL)
        val sortOrder = "${UserContract.Entry.COLUMN_NAME_USER_EMAIL} DESC"
        val cursor=db.query(UserContract.Entry.TABLE_NAME,projection,null,null,null,null,sortOrder)
        if(cursor.moveToFirst()){
            do{
                val id:Long=cursor.getLong(0)
                val email=cursor.getString(1)
                val pass=cursor.getString(2)
                val gender=cursor.getInt(3)
                val tel=cursor.getString(4)
                Log.d(Constants.LOG_TAG,"$id | $email | $pass | $gender | $tel" )
            }while (cursor.moveToNext())
        }
        else
        {
            Log.d(Constants.LOG_TAG,"Sin valores")
        }
    }
}