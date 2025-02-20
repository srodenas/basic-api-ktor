package com.domain.usecase

import com.domain.models.Employee
import com.domain.repository.EmployeeInterface
import com.ktor.ApplicationContext

class GetAllEmployeesUseCase (val repository : EmployeeInterface){

    suspend operator fun invoke(): List<Employee> {

        val listEmployee = repository.getAllEmployee()  //toda la lista de empleados
        return listEmployee.map{
            emp->  //para cada empleado
                emp.urlImage?.let{  //si la imagen no es nula
                    imageName ->  //parametro imagen que tiene el empleado
                        val local = ApplicationContext.context.environment.config.property("ktor.urlPath.baseUrl").getString()
                        val relativePath = ApplicationContext.context.environment.config.property("ktor.path.images").getString()
                        emp.urlImage = "$local/$relativePath/$imageName"
            }
            emp
        }

       // return repository.getAllEmployee()
    }
}