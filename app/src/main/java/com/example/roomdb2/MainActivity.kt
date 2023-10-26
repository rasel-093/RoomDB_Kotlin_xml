package com.example.roomdb2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.roomdb2.data.AppDB
import com.example.roomdb2.data.Student
import com.example.roomdb2.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appDB: AppDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDB = AppDB.getDB(this)
        binding.btnWriteData.setOnClickListener{
            writeData()
        }
        binding.readRollBtn.setOnClickListener{
            readData()
        }
        binding.deleteRollBtn.setOnClickListener{
            deleteRoll()
        }
        binding.updateBtn.setOnClickListener{
            update()
        }
    }

    private fun update() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val rollNo = binding.etRollNo.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                appDB.studentDao().update(
                    firstName = firstName,
                    lastName = lastName,
                    rollNo = rollNo.toInt()
                )
            }
            Toast.makeText(this@MainActivity,"Data updated", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this@MainActivity,"Fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteRoll() {
        val rollNo = binding.etRollNoRead.text.toString()
        if(rollNo.isNotEmpty()){
            GlobalScope.launch {
                val count: Int = appDB.studentDao().getCount(rollNo.toInt()) //Return count of given rollNo
                Log.d("count",count.toString())
                if(count !=0 ){
                    appDB.studentDao().delete(rollNo.toInt())
                    //binding.etRollNoRead.text.clear()
                    showToast("Student Deleted")
                }
                else{
                    showToast("Not found")
                    Log.d("Else","else")
                }
            }
            binding.etRollNoRead.text.clear()
        }
    }


    private fun readData(){
        val rollNo = binding.etRollNoRead.text.toString()
        if(rollNo.isNotEmpty()){
            GlobalScope.launch {
                val count: Int = appDB.studentDao().getCount(rollNo.toInt()) //Return count of given rollNo
                Log.d("count",count.toString())
                if(count !=0 ){
                    var student: Student
                    student = appDB.studentDao().findByRoll(rollNo.toInt())
                    displayData(student)
                }
                else{
                    showToast("Not found")
                    Log.d("Else","else")
                }
            }
        }
    }

    private fun showToast(s: String) {
        Handler(Looper.getMainLooper()).post{
            Toast.makeText(this@MainActivity,s,Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun displayData(student: Student) {
        withContext(Dispatchers.Main){
            binding.tvFirstName.text = student.firstName
            binding.tvLastName.text = student.lastName
            binding.tvRollNo.text = student.rollNo.toString()
        }
    }

    private fun writeData(){
         val firstName = binding.etFirstName.text.toString()
         val lastName = binding.etLastName.text.toString()
        val rollNo = binding.etRollNo.text.toString()

        if(firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()){
            val student = Student(
                id = null,
                firstName = firstName,
                lastName = lastName,
                rollNo = rollNo.toInt()
            )
            GlobalScope.launch(Dispatchers.IO) {
                appDB.studentDao().insert(student)
            }
            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etRollNo.text.clear()
            Toast.makeText(this@MainActivity,"Student Added",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this@MainActivity,"Fill out all fields",Toast.LENGTH_SHORT).show()
        }
    }
}