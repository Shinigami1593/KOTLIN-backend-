package com.example.cruddatabse_firebase.ui.activity

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cruddatabse_firebase.R
import com.example.cruddatabse_firebase.databinding.ActivityAccelometerBinding

class AccelometerActivity : AppCompatActivity(),SensorEventListener{
    lateinit var accelometerActivity: ActivityAccelometerBinding
    lateinit var sensor:Sensor
    lateinit var sensorManager: SensorManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        accelometerActivity = ActivityAccelometerBinding.inflate(layoutInflater)
        setContentView(accelometerActivity.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager


        if(!checkSensor()){
            Toast.makeText(applicationContext,"Accelerometer not supported",Toast.LENGTH_LONG).show()
            return
        }else{
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun checkSensor():Boolean{
        var sensor = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)==null) {
            sensor = false
            return sensor
        }
        return sensor
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var values = event!!.values
        var xAxis = values[0]
        var yAxis = values[1]
        var zAxis = values[2]
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}