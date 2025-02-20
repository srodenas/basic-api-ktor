package com.domain.usecase

import com.domain.models.Employee
import com.domain.repository.EmployeeInterface
import com.ktor.ApplicationContext

class GetEmployeByDniUseCase (val repository : EmployeeInterface) {
    var dni : String? = null


    suspend operator fun invoke() : Employee? {
        return if (dni?.isNullOrBlank() == true)
                null
            else{
                val emp = repository.getEmployeeByDni(dni!!)
                emp?.urlImage?.let{
                    imageName ->
                        val local = ApplicationContext.context.environment.config.property("ktor.urlPath.baseUrl").getString()
                        val relativePath = ApplicationContext.context.environment.config.property("ktor.path.images").getString()
                        emp.urlImage = "$local/$relativePath/imageName"
                }
                return emp
        }
    }
}