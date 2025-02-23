package com.domain.usecase

import com.domain.infraestructure.Utils
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
            try {
                updateEmployee?.urlImage?.let{  newImg->//siempre que haya una nueva imagen a insertar.
                    //estoy dentro de la nueva imagen a crear.
                    val employee = repository.getEmployeeByDni(dni!!)  //necesito el empleado, para la antigua imagen.
                    employee?.let { employee ->
                        employee.urlImage?.let{ oldImg->  //Si hay imagen antigua, me la cargo
                            Utils.deleteImage(employee.dni, oldImg)  //la elimino.
                        }
                    }
                    //ahora tengo que crear la nueva imagen.
                    val newImagenUrl = Utils.createBase64ToImg(newImg, dni!!)
                    updateEmployee!!.urlImage = newImagenUrl
                }//fin de si hay nueva imagen a insertar.
                val employee = repository.updateEmployee(updateEmployee!!, dni!!)
                employee
            }catch (e: Exception){
                e.printStackTrace()
                null
            }
        }

    }
}