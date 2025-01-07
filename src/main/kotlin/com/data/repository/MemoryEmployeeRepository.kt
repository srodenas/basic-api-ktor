package com.data.repository

import com.data.models.EmployeeData
import com.domain.repository.EmployeeInterface
import com.srodenas.data.models.Employee
import com.srodenas.data.models.Salary

/*
Marca el acceso a datos dependiendo del contrato. Será la implementación de acceso a Memoria.
 */
class MemoryEmployeeRepository : EmployeeInterface {

    override fun getAllEmployee(): List<Employee> {
        return EmployeeData.listEmployee
    }


    override fun getEmployeeBySalary(salary: Salary): List <Employee> {
       return EmployeeData.listEmployee.filter { it.salary == salary }
    }


    override fun getEmployeeByName(name: String): List <Employee> {
        return EmployeeData.listEmployee.filter { it.name == name }
    }


    override fun getEmployeeByDni(id: String): Employee ?  = EmployeeData.listEmployee.filter { it.dni == id}.firstOrNull()


    override fun postEmployee(employee: Employee) : Boolean{
        val emp = getEmployeeByDni(employee.dni)
        return if (emp!= null) {
            false
        } else{
                EmployeeData.listEmployee.addLast(employee)
                true
            }
    }


    override fun updateEmployee(employee: Employee) : Boolean{
        val index = EmployeeData.listEmployee.indexOfFirst { it.dni == employee.dni }
        return if (index != -1) {
            EmployeeData.listEmployee[index] = EmployeeData.listEmployee[index].copy(
                name = employee.name,
                description = employee.description,
                salary = employee.salary
            )
            true
        }
        else{
            false
        }
    }

    override fun deleteEmployee(dni: String): Boolean {
        val index = EmployeeData.listEmployee.indexOfFirst { it.dni == dni }
        return if (index != -1) {
            EmployeeData.listEmployee.removeAt(index)
            true
        }else{
            false
        }
    }
}