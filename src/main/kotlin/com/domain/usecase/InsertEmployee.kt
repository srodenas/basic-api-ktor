package com.domain.usecase

import com.domain.repository.EmployeeInterface
import com.srodenas.data.models.Employee

class InsertEmployee  (val repository : EmployeeInterface){

    var employee : Employee? = null

    operator fun invoke() : Boolean {
        /*
        Si devuelve null, es que ya existe el empleado
         */
        return if (employee == null) {
            false
        }else {
            repository.postEmployee(employee!!)
        }
    }
}