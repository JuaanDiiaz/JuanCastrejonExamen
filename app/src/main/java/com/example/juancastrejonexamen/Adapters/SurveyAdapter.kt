package com.example.juancastrejonexamen.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.juancastrejonexamen.Data.ListPolls
import com.example.juancastrejonexamen.DetailActivity
import com.example.juancastrejonexamen.EditActivity
import com.example.juancastrejonexamen.Entity.EntityPolls
import com.example.juancastrejonexamen.R
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.databinding.ItemSurveysBinding
import com.google.android.material.snackbar.Snackbar

class SurveyAdapter(val surveyList:ArrayList<EntityPolls>, val context: Context): RecyclerView.Adapter<SurveyHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SurveyHolder(inflater.inflate(R.layout.item_surveys,parent,false))
    }

    override fun getItemCount(): Int {
        return surveyList.size
    }
    override fun onBindViewHolder(holder: SurveyHolder, position: Int) {
        holder.textViewFullName.text = "${surveyList[position].name} ${surveyList[position].lastName}"
        holder.textViewSurveyDate.text = surveyList[position].polldate
        holder.textViewSurveyName.text = surveyList[position].pollName
        holder.textViewDatePicker.text = surveyList[position].datePick
        holder.textViewTimePicker.text = surveyList[position].timePick
        if(surveyList[position].typeOfGrafic==1){
            holder.imageViewLogo.setImageResource(R.drawable.ic_leaderboard_black_24dp)
        }
        if(surveyList[position].typeOfGrafic==2){
            holder.imageViewLogo.setImageResource(R.drawable.ic_donut_small_black_24dp)
        }
        if(surveyList[position].typeOfGrafic==3){
            holder.imageViewLogo.setImageResource(R.drawable.ic_trending_up_black_24dp)
        }
        val listSurvey = ListPolls()
        val positionList = listSurvey.getPollIndex(surveyList[position].id_user,position)
        holder.imageButtonDelete.setOnClickListener{
            myDialog(position,positionList,it).show()
        }
        holder.imageButtonEdit.setOnClickListener{
            notifyItemChanged(position)
            val intent = Intent(context, EditActivity::class.java).apply {
                putExtra(Constants.Position,positionList)
                putExtra(Constants.ID,surveyList[position].id_user)
            }
            context.startActivity(intent)
        }
        holder.imageButtonSee.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(Constants.ID,positionList)
            }
            context.startActivity(intent)
        }
    }
    private fun myDialog(position: Int,positionList:Int,view: View): AlertDialog {
        val myAlert = AlertDialog.Builder(context)
        myAlert.setTitle("EstudiantesJuan v0.1")
        myAlert.setIcon(R.drawable.ic_add_circle_black_24dp)
        myAlert.setMessage("¿Desea eliminar la encuesta seleccionada?")
        myAlert.setPositiveButton("Simón"){ dialogInterface: DialogInterface, i: Int ->
            val listSurvey = ListPolls()
            if(listSurvey.delete(positionList)){
                surveyList.removeAt(position)
                Toast.makeText(context,"Encuesta eliminada exitosamente", Toast.LENGTH_SHORT).show()
                notifyDataSetChanged()
            }
            else{
                Snackbar.make(view,"Error al intentar eliminar la encuesta", Snackbar.LENGTH_SHORT).show()

            }
        }
        return myAlert.create()
    }

}
class SurveyHolder(view: View): RecyclerView.ViewHolder(view){
    val binding = ItemSurveysBinding.bind(view)
    val imageViewLogo = binding.imageViewLogo
    val textViewFullName = binding.textViewFullName
    val textViewSurveyName = binding.textViewSurveyName
    val textViewSurveyDate = binding.textViewSurveyDate
    val imageButtonDelete= binding.imageButtonDelete
    val imageButtonEdit= binding.imageButtonEdit
    val imageButtonSee= binding.imageButtonSee
    val textViewDatePicker = binding.textViewDatePicker
    val textViewTimePicker = binding.textViewTimePicker
}