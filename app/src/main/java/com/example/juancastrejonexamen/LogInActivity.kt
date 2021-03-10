package com.example.juancastrejonexamen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.juancastrejonexamen.Data.ListUsers
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private val listUsers = ListUsers()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.txt_login)

        binding.buttonOk.setOnClickListener {
            if(isCorrect())
            {
                if(listUsers.logInCorrect(binding.editTextTextEmail.text.toString(),binding.editTextTextPassword.text.toString())){
                    var id_user = listUsers.getId(binding.editTextTextEmail.text.toString())
                    val intent = Intent(this@LogInActivity, HomeActivity::class.java).apply {
                        putExtra(Constants.ID,id_user)
                    }
                    startActivity(intent)
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