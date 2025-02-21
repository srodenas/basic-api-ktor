package com.domain.usecase

import com.domain.models.Employee
import com.domain.models.UpdateEmployee
import com.domain.repository.EmployeeInterface

class UpdateEmployeeUseCase (val repository : EmployeeInterface){
    var updateEmployee: UpdateEmployee? = null
    var dni: String? = null

    suspend operator fun invoke() : Employee? {
        return if (updateEmployee == null || dni == null) {
            null
        }else{
            /*
            Para actualizar, primero tenemos que ver si tiene imagen para modificar.
            1 - Si tiene imagen a modificar, hay que eliminarla físicamente.
              - Hay que crear la nueva imagen, igual que hemos hecho en el insert.
              - Hay que modificar el nombre del atributo, con el nuevo nombre.
             */
            updateEmployee!!.urlImage.let{

            }
            val employee= repository.updateEmployee(updateEmployee!!, dni!!)

            employee
        }

    }
}