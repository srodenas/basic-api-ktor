package com.domain.usecase

import com.domain.infraestructure.Utils
import com.domain.repository.EmployeeInterface

//todo
//método que sólo debería de implementar el administrador.

class DeleteEmployeUseCase (val repository : EmployeeInterface){
    var dni : String? = null

    suspend operator fun invoke() : Boolean {
        return if (dni == null) {
            false
        }else{
            val employee = repository.getEmployeeByDni(dni!!)
            employee?.let { employee ->
                employee.urlImage?.let{ img->
                    Utils.deleteImage(employee.dni!!, img)
                    Utils.deleteDirectory(employee.dni!!)
                }
                return repository.deleteEmployee(employee.dni!!)
            }
            false
        }
    }
}