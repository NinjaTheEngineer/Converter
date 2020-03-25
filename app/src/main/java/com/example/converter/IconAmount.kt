package com.example.converter

class IconAmount {
    lateinit var name: String
    lateinit var size: String
    var amount: Int = 0
    var image: Int = 0
    var dim: Int = 70

    constructor(size: String, name: String, amount: Int, image: Int, dim: Int){
        this.name = name
        this.size = size
        this.amount = amount
        this.image = image
        this.dim = dim
    }


}