package Model

class Car() {
    var id : Long = 0
    lateinit var name : String
    lateinit var price : String
    constructor(id: Long, name: String, price: String) : this() {
        this.id = id
        this.name = name
        this.price = price
    }

    constructor(name: String, price: String) : this() {
        this.name = name
        this.price = price
    }

}