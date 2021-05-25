package com.tonon.a7minuteworkout

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sevenminuteworkout.SqliteOpenHelper
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        supportActionBar!!.hide()
        toolbar()

        btnFinish.setOnClickListener {
            finish()
        }

        addDateToDatabase()
    }


    private fun toolbar(){
        val toolbar = toolbar_exercise_activity

        toolbar.setNavigationIcon(getDrawable(R.drawable.back))
        toolbar.setTitle("7 Minutes Workout")
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    private fun addDateToDatabase(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        Log.e("DATE:", "" + dateTime)

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        val dbHandler = SqliteOpenHelper(this, null)
        dbHandler.addDate(date)
        Log.e("DATE: ", "Added")
    }
}