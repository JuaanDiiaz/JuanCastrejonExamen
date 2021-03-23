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
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.Tools.PermissionAplication
import com.example.juancastrejonexamen.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private val permissions = PermissionAplication(this@LogInActivity)
    private lateinit var binding: ActivityLogInBinding
    private val listPolls= ListPolls()
    private var permissionsOK=true
    private val listUsers = ListUsers()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.txt_login)
        permissionsOK=false
        binding.buttonOk.setOnClickListener {
            if(!permissions.hasPermission(Constants.PERMISSIONS_LOCATION[0])){
                permissions.acceptPermission(Constants.PERMISSIONS_LOCATION,1)
            }else{
                permissionsOK=true
            }
            if(isCorrect() && permissionsOK)
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
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1->{
                for(p in permissions){
                    Log.d(Constants.LOG_TAG,p)
                }
                for(r in grantResults){
                    if(r!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this@LogInActivity,"Es necesario permitir el acceso a la ubicación",Toast.LENGTH_SHORT).show()

                        finish()
                    }
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