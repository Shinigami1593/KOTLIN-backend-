package com.example.cruddatabse_firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cruddatabse_firebase.adapter.ProductAdapter
import com.example.cruddatabse_firebase.databinding.ActivityDashBoardBinding
import com.example.cruddatabse_firebase.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashBoardActivity : AppCompatActivity() {
    lateinit var binding:ActivityDashBoardBinding
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref : DatabaseReference = database.reference.child("products")

    var productList = ArrayList<ProductModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener{
            var intent = Intent(this@DashBoardActivity,AddProductActivity::class.java)
            startActivity(intent)
        }

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for(eachData in snapshot.children){
                    var product = eachData.getValue(ProductModel::class.java)
//                        Log.d("data from firebase", product?.name.toString())
                    if(product!=null){
                        Log.d("data from firebase", product.name)
                        Log.d("data from firebase", product.description)
                        Log.d("data from firebase", product.price.toString())
                        Log.d("data from firebase", product.id)

                        productList.add(product)
                    }

                }
                var adapter = ProductAdapter(productList)
                binding.recyclerView.layoutManager = LinearLayoutManager(this@DashBoardActivity)
                binding.recyclerView.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}