package com.example.android.carsdbroom

import Data.CarsAppDatabase
import Model.Car
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Suppress("SENSELESS_COMPARISON")
class MainActivity : AppCompatActivity() {

    private lateinit var carsAdapter : CarsAdapter
    private val cars : MutableList<Car> = ArrayList()
    private lateinit var recyclerView : RecyclerView
//    private lateinit var dbHandler : DatabaseHandler
    private lateinit var carsAppDatabase: CarsAppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
//        dbHandler = DatabaseHandler(this)
        carsAppDatabase = Room.databaseBuilder(applicationContext,
            CarsAppDatabase::class.java, "CarsBD").allowMainThreadQueries().build()

//        cars.addAll(dbHandler.getAllCars())
        cars.addAll(carsAppDatabase.getCarDAO().getAllCars())

        carsAdapter = CarsAdapter(this, cars, this@MainActivity)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = carsAdapter

        val floatActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatActionButton.setOnClickListener { addAndEditCars(false, null, -1) }
    }

    fun addAndEditCars(isUpdate: Boolean, car: Car?, position: Int) {
        val layoutInflater = LayoutInflater.from(applicationContext)
        val view = layoutInflater.inflate(R.layout.layout_add_car, null)

        val alertDialogBuilderUserInput = AlertDialog.Builder(this@MainActivity)
        alertDialogBuilderUserInput.setView(view)

        val newCarTitle = view.findViewById<TextView>(R.id.newCarTitle)
        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
        val priceEditText = view.findViewById<EditText>(R.id.priceEditText)

        newCarTitle.text = if (!isUpdate) "Add Car" else "Edit Car"

        if (isUpdate && car != null) {
            nameEditText.setText(car.name)
            priceEditText.setText(car.price)
        }

        alertDialogBuilderUserInput
            .setCancelable(false)
            .setPositiveButton(if (isUpdate) "Update" else "Save", DialogInterface.OnClickListener {
                    dialog, which ->  })
            .setNegativeButton(if (isUpdate) "Delete" else "Cancel", DialogInterface.OnClickListener {
                    dialog, which -> if (isUpdate) deleteCar(car, position) else dialog.cancel()})

        val alertDialog = alertDialogBuilderUserInput.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            when {
                TextUtils.isEmpty(nameEditText.text.toString()) -> {
                    Toast.makeText(this@MainActivity, "Enter car name!", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(priceEditText.text.toString()) -> {
                    Toast.makeText(this@MainActivity, "Enter car price!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    alertDialog.dismiss()
                }
            }

            if (isUpdate && car != null) {
                updateCar(nameEditText.text.toString(), priceEditText.text.toString(), position)
            } else {
                createCar(nameEditText.text.toString(), priceEditText.text.toString())
            }
        }
    }

    private fun deleteCar(car: Car?, position: Int) {
        cars.removeAt(position)
//        dbHandler.deleteCar(car!!)
        carsAppDatabase.getCarDAO().deleteCar(car!!)
        carsAdapter.notifyDataSetChanged()

    }

    private fun updateCar(name : String, price : String, position: Int) {
        val car = cars[position]
        car.name = name
        car.price = price
//        dbHandler.updateCar(car)
        carsAppDatabase.getCarDAO().updateCar(car)
        carsAdapter.notifyDataSetChanged()
    }

    private fun createCar(name: String, price: String) {
//        val id = dbHandler.insertCar(name, price)
//        val car = dbHandler.getCar(id)
        val id = carsAppDatabase.getCarDAO().addCar(Car(0, name, price))
        val car = carsAppDatabase.getCarDAO().getCar(id)
        if (car != null) {
            cars.add(0, car)
            carsAdapter.notifyDataSetChanged()
        }
    }
}