package com.data.models

import com.domain.models.Employee
import com.domain.models.Salary


/*
Simulamos nuestro repositorio de datos. Aquí tendremos la lista de datos.
Lo tendremos para la incorporación de los test.
 */
object EmployeeData  {
    val listEmployee = mutableListOf<Employee> (
        Employee("Sonia", "23456789B", "A project manager", Salary.Medium),
        Employee("Guille", "34567890C", "A designer", Salary.Low),
        Employee("Diego", "45678901D", "A data analyst", Salary.Medium),
        Employee("Santiago", "23344334Z", "A backend developer", Salary.High),
        Employee("María", "67890123F", "A UX/UI designer", Salary.Medium),
        Employee("Carlos", "78901234G", "A mobile developer", Salary.Low),
        Employee("Laura", "89012345H", "A product owner", Salary.Medium),
        Employee("Luis", "90123456I", "A DevOps engineer", Salary.High),
        Employee("Ana", "01234567J", "A frontend developer", Salary.Medium),
        Employee("Pablo", "11223344K", "A QA engineer", Salary.Low),
        Employee("Carmen", "22334455L", "A scrum master", Salary.Medium),
        Employee("Javier", "33445566M", "A technical writer", Salary.Low),
        Employee("Marta", "44556677N", "An HR specialist", Salary.Medium)
    )

}