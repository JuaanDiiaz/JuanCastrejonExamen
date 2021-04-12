package com.example.juancastrejonexamen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.juancastrejonexamen.Data.SurveyDB
import com.example.juancastrejonexamen.Entity.EntitySurvey
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.databinding.ActivityRecyclerDbBinding
import com.example.juancastrejonexamen.databinding.ActivityUpdateBinding
import java.util.*

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateBinding
    private lateinit var poll : EntitySurvey
    private val id_survey:Int by lazy {
        intent.getIntExtra(Constants.ID,-1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.txt_recycler)
        if(id_survey != -1){
            loadSurvey()
            binding.buttonOk.setOnClickListener {
                if(isAllCorrect()){
                    saveData()
                }
            }
            binding.editTextDatePicker.setOnClickListener {
                val myCalendar= Calendar.getInstance()
                val year= myCalendar.get(Calendar.YEAR)
                val month= myCalendar.get(Calendar.MONTH)
                val day= myCalendar.get(Calendar.DAY_OF_MONTH)
                val dpd = DatePickerDialog(this@UpdateActivity, DatePickerDialog.OnDateSetListener { view, y, m, d ->
                    binding.editTextDatePicker.setText("${twoDigits(d)} / ${twoDigits(m+1)} / $y")
                },year,month,day)
                dpd.show()
            }
            binding.editTextTimePicker.setOnClickListener {
                val myCalendar= Calendar.getInstance()
                val h = myCalendar.get(Calendar.HOUR_OF_DAY)
                val m = myCalendar.get(Calendar.MINUTE)
                val tpd = TimePickerDialog(this@UpdateActivity, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    binding.editTextTimePicker.setText("${twoDigits(hourOfDay)}:${twoDigits(minute)}")
                },h,m,true)
                tpd.show()
            }
        }
    }
    fun loadSurvey(){
        poll = SurveyDB(this@UpdateActivity).getOne(id_survey)
        binding.editTextTextPollName.setText(poll.pollName)
        binding.editTextTextPersonName.setText(poll.name)
        binding.editTextTextLastnName.setText(poll.lastName)
        binding.editTextTextMultiLine.setText(poll.comments)
        when(poll.typeOfGraphic){
            1->{
                binding.rdbBarras.isChecked=true
            }
            2->{
                binding.rdbPastel.isChecked=true
            }
            3->{
                binding.rdbPuntos.isChecked=true
            }
        }
        binding.ckbCompras.isChecked=poll.purchases
        binding.ckbInventarios.isChecked=poll.stockStore
        binding.ckbVentas.isChecked=poll.sales
        binding.spnUserType.setSelection(poll.userType)
        binding.editTextDatePicker.setText(poll.datePick)
        binding.editTextTimePicker.setText(poll.timePick)
    }
    private fun saveData(){
        val poll2= EntitySurvey()
        poll2.id_user=poll.id_user
        poll2.id=poll.id
        poll2.pollName=binding.editTextTextPollName.text.toString()
        poll2.name=binding.editTextTextPersonName.text.toString()
        poll2.lastName=binding.editTextTextLastnName.text.toString()
        poll2.typeOfGraphic=if(binding.rdbBarras.isChecked) 1 else if(binding.rdbPastel.isChecked) 2 else if(binding.rdbPuntos.isChecked) 3 else 0
        poll2.userType=binding.spnUserType.selectedItemPosition
        poll2.sales=binding.ckbVentas.isChecked
        poll2.stockStore=binding.ckbInventarios.isChecked
        poll2.purchases=binding.ckbCompras.isChecked
        poll2.comments=binding.editTextTextMultiLine.text.toString()
        poll2.datePick=binding.editTextDatePicker.text.toString()
        poll2.timePick=binding.editTextTimePicker.text.toString()
        Log.d("mensajes","${poll2.id_user} ")
        val request = SurveyDB(this@UpdateActivity).update(poll2)
        if(request>0){
            Toast.makeText(this@UpdateActivity,"Encuesta editada correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this@UpdateActivity,"Error al editar la encuesta", Toast.LENGTH_SHORT).show()
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
        if(binding.editTextDatePicker.text.isEmpty()){
            correct = false
            message+=" *Seleccionar fecha"
        }
        if(binding.editTextTimePicker.text.isEmpty()){
            correct = false
            message+=" *Seleccionar hora"
        }
        if(!correct){
            Toast.makeText(this@UpdateActivity,message, Toast.LENGTH_SHORT).show()
        }
        return correct
    }
    fun twoDigits(number:Int):String{
        return if(number<=9)"0$number" else number.toString()
    }
}