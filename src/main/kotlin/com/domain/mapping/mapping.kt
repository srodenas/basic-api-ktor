package com.domain.mapping

import com.data.persistence.models.EmployeeDao
import com.domain.models.Employee
import com.domain.models.Salary

fun EmploeeDaoToEmployee (employeeDao : EmployeeDao) : Employee {

        val e = Employee(
            employeeDao.name,
            employeeDao.dni,
            employeeDao.description,
            Salary.valueOf(employeeDao.salary),
            employeeDao.phone,
            employeeDao.urlImage,
            employeeDao.isActive,
            employeeDao.token,
        )
        return e
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