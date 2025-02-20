package com.domain.usecase

import com.domain.models.Employee
import com.domain.models.Salary
import com.domain.repository.EmployeeInterface
import com.ktor.ApplicationContext

class GetEmployeesBySalaryUseCase (val repository : EmployeeInterface) {
    var filter : Salary? =null

    suspend operator fun invoke() : List<Employee> {
        return filter?.let {
            val list = repository.getEmployeeBySalary(it)
            list.map{
                emp->
                    emp.urlImage?.let{
                        imageName ->
                            val local = ApplicationContext.context.environment.config.property("ktor.urlPath.baseUrl").getString()
                            val relativePath = ApplicationContext.context.environment.config.property("ktor.path.images").getString()
                            emp.urlImage = "$local/$relativePath/imageName"
                    }
                emp
            }
        }?:run{
             emptyList()  //lista vacía
        }
    }
}