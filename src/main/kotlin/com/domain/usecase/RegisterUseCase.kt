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
        employee.description = employee.description?:"Sin determinar"
        employee.salary = employee.salary?: Salary.LOW
        employee.phone = employee.phone?:"0000000"
        employee.urlImage = employee.urlImage?:""
        employee.disponible = employee.disponible?: true
        employee.token = employee.token?: ""

        return if (repository.login(employee.dni!!, employee.password!!)!=null)
                    null
                else
                    repository.register(employee)
    }
}