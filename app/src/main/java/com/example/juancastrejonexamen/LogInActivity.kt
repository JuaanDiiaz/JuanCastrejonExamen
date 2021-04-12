package com.example.juancastrejonexamen

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.juancastrejonexamen.Data.ListPolls
import com.example.juancastrejonexamen.Data.ListUsers
import com.example.juancastrejonexamen.Data.UserDB
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.Tools.PermissionAplication
import com.example.juancastrejonexamen.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private val permissions = PermissionAplication(this@LogInActivity)
    private lateinit var binding: ActivityLogInBinding
    private var permissionsOK=true
    private val listUsers = ListUsers()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        UserDB(this@LogInActivity).getAll()
        supportActionBar?.setTitle(R.string.txt_login)
        binding.buttonOk.setOnClickListener {
            if(isCorrect() && permissionsOK)
            {
                val email=binding.editTextTextEmail.text.toString().trim()
                val pass=binding.editTextTextPassword.text.toString().trim()
                val id_user= UserDB(this@LogInActivity).getOne(email,pass)
                if(id_user != (-1).toLong()){
                    val intent = Intent(this@LogInActivity, HomeActivity::class.java).apply {
                        putExtra(Constants.ID,id_user)
                    }
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this@LogInActivity,"Usuario o contraseña invalido",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun isCorrect():Boolean{
        var correct = true
        var message = "Error falta: "
        if(binding.editTextTextEmail.text.isEmpty()) {
            correct = false
            message += " correo "
        }
        if(binding.editTextTextPassword.text.isEmpty()){
            correct = false
            message += " contraseña "
        }
        if(!correct){
            Toast.makeText(this@LogInActivity,message,Toast.LENGTH_SHORT).show()
        }
        return correct
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itmNewUser->{
                val intent = Intent(this@LogInActivity,
                    RegisterActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}