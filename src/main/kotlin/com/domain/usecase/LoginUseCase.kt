package com.domain.usecase

import com.domain.repository.EmployeeInterface

class LoginUseCase (val repository : EmployeeInterface){
    suspend operator fun invoke(dni: String ?, pass:String ?): Boolean {
        return if (dni.isNullOrBlank() || pass.isNullOrBlank())
           false
        else {
            try{
                val res = repository.login(dni, pass)
                res
            }catch (e: Exception){
                println("Error en login:  ${e.localizedMessage}")
                false
            }

        }
    }

}