package com.example.sqlitedemo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var et_name:EditText
    private lateinit var et_age:EditText
    private lateinit var sw_activeCustomer:Switch
    private lateinit var btn_viewAll: Button
    private lateinit var btn_add:Button
    private lateinit var rv_customerList:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_name = findViewById(R.id.et_name)
        et_age = findViewById(R.id.et_age)
        sw_activeCustomer = findViewById(R.id.sw_activeCustomer)
        btn_viewAll = findViewById(R.id.btn_viewAll)
        btn_viewAll = findViewById(R.id.btn_viewAll)
        btn_add = findViewById(R.id.btn_add)
        rv_customerList = findViewById(R.id.rv_customerList)

        btn_add.setOnClickListener{
            val name = et_name.text.toString()
            val age = et_age.text.toString().toInt()
            val isActive = sw_activeCustomer.isChecked

            var customerModel: CustomerModel

            try{
                customerModel = CustomerModel(-1,name, age, isActive)
                Toast.makeText(this, customerModel.toString(), Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(this, "Error creating customer", Toast.LENGTH_SHORT).show()
                customerModel = CustomerModel(-1, "error", 0, false)
            }
            val dataBaseHelper = DataBaseHelper(this@MainActivity)
            val success: Boolean = dataBaseHelper.addOne(customerModel)
            Toast.makeText(this, "Success=$success", Toast.LENGTH_SHORT).show()
        }

        btn_viewAll.setOnClickListener {
            ShowCustomerOnListView()
//            Toast.makeText(this,everyone.toString(),Toast.LENGTH_SHORT).show()
        }
    }
    fun ShowCustomerOnListView(){
        val databaseHelper = DataBaseHelper(this)
        val everyone = databaseHelper.getEveryone()
        val adapter = Adapter(this, everyone)
        rv_customerList.layoutManager = LinearLayoutManager(this)
        rv_customerList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}