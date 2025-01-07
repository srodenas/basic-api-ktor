package com.domain.repository

import com.srodenas.data.models.*


interface EmployeeInterface {
    fun getAllEmployee () : List <Employee>

    fun getEmployeeBySalary ( salary: Salary) : List<Employee>

    fun getEmployeeByName ( name : String) : List<Employee>

    //Debe ser nullable, por si no existe.
    fun getEmployeeByDni (dni: String) : Employee ?

    fun postEmployee(employee: Employee) : Boolean

    fun updateEmployee(employee: Employee) : Boolean

    fun deleteEmployee(dni : String) : Boolean
}