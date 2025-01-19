package com.data.persistence.repository

import com.domain.models.Employee
import com.domain.models.Salary
import com.domain.models.UpdateEmployee
import com.domain.repository.EmployeeInterface

class PersistenceEmployeeRepository: EmployeeInterface{
    override suspend fun getAllEmployee(): List<Employee> {
        TODO("Not yet implemented")
    }

    override suspend fun getEmployeeBySalary(salary: Salary): List<Employee> {
        TODO("Not yet implemented")
    }

    override suspend fun getEmployeeByName(name: String): List<Employee> {
        TODO("Not yet implemented")
    }

    override suspend fun getEmployeeByDni(dni: String): Employee? {
        TODO("Not yet implemented")
    }

    override  suspend fun postEmployee(employee: Employee): Boolean {
        TODO("Not yet implemented")
    }

    override suspend  fun updateEmployee(employee: UpdateEmployee, dni: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEmployee(dni: String): Boolean {
        TODO("Not yet implemented")
    }
}