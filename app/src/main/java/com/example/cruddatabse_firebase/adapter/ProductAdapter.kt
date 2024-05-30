package com.example.cruddatabse_firebase.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cruddatabse_firebase.R
import com.example.cruddatabse_firebase.model.ProductModel

class ProductAdapter(var data:ArrayList<ProductModel>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var productName: TextView = view.findViewById(R.id.labelName)
        var productPrice: TextView = view.findViewById(R.id.labelPrice)
        var productDescription: TextView = view.findViewById(R.id.labelDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}