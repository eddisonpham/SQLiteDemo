package com.example.sqlitedemo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper: SQLiteOpenHelper{
    val COLUMN_ID = "ID"
    val CUSTOMER_TABLE = "CUSTOMER_TABLE"
    val CUSTOMER_NAME = "CUSTOMER_NAME"
    val CUSTOMER_AGE = "CUSTOMER_AGE"
    val ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER"
    constructor(context: Context)
            :super(context, "customer.db", null, 1){

    }
    //This is called the first time a database is accessed. There should be code in here to create a new database.
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $CUSTOMER_TABLE" +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$CUSTOMER_NAME TEXT, " +
                "$CUSTOMER_AGE INT," +
                "$ACTIVE_CUSTOMER BOOL)"
        db?.execSQL(query)
    }

    //This is called if the database version number changes.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addOne(customerModel: CustomerModel): Boolean{
        val db:SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(CUSTOMER_NAME, customerModel.name)
        cv.put(CUSTOMER_AGE, customerModel.age)
        cv.put(ACTIVE_CUSTOMER, customerModel.isActive)

        val insert: Long = db.insert(CUSTOMER_TABLE, null, cv)
        if (insert.equals(-1)){
            return false
        }
        return true
    }

    fun DeleteOne(customerModel: CustomerModel):Boolean{
        val db = this.writableDatabase
        val query = "DELETE FROM $CUSTOMER_TABLE WHERE $COLUMN_ID = ${customerModel.id}"
        val cursor = db.rawQuery(query,null)
        return cursor.moveToFirst()
    }

    fun getEveryone(): ArrayList<CustomerModel>{
        var returnlist: ArrayList<CustomerModel> = ArrayList()
        val queryString = "SELECT * FROM $CUSTOMER_TABLE"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(queryString, null)
        if(cursor.moveToFirst()){
            do{
                val customerID = cursor.getInt(0)
                val customerName = cursor.getString(1)
                val customerAge = cursor.getInt(2)
                val customerActive = cursor.getInt(3)==1
                val newCustomer = CustomerModel(customerID, customerName, customerAge, customerActive)
                returnlist.add(newCustomer)
            }while(cursor.moveToNext())
        }else{

        }
        cursor.close()
        db.close()
        return returnlist
    }
}