package com.example.urhp

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
@Dao
interface PersonDao {
    @Query("SELECT * FROM TABLE_PERSON")
    fun getAllLD(): LiveData<List<Person>>

    @Query("SELECT * FROM TABLE_PERSON")
    fun getAll(): List<Person>

    @Insert
    fun insert(person: Person)

    @Insert
    fun insertAll(persons: List<Person>)

    @Delete
    fun delete(person : Person)

    @Update
    fun update(person: Person)
}