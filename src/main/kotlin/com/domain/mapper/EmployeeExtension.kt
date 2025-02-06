package com.domain.mapper

import com.domain.models.Employee
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
fun UpdateEmployee.toUpdateEmployee() : Employee {
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