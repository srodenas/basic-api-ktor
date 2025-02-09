package com.data.inmemory.repository

import com.data.inmemory.models.EmployeeData
import com.domain.models.Employee
import com.domain.models.Salary
import com.domain.models.UpdateEmployee
import com.domain.repository.EmployeeInterface

/*
Marca el acceso a datos dependiendo del contrato. Será la implementación de acceso a Memoria.
 */
class MemoryEmployeeRepository : EmployeeInterface {

    override suspend fun getAllEmployee(): List<Employee> {
        return EmployeeData.listEmployee
    }


    override suspend fun getEmployeeBySalary(salary: Salary): List <Employee> {
       return EmployeeData.listEmployee.filter { it.salary == salary }
    }


    override suspend fun getEmployeeByName(name: String): List <Employee> {
        return EmployeeData.listEmployee.filter { it.name == name }
    }


    override suspend fun getEmployeeByDni(id: String): Employee ?  = EmployeeData.listEmployee.filter { it.dni == id}.firstOrNull()


    override suspend fun postEmployee(employee: Employee) : Boolean{
        val emp = getEmployeeByDni(employee.dni)
        return if (emp!= null) {
            false
        } else{
                EmployeeData.listEmployee.add(employee)
                true
            }
    }


    /*
    Buscamos el empleado a modificar y sobreescribimos el mismo objeto con los datos modificados.
    Para ello, utilizamos el método copy que tiene cualquier objeto.
    Os recuerdo que el copy, vuelve a referenciar al objeto, por eso hay que sobreescribirlo en la lista.
     */
    override suspend fun updateEmployee(updateEmployee: UpdateEmployee, dni:String) : Boolean{
        val index = EmployeeData.listEmployee.indexOfFirst { it.dni == dni }
        return if (index != -1) {
            val originEmployee = EmployeeData.listEmployee[index]
            EmployeeData.listEmployee[index] =  originEmployee
                .copy(
                    name = updateEmployee.name ?: originEmployee.name,
                    description = updateEmployee.description ?: originEmployee.description,
                    salary = updateEmployee.salary ?: originEmployee.salary,
                    phone = updateEmployee.phone ?: originEmployee.phone,
                    urlImage = updateEmployee.urlImage ?: originEmployee.urlImage,
                    disponible = updateEmployee.disponible ?: originEmployee.disponible,
                    token = updateEmployee.token ?: originEmployee.token,


                )
            true
        }
        else{
            false
        }
    }

    override suspend fun deleteEmployee(dni: String): Boolean {
        val index = EmployeeData.listEmployee.indexOfFirst { it.dni == dni }
        return if (index != -1) {
            EmployeeData.listEmployee.removeAt(index)
            true
        }else{
            false
        }
    }

    override suspend fun login(dni: String, pass: String): Employee? {
        TODO("Not yet implemented")
    }

    override suspend fun register(employee: UpdateEmployee): Employee? {
        TODO("Not yet implemented")
    }

}