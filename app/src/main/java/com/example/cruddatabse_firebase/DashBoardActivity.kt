package com.example.cruddatabse_firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruddatabse_firebase.adapter.ProductAdapter
import com.example.cruddatabse_firebase.databinding.ActivityDashBoardBinding
import com.example.cruddatabse_firebase.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DashBoardActivity : AppCompatActivity() {
    lateinit var binding:ActivityDashBoardBinding
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref : DatabaseReference = database.reference.child("products")

    var firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef : StorageReference = firebaseStorage.reference

    lateinit var productAdapter: ProductAdapter
    var productList = ArrayList<ProductModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productAdapter = ProductAdapter(this@DashBoardActivity, productList)
        binding.floatingActionButton.setOnClickListener{
            var intent = Intent(this@DashBoardActivity,AddProductActivity::class.java)
            startActivity(intent)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               var id = productAdapter.getProductId(viewHolder.adapterPosition)
               var imageName = productAdapter.getIName(viewHolder.adapterPosition)
                ref.child(id).removeValue().addOnCompleteListener {
                    if(it.isSuccessful){
                        storageRef.child("products").child(imageName).delete()
                        Toast.makeText(this@DashBoardActivity,"Delete Successful",Toast.LENGTH_LONG)
                    }else{

                    }
                }
            }

        }).attachToRecyclerView(binding.recyclerView)

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

                binding.recyclerView.layoutManager = LinearLayoutManager(this@DashBoardActivity)
                binding.recyclerView.adapter = productAdapter

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