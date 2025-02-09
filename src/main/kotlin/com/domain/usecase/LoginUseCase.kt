package com.domain.usecase

import com.domain.mapper.toEmployee
import com.domain.mapper.toUpdateEmployee
import com.domain.models.Employee
import com.domain.repository.EmployeeInterface
import com.domain.security.JwtConfig

            /*
                zona de token.
                1.- Debo generar un token de usuario
                2.- Debo de actualizar el token en dicho usuario
                3.- Debo devolver el token.
                 */

class LoginUseCase (val repository : EmployeeInterface){
    suspend operator fun invoke(dni: String ?, pass:String ?): Employee ? {
        if (dni.isNullOrBlank() || pass.isNullOrBlank()) return null

        return try{
            val em = repository.login(dni, pass)  ?: null//ya tengo el usuario

            em!!.token = JwtConfig.generateToken(em.dni)  //genero un nuevo token
            val updateEmployee = em.toUpdateEmployee()  //actualiamos el token del employee
            val res = repository.updateEmployee(updateEmployee, dni)  //actualizamos el usuario con el token cambiado.
            return if (res) //si la actualización ha sido acertada
                updateEmployee.toEmployee()  //devolvemos el employee. El mapping es seguro!!!!.
            else
                null
        }catch (e: Exception){
            println("Error en login:  ${e.localizedMessage}")
            null
        }
    }

}