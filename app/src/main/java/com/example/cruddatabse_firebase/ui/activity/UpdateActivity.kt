package com.example.cruddatabse_firebase.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cruddatabse_firebase.R
import com.example.cruddatabse_firebase.databinding.ActivityUpdateBinding
import com.example.cruddatabse_firebase.model.ProductModel
import com.example.cruddatabse_firebase.repository.ProductRepositoryImpl
import com.example.cruddatabse_firebase.utils.ImageUtils
import com.example.cruddatabse_firebase.viewmodel.ProductViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class UpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBinding
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref : DatabaseReference = database.reference.child("products")
    var id = ""
    var imageName = ""
    lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    var imageUri : Uri? = null
    lateinit var productViewModel: ProductViewModel
    lateinit var imageUtils: ImageUtils
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)


        imageUtils = ImageUtils(this)
        imageUtils.registerActivity {
            imageUri = it
            Picasso.get().load(it).into(binding.updateImage)
        }

        var product:ProductModel? = intent.getParcelableExtra("products")
        id = product?.id.toString()
        imageName = product?.imageName.toString()
        binding.uName.setText(product?.name)
        binding.uPrice.setText(product?.price.toString())
        binding.uDescription.setText(product?.description)

        binding.btnUpdate.setOnClickListener{
            uploadImage()
        }

        binding.updateImage.setOnClickListener{
            imageUtils.launchGallery(this@UpdateActivity)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun uploadImage(){
        imageUri?.let {
            productViewModel.uploadImage(imageName, it){
                success, imageUrl ->
                if(success){
                    updateProduct(imageUrl.toString())
                }else{

                }
            }
        }
    }
    fun updateProduct(url:String){
        var uName : String = binding.uName.text.toString()
        var uPrice : Int = binding.uPrice.text.toString().toInt()
        var uDesc : String = binding.uDescription.text.toString()

        var data = mutableMapOf<String,Any>()
        data["name"] = uName
        data["price"] = uPrice
        data["description"] = uDesc

        ref.child(id).updateChildren(data).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(applicationContext,"Data updated",
                    Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(applicationContext,it.exception?.message,
                    Toast.LENGTH_LONG).show()
            }
        }

    }

}