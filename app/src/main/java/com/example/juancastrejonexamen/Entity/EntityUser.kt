package com.example.juancastrejonexamen.Entity


data class EntityUser (
    var id_user:Int,
    var userMail:String,
    var pass:String,
    var tel:String,
    var gender:Int){
    constructor():this(-1,"","","",0)
}