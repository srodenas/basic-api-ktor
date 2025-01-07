package com.domain.usecase

import com.data.repository.MemoryEmployeeRepository
import com.srodenas.data.models.Employee
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ProviderUseCase {

    private val repository = MemoryEmployeeRepository()  //me creo el repositorio con todos los datos. Lo hago una sóla vez.
    val logger: Logger = LoggerFactory.getLogger("EmployeeUseCaseLogger")

    //Aquí tengo todos los casos de uso.
    private val getAllEmployeesUseCase = GetAllEmployees(repository)
    private val getEmployeByDniUseCase = GetEmployeByDni(repository)
    private val updateEmployeeUseCase = UpdateEmployee(repository)
    private val insertEmployeeUseCase = InsertEmployee(repository)
    private val deleteEmployeUseCase = DeleteEmploye(repository)


    fun getAllEmployees() = getAllEmployeesUseCase()  //Lo invoco, como si fuera una función.



    fun getEmployeeByDni(dni : String) : Employee ? {
        if (dni.isNullOrBlank()){
            logger.warn("El dni está vacío. No podemos buscar un empleado")
            return null
        }
        getEmployeByDniUseCase.dni = dni
        val emp = getEmployeByDniUseCase()
        return if (emp == null) {
            logger.warn("No se ha encontrado un empleado con ese $dni.")
            null
        }else{
            emp
        }
    }



    fun insertEmployee(employee: Employee?) : Boolean{
        if (employee == null){
            logger.warn( "No existen datos del empleado a insertar")
            return false
        }
        insertEmployeeUseCase.employee = employee
        val res = insertEmployeeUseCase()
            return if (!res){
            logger.warn("No se ha insertado el empleado. Posiblemente ya exista")
            false
        }else{
            true
        }
    }

}