package com.data.inmemory.models

import com.domain.models.Employee
import com.domain.models.Salary


/*
Simulamos nuestro repositorio de datos. Aquí tendremos la lista de datos.
Lo tendremos para la incorporación de los test.
 */
object  EmployeeData  {
    val listEmployee=  mutableListOf  <Employee>(
        Employee(
            name = "Santi",
            dni = "600123001",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A project manager",
            salary = Salary.HIGH,
            phone = "600123001",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Sonia",
            dni = "600123002",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A designer",
            salary = Salary.MEDIUM,
            phone = "600123002",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Guille",
            dni = "600123003",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A data analyst",
            salary = Salary.MEDIUM,
            phone = "600123003",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = false,
            token = ""
        ),
        Employee(
            name = "Diego",
            dni = "600123004",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A mobile developer",
            salary = Salary.MEDIUM,
            phone = "600123004",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Luis Torres",
            dni = "600123005",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A frontend developer",
            salary = Salary.LOW,
            phone = "600123005",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Lucía Martínez",
            dni = "600123006",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A backend developer",
            salary = Salary.HIGH,
            phone = "600123006",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = false,
            token = ""
        ),
        Employee(
            name = "Miguel Romero",
            dni = "600123007",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A project manager",
            salary = Salary.HIGH,
            phone = "600123007",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Sofía Jiménez",
            dni = "600123008",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A designer",
            salary = Salary.MEDIUM,
            phone = "600123008",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Pedro Castillo",
            dni = "600123009",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A data analyst",
            salary = Salary.LOW,
            phone = "600123009",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = false,
            token = ""
        ),
        Employee(
            name = "Laura Díaz",
            dni = "600123010",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A mobile developer",
            salary = Salary.MEDIUM,
            phone = "600123010",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Diego Gómez",
            dni = "600123011",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A frontend developer",
            salary = Salary.HIGH,
            phone = "600123011",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Natalia Ruiz",
            dni = "600123012",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A designer",
            salary = Salary.MEDIUM,
            phone = "600123012",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = false,
            token = ""
        ),
        Employee(
            name = "Javier Moreno",
            dni = "600123013",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A data analyst",
            salary = Salary.LOW,
            phone = "600123013",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Elena Vega",
            dni = "600123014",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A mobile developer",
            salary = Salary.MEDIUM,
            phone = "600123014",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Ricardo Flores",
            dni = "600123015",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A frontend developer",
            salary = Salary.HIGH,
            phone = "600123015",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Patricia Navarro",
            dni = "600123016",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A backend developer",
            salary = Salary.LOW,
            phone = "600123016",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Fernando Paredes",
            dni = "600123017",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A project manager",
            salary = Salary.HIGH,
            phone = "600123017",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = false,
            token = ""
        ),
        Employee(
            name = "Cristina Sáez",
            dni = "600123018",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A designer",
            salary = Salary.MEDIUM,
            phone = "600123018",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        ),
        Employee(
            name = "Raúl Castro",
            dni = "600123019",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A data analyst",
            salary = Salary.LOW,
            phone = "600123019",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = false,
            token = ""
        ),
        Employee(
            name = "Inés Sánchez",
            dni = "600123020",
            password = "nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK",
            description = "A mobile developer",
            salary = Salary.HIGH,
            phone = "600123020",
            urlImage = "https://cdn.pixabay.com/photo/2023/05/27/19/15/call-center-8022155_960_720.jpg",
            disponible = true,
            token = ""
        )
    )


}