package com.domain.usecase

import com.domain.infraestructure.Utils
import com.domain.models.Employee
import com.domain.models.Salary
import com.domain.models.UpdateEmployee
import com.domain.repository.EmployeeInterface
import com.ktor.ApplicationContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

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
        ProviderUseCase.logger.warn("Register-UseCase: Compruebo login antes de registro")
        return if (repository.login(employee.dni!!, employee.password!!)!=null)
                    null
                else {
                  //  val reg = repository.register(employee)  //registro el nuevo employee
                    ProviderUseCase.logger.warn("Register-UseCase: Login comprobado antes de registro y null")
                    employee.apply{ //modifico el registrado, en caso de que no sea null
                        val isCreate = Utils.createDir(dni!!)   //creamos directorio con el dni, siempre y cuando no exista.
                        if (isCreate){
                            ProviderUseCase.logger.warn("Register-UseCase: Carpeta creada")
                            if (!urlImage.isNullOrBlank()){
                                urlImage = Utils.createBase64ToImg(urlImage!!, dni!!)
                            }
                        }else
                            throw IllegalStateException("No se pudo crear el directorio del empleado. Puede que ya exista")

                    }

                    ProviderUseCase.logger.warn("Register-UseCase: Se procede al registro")
                    val reg  = repository.register(employee)//devuelvo el employee creado o nulo si no se ha podido crear.
                    reg
                }

    }


}