package com.example.juancastrejonexamen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.juancastrejonexamen.Adapters.SurveyAdapter
import com.example.juancastrejonexamen.Data.ListPolls
import com.example.juancastrejonexamen.Tools.Constants
import com.example.juancastrejonexamen.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecyclerBinding
    private val listSsurveys= ListPolls()
    private val id_user:Int by lazy {
        intent.getIntExtra(Constants.ID,-1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.txt_recycler)

        loadRecycler()
    }

    override fun onRestart() {
        super.onRestart()
        loadRecycler()
    }
    fun loadRecycler(){
        var list = listSsurveys.getListPollsArray(id_user)
        val adapter = SurveyAdapter(list,this@RecyclerActivity)
        val lineraLayout = LinearLayoutManager(this@RecyclerActivity,
            LinearLayoutManager.VERTICAL,false)
        binding.rvwSurveys.layoutManager=lineraLayout
        binding.rvwSurveys.setHasFixedSize(true)
        binding.rvwSurveys.adapter=adapter
    }
}