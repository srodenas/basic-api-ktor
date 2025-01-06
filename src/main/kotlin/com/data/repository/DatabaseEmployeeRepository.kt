package com.data.repository

import com.domain.repository.EmployeeInterface
import com.srodenas.data.models.Employee
import com.srodenas.data.models.Salary

class DatabaseEmployeeRepository : EmployeeInterface {
    override fun getAllEmployee(): List<Employee> {
        TODO("Not yet implemented")
    }

    override fun getEmployeeBySalary(salary: Salary): List< Employee >{
        TODO("Not yet implemented")
    }

    override fun getEmployeeByName(name: String): List<Employee> {
        TODO("Not yet implemented")
    }

    override fun getEmployeeByIde(id: String): Employee ? {
        TODO("Not yet implemented")
    }

    override fun postEmployee(employee: Employee) : Boolean {
        TODO("Not yet implemented")
    }

    override fun updateEmployee(employee: Employee) : Boolean{
        TODO("Not yet implemented")
    }

    override fun deleteEmployee(dni: String): Boolean {
        TODO("Not yet implemented")
    }
}