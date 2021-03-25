package com.example.juancastrejonexamen.Entity

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class EntityPolls (
    var id_user:Int,
    var pollName:String,
    var polldate:String,
    var name:String,
    var lastName:String,
    var typeOfGrafic:Int,
    var stockStore:Boolean,
    var purchases:Boolean,
    var sales:Boolean,
    var userType:Int,
    var datePick:String,
    var timePick:String,
    var comments:String){
    constructor():this(-1,
            "",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            "",
            "",
            0,
            false,
            false,
            false,
            0,
            "",
            "",
            "")
}