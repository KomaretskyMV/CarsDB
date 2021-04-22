package Data

import Model.Car
import androidx.room.*

@Dao
interface CarDAO {

    @Insert
    fun addCar(car : Car) : Long

    @Update
    fun updateCar(car: Car)

    @Delete
    fun deleteCar(car: Car)

    @Query("select * from cars")
    fun getAllCars() : List<Car>

    @Query("select * from cars where car_id ==:carId ")
    fun getCar(carId : Long) : Car
}