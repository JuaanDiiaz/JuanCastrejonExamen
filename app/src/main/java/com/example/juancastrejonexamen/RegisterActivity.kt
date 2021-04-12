package com.example.juancastrejonexamen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.juancastrejonexamen.Data.ListUsers
import com.example.juancastrejonexamen.Data.UserDB
import com.example.juancastrejonexamen.Entity.EntityUser
import com.example.juancastrejonexamen.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val listUsers = ListUsers()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.txt_register)

        binding.buttonOk.setOnClickListener {
            if(isAllCorrect()){
                val user = EntityUser();
                user.id_user=listUsers.getSize()+1
                user.userMail=binding.editTextTextEmail.text.toString().trim()
                user.pass= binding.editTextTextPassword.text.toString().trim()
                user.tel=binding.editTextPhone.text.toString().trim()
                user.gender=binding.spinnerGender.selectedItemPosition
                val result = UserDB(this@RegisterActivity).add(user)
                if(result != (-1).toLong()){
                    Toast.makeText(this@RegisterActivity,"Usuario creado correctamente",Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@RegisterActivity,"Error al crear el usuario, correo existente",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun isAllCorrect():Boolean{
        var correct = true
        var message = "Error, falta: "
        if(binding.editTextTextEmail.text.isEmpty()){
            correct = false
            message+=" *Correo"
        }
        if(binding.editTextTextPassword.text.isEmpty()){
            correct = false
            message+=" *Contraseña"
        }
        if(binding.editTextPhone.text.isEmpty()){
            correct = false
            message+=" *Telefono"
        }
        if(binding.spinnerGender.selectedItemPosition==0){
            correct = false
            message+=" *Genero"
        }
        if(!correct){
            Toast.makeText(this@RegisterActivity,message,Toast.LENGTH_SHORT).show()
        }
        return correct
    }
}