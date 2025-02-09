package com.domain.mapper

import com.data.persistence.models.EmployeeDao
import com.domain.models.Employee
import com.domain.models.Salary
import com.domain.models.UpdateEmployee

fun Employee.toUpdateEmployee() : UpdateEmployee {
    return UpdateEmployee(
        dni = dni,
        name = name,
        password = password,
        description = description,
        salary = salary,
        phone = phone,
        urlImage = urlImage,
        token = token,
        disponible =disponible

    )
}

/*
Esta función de extensión, la vamos a usar, siempre que queramos actualizar los datos de un
employee que ya ha sido leído de la BBDD y simplemente hemos modificado algún atributo, por ejemplo el token.
 */
fun UpdateEmployee.toEmployee() : Employee {
    return Employee(
        dni = dni!!,
        name = name!!,
        password = password!!,
        description = description!!,
        salary = salary!!,
        phone = phone!!,
        urlImage = urlImage!!,
        token = token!!,
        disponible =disponible!!


    )
}

fun EmployeeDao.toEmployee () : Employee {

    val e = Employee(
        this.name ?: "Sin nombre",
        this.dni,
        this.password,
        this.description ?: "Sin descripción",
        //Salary.valueOf(employeeDao.salary),
        this.salary.toSalaryOrDefault(),
        this.phone ?: "000-0000-0000",
        this.urlImage ?: "null",
        this.isActive,
        this.token ?: "null",
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
