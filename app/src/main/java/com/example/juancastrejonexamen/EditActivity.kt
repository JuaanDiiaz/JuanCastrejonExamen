package com.example.juancastrejonexamen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.juancastrejonexamen.Data.ListPolls
import com.example.juancastrejonexamen.Entity.EntityPolls
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.databinding.ActivityEditBinding
import com.example.juancastrejonexamen.databinding.ActivityHomeBinding
import java.util.*
import kotlin.properties.Delegates

class EditActivity : AppCompatActivity() {
    private val position:Int by lazy {
        intent.getIntExtra(Constants.Position,-1)
    }
    val id:Int by lazy {
        intent.getIntExtra(Constants.ID,-1)
    }
    private lateinit var binding: ActivityEditBinding
    private val listPolls= ListPolls()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.txt_edit)

        Log.d("mensajes","id usuario: $id - index lista: $position")
        if(position!=-1 && id!=-1){
            uploadData(position)
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
                val dpd = DatePickerDialog(this@EditActivity, DatePickerDialog.OnDateSetListener { view, y, m, d ->
                    binding.editTextDatePicker.setText("${twoDigits(d)} / ${twoDigits(m+1)} / $y")
                },year,month,day)
                dpd.show()
            }
            binding.editTextTimePicker.setOnClickListener {
                val myCalendar=Calendar.getInstance()
                val h = myCalendar.get(Calendar.HOUR_OF_DAY)
                val m = myCalendar.get(Calendar.MINUTE)
                val tpd = TimePickerDialog(this@EditActivity, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    binding.editTextTimePicker.setText("${twoDigits(hourOfDay)}:${twoDigits(minute)}")
                },h,m,true)
                tpd.show()
            }
        }
    }
    private fun saveData(){
        val poll= EntityPolls()
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
        poll.datePick=binding.editTextDatePicker.text.toString()
        poll.timePick=binding.editTextTimePicker.text.toString()
        Log.d("mensajes","${poll.id_user} $position")
        val request = listPolls.edit(position,poll)
        if(request){
            Toast.makeText(this@EditActivity,"Encuesta editada correctamente",Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this@EditActivity,"Error al editar la encuesta",Toast.LENGTH_SHORT).show()
        }
    }
    private fun uploadData(index:Int){
        var poll = listPolls.getPoll(index)
        binding.editTextTextPollName.setText(poll.pollName)
        binding.editTextTextPersonName.setText(poll.name)
        binding.editTextTextLastnName.setText(poll.lastName)
        binding.editTextTextMultiLine.setText(poll.comments)
        when(poll.typeOfGrafic){
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
            Toast.makeText(this@EditActivity,message, Toast.LENGTH_SHORT).show()
        }
        return correct
    }
    fun twoDigits(number:Int):String{
        return if(number<=9)"0$number" else number.toString()
    }
}