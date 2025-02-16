package com.domain.usecase

import com.data.security.PasswordHash
import com.domain.models.Employee
import com.domain.models.Salary
import com.domain.models.UpdateEmployee
import com.domain.repository.EmployeeInterface

class RegisterUseCase(val repository: EmployeeInterface) {
    operator suspend fun invoke(employee: UpdateEmployee): Employee? {

        employee.dni = employee.dni!!
        employee.password = employee.password!!
        employee.description = employee.description?:"Sin determinar"  //por si es nulo
        employee.salary = employee.salary?: Salary.LOW                  //por si no tenía, lo ponemos bajo
        employee.phone = employee.phone?:"0000000"                      //por si no tenía, ponemos todos 00
        employee.urlImage = employee.urlImage?:""                       //por si no tenía, vacío.
        employee.disponible = employee.disponible?: true                //por si no tenía, a true
        employee.token = employee.token?: ""                            //sin nada.

        return if (repository.login(employee.dni!!, employee.password!!)!=null)
                    null
                else
                    repository.register(employee)
    }
}