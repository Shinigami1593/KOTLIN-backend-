package com.example.cruddatabse_firebase.repository

import android.net.Uri
import com.example.cruddatabse_firebase.model.ProductModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class ProductRepositoryImpl:ProductRepository {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref : DatabaseReference = database.reference.child("products")
    var firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef : StorageReference = firebaseStorage.reference

    override fun uploadImage(imageUrl: Uri, callback: (Boolean, String?, String?) -> Unit) {
        val imageName = UUID.randomUUID().toString()
        var imageReference = storageRef.child("products").child(imageName)

        imageUrl?.let { url ->
            imageReference.putFile(url).addOnSuccessListener {
                imageReference.downloadUrl.addOnSuccessListener {downloadUrl->
                    var imageUrls = downloadUrl.toString()
                    callback(true,imageUrls,imageName)
                }
            }.addOnFailureListener {
                callback(false,"","")
            }
        }    }

    override fun addProduct(productModel: ProductModel, callback: (Boolean, String?) -> Unit) {
        var id = ref.push().key.toString() //creates random unique id for database entities
        productModel.id = id

        ref.child(id).setValue(productModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Your data added successfully")
            } else {
                callback(false,"Unable to add the data")
            }

        }
    }
}