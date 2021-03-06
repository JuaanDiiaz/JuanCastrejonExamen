package com.example.juancastrejonexamen

import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.Transliterator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.juancastrejonexamen.Data.ListPolls
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.Tools.PermissionAplication
import com.example.juancastrejonexamen.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private val permissions = PermissionAplication(this@HomeActivity)
    private lateinit var binding: ActivityHomeBinding
    private val listPolls= ListPolls()
    private var permissionsOK=true
    private var pos:Int=-1
    private val id_user:Long by lazy {
        intent.getLongExtra(Constants.ID,-1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.txt_home)
        permissionsOK=false
        if(id_user!=(-1).toLong()){
            if(!permissions.hasPermission(Constants.PERMISSIONS_LOCATION[0])){
                permissions.acceptPermission(Constants.PERMISSIONS_LOCATION,1)
            }else{
                permissionsOK=true
            }
            getList(id_user.toInt())
            binding.ltvpolls.setOnItemClickListener { parent, view, position, id ->
                permissionsOK=false
                pos=position
                if(!permissions.hasPermission(Constants.PERMISSION_MICROPHONE[0])){
                    permissions.acceptPermission(Constants.PERMISSION_MICROPHONE,2)
                }else{
                    permissionsOK=true
                }
                if(permissionsOK){
                    miDialogo(position).show()
                }
            }
        }
        else{
            finishAffinity()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_poll_form,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itmNewPoll->{
                if(id_user!=(-1).toLong()){
                    val intent = Intent(this@HomeActivity,SurveyActivity::class.java).apply {
                        putExtra(Constants.ID,id_user.toInt())
                    }
                    startActivity(intent)
                }
            }
            R.id.itmExit->finish()
            R.id.itmSeePoll->{
                if(id_user!=(-1).toLong()){
                    val intent = Intent(this@HomeActivity,RecyclerActivity::class.java).apply {
                        putExtra(Constants.ID,id_user.toInt())
                    }
                    startActivity(intent)
                }
            }
            R.id.itmSeeDB->{
                if(id_user!=(-1).toLong()){
                    val intent = Intent(this@HomeActivity,RecyclerDbActivity::class.java).apply {
                        putExtra(Constants.ID,id_user.toInt())
                    }
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onRestart() {
        super.onRestart()
        if(id_user!=(-1).toLong()){
            Log.d("mensajes","id usuario: $id_user")
            getList(id_user.toInt())
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1->{
                for(r in grantResults){
                    if(r!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this@HomeActivity,"Es necesario dar permiso de acceder a la localizaci??n",Toast.LENGTH_SHORT).show()
                        permissionsOK=false
                        finish()
                    }
                }
            }
            2->{
                for(r in grantResults){
                    if(r!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this@HomeActivity,"Es necesario dar permiso de acceder al microfono para continuar",Toast.LENGTH_SHORT).show()
                        permissionsOK=false
                        finish()
                    }else{
                        miDialogo(pos).show()
                    }
                }
            }
        }
    }
    private fun miDialogo(index:Int): AlertDialog {
        val miAlerta = AlertDialog.Builder(this@HomeActivity)
        Log.d("mensajes","id usuario: $index ")
        Log.d("mensajes","id usuario: $id_user - index lista: ${listPolls.getPollIndex(id_user.toInt(),index)}")
        miAlerta.setTitle("Mensaje del sistema")
        miAlerta.setMessage("??Que acci??n desea realizar con el estudiante?")
        miAlerta.setPositiveButton("Editar"){_,_ ->
            val intent = Intent(this@HomeActivity,EditActivity::class.java).apply {
                putExtra(Constants.ID,id_user)
                Log.d("mensajes","id usuario: $id_user ")
                putExtra(Constants.Position,listPolls.getPollIndex(id_user.toInt(),index))
            }
            startActivity(intent)
        }
        miAlerta.setNeutralButton("Visualizar"){_,_ ->
            val intent = Intent(this@HomeActivity,DetailActivity::class.java).apply {
                putExtra(Constants.ID,listPolls.getPollIndex(id_user.toInt(),index))
            }
            startActivity(intent)
        }
        miAlerta.setNegativeButton("Eliminar"){_,_ ->
            var request = listPolls.delete(listPolls.getPollIndex(id_user.toInt(),index))
            if(request){
                Toast.makeText(this@HomeActivity,"Registro eliminado",Toast.LENGTH_SHORT).show()
                if(id_user!=(-1).toLong()){
                    getList(id_user.toInt())
                }
            }
        }
        return miAlerta.create()
    }
    private fun getList(id:Int){
        val adapter = ArrayAdapter<String>(this@HomeActivity,android.R.layout.simple_list_item_1,listPolls.getStringArray(id))
        binding.ltvpolls.adapter = adapter
    }
}