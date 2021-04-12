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
import com.example.juancastrejonexamen.*
import com.example.juancastrejonexamen.Data.ListPolls
import com.example.juancastrejonexamen.Data.SurveyDB
import com.example.juancastrejonexamen.Entity.EntitySurvey
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.databinding.ItemSurveysDbBinding
import com.google.android.material.snackbar.Snackbar

class SurveyAdapterDB(val surveyList:ArrayList<EntitySurvey>, val context: Context): RecyclerView.Adapter<SurveyHolderDB>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyHolderDB {
        val inflater = LayoutInflater.from(parent.context)
        return SurveyHolderDB(inflater.inflate(R.layout.item_surveys_db,parent,false))
    }

    override fun getItemCount(): Int {
        return surveyList.size
    }
    override fun onBindViewHolder(holder: SurveyHolderDB, position: Int) {
        holder.textViewFullName.text = "${surveyList[position].name} ${surveyList[position].lastName}"
        holder.textViewSurveyDate.text = surveyList[position].polldate
        holder.textViewSurveyName.text = surveyList[position].pollName
        holder.textViewDatePicker.text = surveyList[position].datePick
        holder.textViewTimePicker.text = surveyList[position].timePick
        if(surveyList[position].typeOfGraphic==1){
            holder.imageViewLogo.setImageResource(R.drawable.ic_leaderboard_black_24dp)
        }
        if(surveyList[position].typeOfGraphic==2){
            holder.imageViewLogo.setImageResource(R.drawable.ic_donut_small_black_24dp)
        }
        if(surveyList[position].typeOfGraphic==3){
            holder.imageViewLogo.setImageResource(R.drawable.ic_trending_up_black_24dp)
        }
        val listSurvey = ListPolls()
        val positionList = listSurvey.getPollIndex(surveyList[position].id_user,position)
        holder.imageButtonDelete.setOnClickListener{
            myDialog(surveyList[position].id,position,it).show()
        }
        holder.imageButtonEdit.setOnClickListener{
            notifyItemChanged(position)
            val intent = Intent(context, UpdateActivity::class.java).apply {
                putExtra(Constants.ID,surveyList[position].id)
            }
            context.startActivity(intent)
        }
        holder.imageButtonSee.setOnClickListener{
            val intent = Intent(context, SeeSurveyActivity::class.java).apply {
                putExtra(Constants.ID,surveyList[position].id)
            }
            context.startActivity(intent)
        }
    }
    private fun myDialog(id:Int,position:Int,view: View): AlertDialog {
        val myAlert = AlertDialog.Builder(context)
        myAlert.setTitle("EstudiantesJuan v0.1")
        myAlert.setIcon(R.drawable.ic_add_circle_black_24dp)
        myAlert.setMessage("¿Desea eliminar la encuesta seleccionada?")
        myAlert.setPositiveButton("Simón"){ dialogInterface: DialogInterface, i: Int ->
            if(SurveyDB(context).delete(id)!=0){
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
class SurveyHolderDB(view: View): RecyclerView.ViewHolder(view){
    val binding = ItemSurveysDbBinding.bind(view)
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