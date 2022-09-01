package com.example.sqlitedemo

class CustomerModel {
    var id: Int
    var name: String
    var age: Int
    var isActive: Boolean
    constructor(id: Int, name: String, age: Int, isActive: Boolean) {
        this.id = id
        this.name = name
        this.age = age
        this.isActive = isActive
    }


}