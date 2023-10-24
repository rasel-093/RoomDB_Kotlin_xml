package com.example.roomdb2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudentDao {
    @Query("SELECT * FROM student_table")
    fun getAll(): List<Student>

    @Query("SELECT * FROM student_table WHERE roll_no LIKE  :roll LIMIT 1 ")
    suspend fun findByRoll(roll: Int): Student

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("DELETE FROM STUDENT_TABLE")
    suspend fun deleteAll()
}