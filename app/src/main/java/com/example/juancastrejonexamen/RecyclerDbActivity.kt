package com.example.juancastrejonexamen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.juancastrejonexamen.Adapters.SurveyAdapter
import com.example.juancastrejonexamen.Adapters.SurveyAdapterDB
import com.example.juancastrejonexamen.Data.ListPolls
import com.example.juancastrejonexamen.Data.SurveyDB
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.databinding.ActivityRecyclerBinding
import com.example.juancastrejonexamen.databinding.ActivityRecyclerDbBinding

class RecyclerDbActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecyclerDbBinding
    private val id_user:Int by lazy {
        intent.getIntExtra(Constants.ID,-1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerDbBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.txt_recycler)

        loadRecycler()
    }

    override fun onPause() {
        super.onPause()
        Log.d(Constants.LOG_TAG,"Entre a OnPause")
        finish()
    }
    override fun onStop() {
        super.onStop()
        Log.d(Constants.LOG_TAG,"Entre a OnStop")
        finish()
    }
    override fun onRestart() {
        super.onRestart()
        loadRecycler()
    }
    fun loadRecycler(){
        val list = SurveyDB(this@RecyclerDbActivity).getAllByUser(id_user)
        Log.d(Constants.LOG_TAG,list.toString())
        Log.d(Constants.LOG_TAG,id_user.toString())
        val adapter = SurveyAdapterDB(list,this@RecyclerDbActivity)
        val linearLayout = LinearLayoutManager(this@RecyclerDbActivity,
                LinearLayoutManager.VERTICAL,false)
        binding.rvwSurveys.layoutManager=linearLayout
        binding.rvwSurveys.setHasFixedSize(true)
        binding.rvwSurveys.adapter=adapter
    }
}