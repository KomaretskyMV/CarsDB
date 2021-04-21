package Data

import Model.Car
import Utils.Util
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_CARS_TABLE = ("CREATE TABLE " + Util.TABLE_NAME + "("
            + Util.KEY_ID + " INTEGER PRIMARY KEY,"
            + Util.KEY_NAME + " TEXT,"
            + Util.KEY_PRICE + " TEXT" + ")")

        db.execSQL(CREATE_CARS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME)
        onCreate(db)
    }

    fun insertCar(name: String, price: String) : Long {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Util.KEY_NAME, name)
        contentValues.put(Util.KEY_PRICE, price)
        val id = db.insert(Util.TABLE_NAME, null, contentValues)

        db.close()
        return id
    }

    fun getCar(id: Long) : Car {
        val db = readableDatabase
        val cursor = db.query(
            Util.TABLE_NAME, arrayOf(Util.KEY_ID, Util.KEY_NAME, Util.KEY_PRICE),
            Util.KEY_ID + "=?", arrayOf(id.toString()),
            null, null, null, null
        )
        cursor?.moveToFirst()
        val car = Car(
            cursor.getString(0).toLong(),
            cursor.getString(1), cursor.getString(2)
        )

        cursor.close()
        return car
    }

    fun getAllCars() : List<Car> {
        val db = readableDatabase
        val carsList: MutableList<Car> = ArrayList()
        val selectAllCars = "SELECT * FROM " + Util.TABLE_NAME
        val cursor = db.rawQuery(selectAllCars, null)
        if (cursor.moveToFirst()) {
            do {
                val car = Car()
                car.id = (cursor.getString(0).toLong())
                car.name = (cursor.getString(1))
                car.price = (cursor.getString(2))
                carsList.add(car)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return carsList
    }

    fun updateCar(car: Car) : Int {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Util.KEY_NAME, car.name)
        contentValues.put(Util.KEY_PRICE, car.price)

        return db.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?",
            arrayOf(car.id.toString()))
        db.close()
    }

    fun deleteCar(car: Car) {
        val db = writableDatabase
        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
            arrayOf(car.id.toString()))
        db.close()
    }

    fun getCarsCount() : Int {
        val db = readableDatabase
        val countQuery = "SELECT * FROM " + Util.TABLE_NAME
        val cursor = db.rawQuery(countQuery, null)
        return cursor.count
    }
}