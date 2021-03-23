package com.example.juancastrejonexamen.Data

import android.util.Log
import com.example.juancastrejonexamen.Entity.EntityPolls
import com.example.juancastrejonexamen.Entity.EntityUser

class ListPolls {
    fun add(poll:EntityPolls):Int{
        var answer = -1
        if(!existPollFilter(poll.pollName)){
            listPolls.add(poll)
            answer = listPolls.size -1
        }
        return answer
    }
    fun existPollFilter(name:String):Boolean{
        var answer: Boolean = false
        if (listPolls.filter{e -> e.pollName==name}.count()==1) {
            answer=true
        }
        return answer
    }
    fun edit(index:Int,poll:EntityPolls):Boolean{
        if(index >-1 && index < listPolls.size){
            listPolls[index]=poll
            return true
        }
        return false
    }

    fun getPollIndex(idUser:Int,position:Int):Int{
        var list = listPolls.filter { e -> e.id_user==idUser}
        for((index,item) in listPolls.withIndex()) {
            if(list[position].id_user==item.id_user && list[position].name==item.name){
                Log.d("mensajes","id usuario: ${list[position].id_user} encuesta nombre: ${list[position].name}")
                return index
            }
        }
        return -1
    }
    fun getPoll(index:Int):EntityPolls{
        return listPolls[index]
    }

    fun delete(index:Int):Boolean{
        if(index>-1 && index< listPolls.size){
            listPolls.removeAt(index)
            return true
        }
        return false
    }
    fun getListPollsArray(id:Int): ArrayList<EntityPolls>{
        val answerList= arrayListOf<EntityPolls>()
        for((index,item) in listPolls.filter { e -> e.id_user==id }.withIndex()){
            answerList.add(item)
        }
        return answerList
    }
    fun getStringArray(id:Int):Array<String>{
        val answerList= arrayListOf<String>()
        for((index,item) in listPolls.filter { e -> e.id_user==id }.withIndex()){
            answerList.add("${item.pollName} ${item.polldate}")
        }
        return answerList.toTypedArray()
    }
    companion object {
        private var listPolls= ArrayList<EntityPolls>()
    }
}