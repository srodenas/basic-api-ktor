package com.domain.usecase

import com.domain.repository.EmployeeInterface
import com.srodenas.data.models.Employee

class GetAllEmployees (val repository : EmployeeInterface){

    operator fun invoke(): List<Employee> = repository.getAllEmployee()
}