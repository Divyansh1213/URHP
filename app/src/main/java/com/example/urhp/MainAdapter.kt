package com.example.urhp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_row.view.*

class MainAdapter(val editClick: (Person) -> Unit) : RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {

    override fun getItemCount() = feed.count()

    var feed = mutableListOf<Person>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflator = LayoutInflater.from(p0.context)
        val cellForRow = layoutInflator.inflate(R.layout.recycler_row,p0,false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {
        val person = feed[p1]
        p0.itemView.tv_first_name.text = person.first_name
        p0.itemView.tv_last_name.text = person.last_name
        p0.itemView.tv_id.text = person.id.toString()
        p0.itemView.ib_edit.setOnClickListener {
            editClick(person)
        }

        val profilePhoto = p0.itemView.iv_profile
        Picasso.with(p0.itemView.context).load(person.avatar).into(profilePhoto)


    }



    inner class CustomViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }
}