package com.example.cruddatabse_firebase.ui.activity

import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cruddatabse_firebase.R
import com.example.cruddatabse_firebase.databinding.ActivityLightSensorBinding

class LightSensorActivity : AppCompatActivity(),SensorEventListener {
    lateinit var sensor: Sensor
    lateinit var sensorManager: SensorManager
    lateinit var lightSensorBinding: ActivityLightSensorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightSensorBinding = ActivityLightSensorBinding.inflate(layoutInflater)
        setContentView(lightSensorBinding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        if(!checkSensor()){
            Toast.makeText(applicationContext,"Accelerometer not supported", Toast.LENGTH_LONG).show()
            return
        }else{
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)!!
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}