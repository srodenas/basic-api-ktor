package com.data.models

import com.srodenas.data.models.Employee

interface EmployeeInterface {
    fun getAllEmployee () : List <Employee>
    fun getEmployeeByIde (id: Int) : Employee

    fun postEmployee(employee: Employee)

    fun updateEmployee(employee: Employee)

    fun deleteEmployee(id : Int) : Boolean
}