package com.data.persistence.repository

import com.data.persistence.models.EmployeeDao
import com.data.persistence.models.EmployeeTable
import com.data.persistence.models.suspendTransaction
import com.data.security.PasswordHash
import com.domain.mapping.EmployeeDaoToEmployee
import com.domain.mapping.toSalaryOrDefault
import com.domain.models.Employee
import com.domain.models.Salary
import com.domain.models.UpdateEmployee
import com.domain.repository.EmployeeInterface
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.update

class PersistenceEmployeeRepository: EmployeeInterface {

    /*
    getAllEmployee():
    1.- Devuelve una lista de objetos EmployeeDao que son los objetos de la data. Realmente, representa una fila de la tabla.
    2.- Como EmployeeDao, hereda de IntEntity(id), indicando que la clave primera será entera y de nombre id,
    IntEntity ya incorpora metodos de acceso a los datos. Dicha clase, proviene de Exposed.
    3.- Para cada objeto que representa la fila, lo mapeamos a objeto del modelo de negocio a partir de una función de mapeo.
     */

    override suspend fun getAllEmployee(): List<Employee> {
        return suspendTransaction {
            EmployeeDao.all().map(::EmployeeDaoToEmployee)
        }
    }


    /*
    getEmployeeBySalary:
    1.- Devuelve una lista de EmployeeDao representados por filas, en los que pasamos una condición de búsqueda.
    2.- La condición de búsqueda, representada por medio del método find, acepta como lambda la lógica para comparar
    aquellos registros en los que el campo salario sobre la tabla de la BBDD, sea igual al que pasamos como argumento.
    3.- Volvemos a mapear a objetos de la lógica de negocio.
    4.- Recordar que al comparar, no lo hacemos sobre el campo de EmployeeDao, sino sobre el campo de la tabla.
     */

    override suspend fun getEmployeeBySalary(salary: Salary): List<Employee> {
        return suspendTransaction {
            EmployeeDao
                .find {
                    EmployeeTable.salary eq salary.toString()  //comparamos sobre el campo de la tabla, no de la entidad.
                }
                .map(::EmployeeDaoToEmployee)
        }
    }


    /*
    getEmployeeByName
    1.- Es exactamente igual que la anterior, solo que comparamos sobre el campo name de la tabla.
     */

    override suspend fun getEmployeeByName(name: String): List<Employee> {
        return suspendTransaction {
            EmployeeDao
                .find {
                    EmployeeTable.name eq name
                }
                .map(::EmployeeDaoToEmployee)
        }
    }


    /*
    getEmployeeByDni()
    1.- Es igual que los dos anteriores, sólo que limitamos a un sólo objeto y en caso
    de que no lo encuentre, devuelva null, porque la función debe devolver un Employee?
     */
    override suspend fun getEmployeeByDni(dni: String): Employee? {
        return suspendTransaction {
            EmployeeDao
                .find {
                    EmployeeTable.dni eq dni
                }
                .limit(1)
                .map(::EmployeeDaoToEmployee)
                .firstOrNull()
        }
    }



    override suspend fun postEmployee(employee: Employee): Boolean {
        val em = getEmployeeByDni(employee.dni)
        return if (em == null) {
            suspendTransaction {
                EmployeeDao.new {
                    this.name = employee.name
                    this.dni = employee.dni
                    this.password = PasswordHash.hash(employee.password) //hasheo la password.
                    this.description = employee.description
                    this.salary = employee.salary.toString()
                    this.phone = employee.phone
                    this.urlImage = employee.urlImage
                    this.isActive = employee.disponible
                    this.token = employee.token
                }
            }
            true
        } else
            false
    }



    override suspend fun updateEmployee(employee: UpdateEmployee, dni: String): Boolean {
        var num = 0
        try {
            suspendTransaction {
                num = EmployeeTable
                    .update({ EmployeeTable.dni eq dni }) { stm ->
                        employee.name?.let { stm[name] = it }
                        employee.salary?.let { stm[salary] = it.toString() }
                        employee.phone?.let { stm[phone] = it }
                        employee.urlImage?.let { stm[urlImage] = it }
                        employee.token?.let { stm[token] = it }
                        employee.description?.let { stm[description] = it }
                        employee.disponible?.let { stm[disponible] = it }

                    }
            }

        }catch (e:Exception){
            e.printStackTrace()
            false
        }
        return num == 1
    }


    override suspend fun deleteEmployee(dni: String): Boolean = suspendTransaction {
        val num = EmployeeTable
            .deleteWhere { EmployeeTable.dni eq dni }
        num == 1
    }

    //método que a partir de
    override suspend fun login(dni: String, pass: String): Boolean {
        val employee = getEmployeeByDni(dni)?: return false

        return try{
            val posibleHash = PasswordHash.hash(pass) //hasheo la password del logueo
            posibleHash == employee.password //compruebo con la que hay en la BBDD
        }catch (e: Exception){
            println("Error en la autenticación: ${e.localizedMessage}")
            false
        }
    }

    /*
    Cambiaremos después algunas cosas en la bBDD


     */
    override suspend fun register(employee: UpdateEmployee): Employee? {
        // val em = getEmployeeByDni(employee.dni!!)?:return null

        return try {
            suspendTransaction {
                EmployeeDao.new {
                    this.name = employee.name!! //es seguro.
                    this.dni = employee.dni!!   //es seguro.
                    this.password = PasswordHash.hash(employee.password!!) //hasheo la password.
                    this.description = employee.description!!
                    this.salary = employee.salary!!.toString()
                    this.phone = employee.phone!!
                    this.urlImage = employee.urlImage!!
                    this.isActive = employee.disponible!!
                    this.token = employee.token!!
                }
            }.let {
                EmployeeDaoToEmployee(it) //hago directamente el mapping.
            }
        }catch (e: Exception){
            println("Error en el registro de empleado: ${e.localizedMessage}")
            null
        }

    }

}