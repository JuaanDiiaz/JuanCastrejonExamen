package com.example.juancastrejonexamen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.juancastrejonexamen.Data.ListPolls
import com.example.juancastrejonexamen.Entity.EntityPolls
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.databinding.ActivitySurveyBinding

class SurveyActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySurveyBinding
    private val listPolls= ListPolls()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.txt_survey)

        val id:Int = intent.getIntExtra(Constants.ID,-1)
        if(id!=-1){
            binding.buttonOk.setOnClickListener {
                if(isAllCorrect()){
                    val poll=EntityPolls()
                    poll.id_user=id
                    poll.pollName=binding.editTextTextPollName.text.toString()
                    poll.name=binding.editTextTextPersonName.text.toString()
                    poll.lastName=binding.editTextTextLastnName.text.toString()
                    poll.typeOfGrafic=if(binding.rdbBarras.isChecked) 1 else if(binding.rdbPastel.isChecked) 2 else if(binding.rdbPuntos.isChecked) 3 else 0
                    poll.userType=binding.spnUserType.selectedItemPosition
                    poll.sales=binding.ckbVentas.isChecked
                    poll.stockStore=binding.ckbInventarios.isChecked
                    poll.purchases=binding.ckbCompras.isChecked
                    poll.comments=binding.editTextTextMultiLine.text.toString()
                    var request = listPolls.add(poll)
                    if(request != -1){
                        Toast.makeText(this@SurveyActivity,"Encuesta creada correctamente",Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this@SurveyActivity,"Error al crear la encuesta",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
    private fun isAllCorrect():Boolean{
        var correct = true
        var message = "Error, falta: "
        if(binding.editTextTextPollName.text.isEmpty()){
            correct = false
            message+=" *Nombre de encuesta"
        }
        if(binding.editTextTextPersonName.text.isEmpty()){
            correct = false
            message+=" *Nombre de encuestado"
        }
        if(binding.editTextTextLastnName.text.isEmpty()){
            correct = false
            message+=" *Apellido(s) de encuestado"
        }
        when(binding.rdgGraffics.checkedRadioButtonId){
            binding.rdbBarras.id->""
            binding.rdbPastel.id->""
            binding.rdbPuntos.id->""
            else->{
                correct = false
                message+=" *Seleccionar tipo de graficas"
            }
        }
        if(binding.spnUserType.selectedItemPosition==0){
            correct = false
            message+=" *Seleccionar tipo de usuario"
        }
        if(!correct){
            Toast.makeText(this@SurveyActivity,message,Toast.LENGTH_SHORT).show()
        }
        return correct
    }
}