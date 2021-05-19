package com.decagon.android.sq007.userModel

class UserModel {
    var uid:String? = null
    var email:String? = null
    private var acceptedList:HashMap<String, UserModel>? = null
    constructor(){}
    constructor(uid:String, email:String){
        this.uid = uid
        this.email = email
        acceptedList = HashMap()
    }
}