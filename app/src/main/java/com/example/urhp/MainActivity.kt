package com.example.urhp

import android.app.Dialog
//import android.content.Context
//import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
//import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
//import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope.coroutineContext
//import kotlinx.android.synthetic.main.custom_alert_box.*
//import kotlinx.android.synthetic.main.recycler_row.view.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var personViewModel: PersonViewModel
    var list = listOf<Person>()
    private lateinit var repository : PersonRepository

    val adapter = MainAdapter {
        Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.custom_alert_box)
            window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
            val etFirstName = findViewById<EditText>(R.id.et_first_name)
            etFirstName.setText(it.first_name, TextView.BufferType.EDITABLE)
            val eLastName = findViewById<EditText>(R.id.et_last_name)
            eLastName.setText(it.last_name, TextView.BufferType.EDITABLE)
            val btClose = findViewById<Button>(R.id.bt_close)
            btClose.setOnClickListener {
                dismiss()
            }
            val btSave = findViewById<Button>(R.id.bt_save)
            btSave.setOnClickListener { _ ->
                it.first_name = etFirstName.text.toString()
                it.last_name = eLastName.text.toString()
                //recyclerView_main.adapter?.notifyDataSetChanged()
                updateRecyclerView(it)
                dismiss()
            }
        }.show()
    }
    private fun updateRecyclerView(data: Person) {
        val index = adapter.feed.indexOfFirst {
            it.id == data.id
        }
        adapter.feed[index] = data
        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        personViewModel = PersonViewModel(application)
        //val builder = AlertDialog.Builder(this)
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = adapter
        // get all persons from DB. see if it is empty. only if it is empty, make api call
        if(personViewModel.getAllPersons().isEmpty()){
            Thread(BackgroundFetcher()).start()
        }
    }
    inner class BackgroundFetcher : Runnable {
        override fun run() {
            for (i in 1..4) {
                list = list + repository.fetchJSON(i)
            }
            personViewModel.insertAll(list.toList())

            adapter.feed = personViewModel.getAllPersons() as MutableList<Person>
            adapter.notifyDataSetChanged()
        }
    }
}



//class Feed(var person: MutableList<Person>)

//class Person(var id: Int, var first_name: String, var last_name: String, var avatar: String)
