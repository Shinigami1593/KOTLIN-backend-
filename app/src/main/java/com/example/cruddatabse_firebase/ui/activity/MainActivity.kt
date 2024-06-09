package com.example.cruddatabse_firebase.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cruddatabse_firebase.R
import com.example.cruddatabse_firebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var auth:FirebaseAuth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener{
            var email : String = binding.Email.text.toString()
            var pass : String = binding.Password.text.toString()

            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(applicationContext, "Registration successful",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext,it.exception?.message,Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.passforgot.setOnClickListener{
            var intent = Intent(this@MainActivity, ResetActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener{
            var email : String = binding.Email.text.toString()
            var pass : String = binding.Password.text.toString()

            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(applicationContext, "Login successful",Toast.LENGTH_LONG).show()
                    var intent = Intent(this@MainActivity, DashBoardActivity::class.java)
                    startActivity(intent)
                    //navigate to dashboaard
                }else{
                    Toast.makeText(applicationContext,it.exception?.message,Toast.LENGTH_LONG).show()
                }
            }
        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}