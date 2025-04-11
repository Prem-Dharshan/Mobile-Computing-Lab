package com.example.finals.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class Database(context: Context) : SQLiteOpenHelper(context, "FinalsDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("" +
                "CREATE TABLE Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, email TEXT, dob TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Users")
        onCreate(db)
    }

    fun addUser(name: String, email: String, dob: String): Long {
        return writableDatabase.use {
            val values = ContentValues().apply {
                put("name", name)
                put("email", email)
                put("dob", dob)
            }
            it.insert("Users", null, values)
        }
    }

    fun getUserById(userId: Long) = readableDatabase.use {
        it.rawQuery("SELECT * FROM Users WHERE id = ?", arrayOf(userId.toString())).use { cursor ->
            if (cursor.moveToFirst()) User(
                cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            )
            else null
        }
    }

    fun updateUser(user: User): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", user.name)
            put("email", user.email)
            put("dob", user.dob)
        }

        val rowsUpdated = db.update("users", values, "id=?", arrayOf(user.id.toString()))
        db.close()

        return rowsUpdated > 0
    }

    fun deleteUser(userId: Long): Boolean {
        val db = writableDatabase
        val rowsDeleted = db.delete("users", "id=?", arrayOf(userId.toString()))
        db.close()

        return rowsDeleted > 0
    }

    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users", null)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val dob = cursor.getString(cursor.getColumnIndexOrThrow("dob"))
            userList.add(User(id, name, email, dob))
        }
        cursor.close()
        return userList
    }
}


data class User(
    val id: Long,
    val name: String,
    val email: String,
    val dob: String
)
