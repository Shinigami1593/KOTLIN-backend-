package com.example.cruddatabse_firebase.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
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
    var _productList = MutableLiveData<List<ProductModel>?>()
    var productList = MutableLiveData<List<ProductModel>?>()
        get() = _productList
    var _loadingState = MutableLiveData<Boolean>()
    var loadingState = MutableLiveData<Boolean>()
        get() = _loadingState
    fun fetchProduct(){
        _loadingState.value = true
        repository.getAllProduct{productList,success,message ->
            if (productList!=null){
                _productList.value = productList
                _loadingState.value = false
            }
        }
    }

}