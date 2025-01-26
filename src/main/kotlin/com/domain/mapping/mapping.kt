package com.domain.mapping

import com.data.persistence.models.EmployeeDao
import com.domain.models.Employee
import com.domain.models.Salary

fun EmployeeDaoToEmployee (employeeDao : EmployeeDao) : Employee {

        val e = Employee(
            employeeDao.name ?: "Sin nombre",
            employeeDao.dni,
            employeeDao.password,
            employeeDao.description ?: "Sin descripción",
            //Salary.valueOf(employeeDao.salary),
            employeeDao.salary.toSalaryOrDefault(),
            employeeDao.phone ?: "000-0000-0000",
            employeeDao.urlImage ?: "null",
            employeeDao.isActive,
            employeeDao.token ?: "null",
        )
        return e
}

/*
Extiendo la clase String con esta validación
 */
fun String.toSalaryOrDefault(): Salary {
    return try{
        Salary.valueOf(this)
    }catch (e: IllegalArgumentException){
        Salary.LOW  //pongo bajo por defecto
    }
}
/*
    val name : String,
    val dni : String,
    val description: String,
    val salary: Salary,
    val phone: String,
    val url_image: String ? = null,
    val disponible: Boolean = true,
    val token:String ? = null
 */