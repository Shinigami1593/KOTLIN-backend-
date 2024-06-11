package com.example.cruddatabse_firebase.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruddatabse_firebase.R
import com.example.cruddatabse_firebase.adapter.ProductAdapter
import com.example.cruddatabse_firebase.databinding.ActivityDashBoardBinding
import com.example.cruddatabse_firebase.model.ProductModel
import com.example.cruddatabse_firebase.repository.ProductRepositoryImpl
import com.example.cruddatabse_firebase.viewmodel.ProductViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DashBoardActivity : AppCompatActivity() {
    lateinit var binding:ActivityDashBoardBinding

    lateinit var productAdapter: ProductAdapter

    lateinit var productViewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)
        productViewModel.fetchProduct()
        productAdapter = ProductAdapter(this@DashBoardActivity, ArrayList())

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@DashBoardActivity)
            adapter = productAdapter
        }

        productViewModel.loadingState.observe(this){loading ->
            if(loading){
                binding.homeLoad.visibility = View.VISIBLE
            }else{
                binding.homeLoad.visibility = View.GONE
            }
        }

//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                TODO("Not yet implemented")
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//               var id = productAdapter.getProductId(viewHolder.adapterPosition)
//               var imageName = productAdapter.getIName(viewHolder.adapterPosition)
//                ref.child(id).removeValue().addOnCompleteListener {
//                    if(it.isSuccessful){
//                        storageRef.child("products").child(imageName).delete()
//                        Toast.makeText(this@DashBoardActivity,"Delete Successful",Toast.LENGTH_LONG)
//                    }else{
//
//                    }
//                }
//            }
//
//        }).attachToRecyclerView(binding.recyclerView)


        binding.floatingActionButton.setOnClickListener{
            var intent = Intent(this@DashBoardActivity, AddProductActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}