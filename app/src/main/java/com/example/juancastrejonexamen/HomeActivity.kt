package com.example.juancastrejonexamen

import android.content.Intent
import android.icu.text.Transliterator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.juancastrejonexamen.Data.ListPolls
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val listPolls= ListPolls()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.txt_home)

        val id1:Int = intent.getIntExtra(Constants.ID,-1)
        if(id1!=-1){
            getList(id1)
            binding.ltvpolls.setOnItemClickListener { parent, view, position, id ->
                miDialogo(position).show()
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
                val id:Int = intent.getIntExtra(Constants.ID,-1)
                if(id!=-1){
                    val intent = Intent(this@HomeActivity,SurveyActivity::class.java).apply {
                        putExtra(Constants.ID,id)
                    }
                    startActivity(intent)
                }
            }
            R.id.itmExit->finish()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onRestart() {
        super.onRestart()
        val id:Int = intent.getIntExtra(Constants.ID,-1)
        if(id!=-1){
            getList(id)
        }
    }
    private fun miDialogo(index:Int): AlertDialog {
        val miAlerta = AlertDialog.Builder(this@HomeActivity)
        val id:Int = intent.getIntExtra(Constants.ID,-1)
        Toast.makeText(this@HomeActivity,"$index",Toast.LENGTH_SHORT).show()
        Toast.makeText(this@HomeActivity,"${listPolls.getPollIndex(id,index)}",Toast.LENGTH_SHORT).show()
        miAlerta.setTitle("Mensaje del sistema")
        miAlerta.setMessage("¿Que acción desea realizar con el estudiante?")
            .setPositiveButton("Editar"){_,_ ->
                val intent = Intent(this@HomeActivity,EditActivity::class.java).apply {
                    putExtra(Constants.ID,listPolls.getPollIndex(id,index))
                    putExtra(Constants.Position,id)
                }
                startActivity(intent)

        }
            .setNeutralButton("Visuallizar"){_,_ ->
                val intent = Intent(this@HomeActivity,DetailActivity::class.java).apply {
                    putExtra(Constants.ID,listPolls.getPollIndex(id,index))
                }
                startActivity(intent)
        }
        miAlerta.setNegativeButton("Eliminar"){_,_ ->
            var request = listPolls.delete(listPolls.getPollIndex(id,index))
            if(request){
                Toast.makeText(this@HomeActivity,"Registro eliminado",Toast.LENGTH_SHORT).show()
                val id:Int = intent.getIntExtra(Constants.ID,-1)
                if(id!=-1){
                    getList(id)
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