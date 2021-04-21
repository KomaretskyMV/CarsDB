package com.example.android.carsdbroom

import Model.Car
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarsAdapter(private val context: Context, private val cars: MutableList<Car>, private val mainActivity: MainActivity) :
//class CarsAdapter(context: Context, cars: MutableList<Car>, mainActivity: MainActivity) :
    RecyclerView.Adapter<CarsAdapter.MyViewHolder>() {
    /*init {
        val context = context
        var cars = cars
        val mainActivity = mainActivity
    }*/

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        init {
            var nameTextView: TextView = itemView.findViewById<TextView>(R.id.nameTextView)
            var priceTextView = itemView.findViewById<TextView>(R.id.priceTextView)
//        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.car_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarsAdapter.MyViewHolder, position: Int) {

        val car = cars[position]
        holder.nameTextView.text = car.name
        holder.priceTextView.text = "${car.price} \$"
        holder.itemView.setOnClickListener { mainActivity.addAndEditCars(true, car, position) }

        /*holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mainActivity.addAndEditCars(true, car, position)
            }
        })*/
    }

    override fun getItemCount(): Int {
        return cars.size
    }

}