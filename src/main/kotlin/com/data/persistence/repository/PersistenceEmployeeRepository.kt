package com.data.persistence.repository

import com.data.persistence.models.EmployeeDao
import com.data.persistence.models.EmployeeTable
import com.data.persistence.models.suspendTransaction
import com.domain.mapping.EmployeeDaoToEmployee
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

        return if (getEmployeeByDni(employee.dni) != null) {
            suspendTransaction {
                EmployeeDao.new {
                    this.name = employee.name
                    this.dni = employee.dni
                    this.salary = employee.salary.toString()
                    this.phone = employee.phone
                    this.urlImage = employee.urlImage
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
            num = EmployeeTable
                .update({ EmployeeTable.dni eq dni }) { stm ->
                    employee.name?.let { stm[EmployeeTable.name] = it }
                    employee.salary?.let { stm[EmployeeTable.salary] = it.toString() }
                    employee.phone?.let { stm[EmployeeTable.phone] = it }
                    employee.urlImage?.let { stm[EmployeeTable.urlImage] = it }
                    employee.token?.let { stm[EmployeeTable.token] = it }
                    employee.description?.let { stm[EmployeeTable.description] = it }
                    employee.disponible?.let { stm[EmployeeTable.disponible] = it }

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

}