package com.example.cruddatabse_firebase.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.cruddatabse_firebase.model.ProductModel
import com.example.cruddatabse_firebase.repository.ProductRepository

class ProductViewModel(val repository: ProductRepository):ViewModel() {
    fun uploadImage(imageUrl: Uri, callback:(Boolean, String?, String?) -> Unit){
        repository.uploadImage(imageUrl,callback)
    }
    fun addProduct(productModel: ProductModel, callback:(Boolean, String?) -> Unit){
        repository.addProduct(productModel,callback)
    }

}