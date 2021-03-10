package com.example.juancastrejonexamen.Data

import com.example.juancastrejonexamen.Entity.EntityUser

class ListUsers{
    fun add(user:EntityUser):Int{
        var answer =-1
        if(!existUserFilter(user.userMail)){
            listUsers.add(user)
            answer = listUsers.size -1
        }
        return answer
    }
    private fun existUserFilter(user:String):Boolean{
        var answer: Boolean = false
        if (listUsers.filter{e -> e.userMail==user}.count()==1) {
            answer=true
        }
        return answer
    }
    fun logInCorrect(user:String, pass:String):Boolean{
        if ((listUsers.filter{e -> e.userMail==user && e.pass==pass}.count()==1)) {
            return true
        }
        return false
    }

    fun getSize():Int{
        return listUsers.size
    }

    fun getId(user:String):Int{
        if (listUsers.filter{e -> e.userMail==user}.count()==1) {
           return listUsers.filter { e-> e.userMail==user}.get(0).id_user
        }
        return -1
    }
    companion object {
        private var listUsers= arrayListOf<EntityUser>()
    }
}