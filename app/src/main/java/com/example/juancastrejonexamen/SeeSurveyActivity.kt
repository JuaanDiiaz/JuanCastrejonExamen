package com.example.juancastrejonexamen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.juancastrejonexamen.Data.SurveyDB
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.databinding.ActivityDetailBinding
import com.example.juancastrejonexamen.databinding.ActivitySeeSurveyBinding
import com.example.juancastrejonexamen.databinding.ActivityUpdateBinding

class SeeSurveyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeeSurveyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.txt_detail)

        val id:Int = intent.getIntExtra(Constants.ID,-1)
        if(id!=-1){
            val poll = SurveyDB(this@SeeSurveyActivity).getOne(id)
            binding.textViewPollName.text = poll.pollName
            binding.textViewPollDate.text = poll.polldate
            binding.textViewFullName.text = "${poll.name} ${poll.lastName}"
            binding.textViewGrafics.text= if(poll.typeOfGraphic==1) "Prefiere grafica de Barras" else if(poll.typeOfGraphic==2) "Prefiere grafica de Pastel" else if(poll.typeOfGraphic==3) "Prefiere grafica de Puntos" else ""
            binding.textViewCompras.text= if(poll.purchases) "Le interesan las ordenes de compra" else "No le interesan las ordenes de compra"
            binding.textViewVentas.text= if(poll.sales) "Le interesan los pedidos de venta" else "No le interesan los pedidos de venta"
            binding.textViewAlmacenes.text= if(poll.purchases) "Le interesan los inventarios" else "No le interesan los inventarios"
            binding.textViewTipoUser.text=if(poll.userType==1) "Operaciones" else if(poll.userType==2) "Administración" else if(poll.userType==3) "Tesoreria"
            else if(poll.userType==4) "Ventas" else if(poll.userType==5) "Inventario" else ""
            binding.textViewComments.text=if(poll.comments.isNotEmpty()) "${poll.comments}" else "Sin comentarios"
            binding.textViewDatePicker.text="Fecha: ${poll.datePick}"
            binding.textViewTimePicker.text="Hora: ${poll.timePick}"
        }
    }
}