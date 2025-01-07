package com.domain.usecase

import com.domain.repository.EmployeeInterface
import com.srodenas.data.models.Employee

class GetEmployeByDni (val repository : EmployeeInterface) {
    var dni : String? = null


    operator fun invoke() : Employee? {
        return if (dni?.isNullOrBlank() == true)
                null
            else{
                repository.getEmployeeByDni(dni!!)
        }
    }
}