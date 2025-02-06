package com.domain.repository

import com.domain.models.Employee
import com.domain.models.Salary
import com.domain.models.UpdateEmployee

/*
Como vamos a lanzar consultas a la BBDD, deben hacerse por medio de corrutinas.
Cuando desde una corrutina ejecuto un método, éste tiene que estar definido como suspend.
 */

interface EmployeeInterface {
    suspend fun getAllEmployee () : List <Employee>

    suspend fun getEmployeeBySalary ( salary: Salary) : List<Employee>

    suspend fun getEmployeeByName ( name : String) : List<Employee>

    //Debe ser nullable, por si no existe.
    suspend fun getEmployeeByDni (dni: String) : Employee ?

    suspend fun postEmployee(employee: Employee) : Boolean

    suspend fun updateEmployee(employee: UpdateEmployee, dni:String) : Boolean

    suspend fun deleteEmployee(dni : String) : Boolean

    suspend fun login(dni: String, pass: String) : Employee?  //más adelante, implementaré con token

    suspend fun register(employee: UpdateEmployee) : Employee? //Este será el que utilicemos para el registro

  //  suspend fun updateTokenUser(dni: String, token: String) : Boolean
}