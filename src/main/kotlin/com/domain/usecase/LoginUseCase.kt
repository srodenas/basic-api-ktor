package com.domain.usecase

import com.domain.repository.EmployeeInterface

class LoginUseCase (val repository : EmployeeInterface){
    suspend operator fun invoke(dni: String ?, pass:String ?): Boolean {
        return if (dni.isNullOrBlank() || pass.isNullOrBlank())
           false
        else repository.login(dni, pass)
    }

}