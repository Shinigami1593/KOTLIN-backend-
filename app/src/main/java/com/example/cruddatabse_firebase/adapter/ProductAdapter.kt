package com.example.cruddatabse_firebase.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cruddatabse_firebase.R
import com.example.cruddatabse_firebase.UpdateActivity
import com.example.cruddatabse_firebase.model.ProductModel

class ProductAdapter(var context: Context , var data:ArrayList<ProductModel>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var productName: TextView = view.findViewById(R.id.labelName)
        var productPrice: TextView = view.findViewById(R.id.labelPrice)
        var productDescription: TextView = view.findViewById(R.id.labelDescription)
        var btnEdit:TextView = view.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.sample_product,parent,false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text = data[position].name
        holder.productPrice.text = data[position].price.toString()
        holder.productDescription.text = data[position].description
        holder.btnEdit.setOnClickListener {
            var intent = Intent(context,UpdateActivity::class.java)
            intent.putExtra("products", data[position])
            context.startActivity(intent)
        }
    }
}