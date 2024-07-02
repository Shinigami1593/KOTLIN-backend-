package com.example.cruddatabse_firebase.repository

interface AuthRepo {
    fun login(username: String,password:String,callback:(Boolean,String)->Unit)
    fun register(username: String,password:String,callback:(Boolean,String)->Unit)
}