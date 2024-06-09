package com.example.cruddatabse_firebase.repository

import android.net.Uri
import com.example.cruddatabse_firebase.model.ProductModel

interface ProductRepository {
    fun uploadImage(imageUrl: Uri, callback:(Boolean, String?, String?) -> Unit)

    fun addProduct(productModel: ProductModel,callback:(Boolean,String?) -> Unit)
}