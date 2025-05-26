package com.itesthida.techserviceapp.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseManager(context: Context): SQLiteOpenHelper(context, "", null, 1) {
    // SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        // Al cambiar la versión, se ejecutará el onUpgrade, con ello eliminará las tablas que
        // existen en la base de datos y las creará nuevamente
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "TechnicalService.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}
