package com.example.sqlitedemo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class Adapter(var context: Context, var arrayList: ArrayList<CustomerModel>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = LayoutInflater.from(context).inflate(R.layout.customer_row,parent,false)
        return CustomerViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val id = arrayList[position].id
        val name = arrayList[position].name
        val age = arrayList[position].age
        val isActive = arrayList[position].isActive

        (holder as CustomerViewHolder).initializeRowUIComponent(
            id,
            name,
            age,
            isActive
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    inner class CustomerViewHolder(viewHolder: View): RecyclerView.ViewHolder(viewHolder){
        var name = viewHolder.findViewById<TextView>(R.id.txtName)
        var isActive = viewHolder.findViewById<TextView>(R.id.txtIsActive)
        var layout = viewHolder.findViewById<LinearLayout>(R.id.layout)
        fun initializeRowUIComponent(id_: Int, name_:String, age_:Int, isActive_:Boolean){
            name.text = "$name_ ($age_)"
            isActive.text="Is Active: ${if (isActive_) "yes" else "no"}"
            layout.setOnClickListener {
                val dbHelper = DataBaseHelper(context)
                val customerModel = CustomerModel(id_, name_, age_, isActive_)
                dbHelper.DeleteOne(customerModel)
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}