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

    @Query("DELETE FROM student_table WHERE roll_no = :roll")
    suspend fun delete(roll: Int)

    @Query("DELETE FROM STUDENT_TABLE")
    suspend fun deleteAll()

    @Query("UPDATE student_table SET first_name=:firstName, last_name =:lastName WHERE roll_no =:rollNo")
    fun update(firstName: String, lastName: String,rollNo: Int)

    @Query("SELECT COUNT(*) FROM student_table WHERE roll_no = :roll ")
    fun getCount(roll: Int): Int

    @Query("SELECT * FROM student_table WHERE roll_no LIKE  :roll LIMIT 1 ")
    suspend fun findByRoll2(roll: Int): Student?
}