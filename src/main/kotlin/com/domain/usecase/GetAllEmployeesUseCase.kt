package com.domain.usecase

import com.domain.models.Employee
import com.domain.repository.EmployeeInterface

class GetAllEmployeesUseCase (val repository : EmployeeInterface){

    suspend operator fun invoke(): List<Employee> = repository.getAllEmployee()
}