package Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
class Car @Ignore constructor() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "car_id")
    var id : Long = 0
    @ColumnInfo(name = "car_name")
    lateinit var name : String
    @ColumnInfo(name = "car_price")
    lateinit var price : String

    constructor(id: Long, name: String, price: String) : this() {
        this.id = id
        this.name = name
        this.price = price
    }

    @Ignore
    constructor(name: String, price: String) : this() {
        this.name = name
        this.price = price
    }

}