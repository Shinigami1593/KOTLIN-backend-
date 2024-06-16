package com.example.cruddatabse_firebase.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cruddatabse_firebase.R
import com.example.cruddatabse_firebase.databinding.ActivitySensorDashBoardAcitivityBinding

class SensorDashBoardAcitivity : AppCompatActivity() {
    lateinit var sensorDashboardActivity:ActivitySensorDashBoardAcitivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sensorDashboardActivity = ActivitySensorDashBoardAcitivityBinding.inflate(layoutInflater)
        setContentView(sensorDashboardActivity.root)

        sensorDashboardActivity.sensorBtn.setOnClickListener{
            var intent = Intent(this@SensorDashBoardAcitivity,SensorListActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}