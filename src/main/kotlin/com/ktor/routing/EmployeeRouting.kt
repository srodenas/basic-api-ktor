package com.ktor.routing

import com.domain.models.Employee
import com.domain.models.Salary
import com.domain.models.UpdateEmployee
import com.domain.usecase.ProviderUseCase
import com.ktor.validateToken
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/*
Esta función de extensión, sólo es accesible dentro del contexto de Routing.
Aquí pondremos todas las rutas que tienen que ver con nuestra lógica.
 */
fun Route.employeeRouting(){

    route ("/"){
        get() {
            call.respondText("Hello World!")
        }
    }




    route ("/employee"){

        authenticate("jwt-auth"){ //Todas protegidas
            get(){
                /*
                    En esta ruta, comprobamos diferentes parámetros:
                    1.- Que no tenga ningún parámetro. Devuelve todos los empleados sin filtro.
                    2.- Que le pasemos el dni por query. Devuelve ese empleado . Lo tengo de ejemplo, ya que no debería utilizar una query para un recurso específico.
                    3.- Que le pasemos el salario por query. Devuelve todos los empleados que tienen dicho salario.
                */
                //Authorization: Bearer <token>  es el standar (RFC 6750)
                //Ejemplo Authorization: Bearer 4858803kfgj49r8rrfgar8gfgfig8gudgdfgfdg
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ") //token el header
                val validate = call.validateToken(token!!)  //si llega aqúi, es porque el token se ha verificado antes automaticamente
                if (!validate)
                    return@get  //Ya se ha mandado el responde dentro de la validación

              //Hasta aquí, es que el token es el del usuario de la BBDD.
                val employeeDni = call.request.queryParameters["dni"]
                ProviderUseCase.logger.warn("El Dni tiene de valor $employeeDni")
                if (employeeDni != null) {
                    val employee = ProviderUseCase.getEmployeeByDni(employeeDni)
                    if (employee == null) {
                        call.respond(HttpStatusCode.NotFound, "Empleado no encontrado")
                    } else {
                        call.respond(employee)
                    }
                    return@get
                }

                //comprobamos si hemos pasado el parámetro salary
                val salary = call.request.queryParameters["salary"]
                ProviderUseCase.logger.warn("El salario pasado es $salary")
                if (salary != null) {
                    try{
                        val s = salary.uppercase()
                        ProviderUseCase.logger.warn("salario seleccionado: ${Salary.valueOf(s)}")
                        val employees = ProviderUseCase.getEmployeeBySalary(Salary.valueOf(salary.uppercase()))  //Salary.valueOf(String.upperecase()) convierte un string a Enum
                        call.respond(employees)
                    }catch (e: IllegalArgumentException){
                        call.respond(HttpStatusCode.BadRequest, "El salario no corresponde con los tipos utilizados")
                    }

                }else{ //No hemos pasado ninguna query
                    val employees = ProviderUseCase.getAllEmployees()  //Ya tengo todos los empleados.
                    call.respond(employees)
                }
            }




            get ("{employeeDni"){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ") //token el header
                val validate = call.validateToken(token!!)  //si llega aqúi, es porque el token se ha verificado antes automaticamente
                if (!validate)
                    return@get  //Ya se ha mandado el responde dentro de la validación

                /*
                    Endpoint que no es recomendable, porque no se debe utilizar parámetros de url para filtrar. Para eso están los de consulta.
                */
                //Comprobamos si se ha pasado el dni por parámetro
                val employeeDni = call.parameters["employeeDni"]
                if (employeeDni == null){
                    call.respond(HttpStatusCode.BadRequest, "Debes pasar el dni a buscar") //Montamos una respuesta con código 400.
                    return@get  //finalizamos en endpoint y mandamos inmediantamente la respuesta.
                }

                val employee = ProviderUseCase.getEmployeeByDni(employeeDni)
                if (employee ==null){
                    call.respond(HttpStatusCode.NotFound,"Empleado no encontrado")  //Montamos un 404 de no encontrado.
                    return@get //finalizamos en endpoint y mandamos inmediantamente la respuesta.
                }
                call.respond(employee)  //mandamos el empleado como respuesta al cliente.
            }




            post(){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ") //token el header
                val validate = call.validateToken(token!!)  //si llega aqúi, es porque el token se ha verificado antes automaticamente
                if (!validate)
                    return@post  //Ya se ha mandado el responde dentro de la validación
                try{
                    val emp = call.receive<Employee>()  //Leemos el cuerpo de la solicitud como un objeto Employee
                    val res = ProviderUseCase.insertEmployee(emp)
                    if (! res){
                        call.respond(HttpStatusCode.Conflict, "El empleado no pudo insertarse. Puede que ya exista")
                        return@post //aunque no es necesario, es buena práctica ponerlo para no olvidarlo, pero no hay más lógica.
                    }
                    call.respond(HttpStatusCode.Created, "Se ha insertado correctamente con dni =  ${emp.dni}")
                } catch (e : IllegalStateException){
                    call.respond(HttpStatusCode.BadRequest, "Error en el formato de envío de datos o lectura del cuerpo.")
                } catch (e: JsonConvertException){
                    call.respond(HttpStatusCode.BadRequest," Problemas en la conversión json")
                } catch (e: Exception){
                    call.respond(HttpStatusCode.BadRequest, "Error en los datos. Probablemente falten.")
                }

            }




            patch("{employeeDni}"){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ") //token el header
                val validate = call.validateToken(token!!)  //si llega aqúi, es porque el token se ha verificado antes automaticamente
                if (!validate)
                    return@patch  //Ya se ha mandado el responde dentro de la validación

                try{
                    val dni = call.parameters["employeeDni"]
                    dni?.let{
                        val updateEmployee = call.receive<UpdateEmployee>()
                        val res = ProviderUseCase.updateEmployee(updateEmployee, dni)
                        if (! res){
                            call.respond(HttpStatusCode.Conflict, "El empleado no pudo modificarse. Puede que no exista")
                            return@patch //aunque no es necesario, es buena práctica ponerlo para no olvidarlo, pero no hay más lógica.
                        }
                        call.respond(HttpStatusCode.Created, "Se ha actualizado correctamente con dni =  ${dni}")
                    }?: run{
                        call.respond(HttpStatusCode.BadRequest,"Debes identificar el empleado")
                        return@patch //aunque no es necesario, es buena práctica ponerlo para no olvidarlo, pero no hay más lógica.
                    }
                } catch (e: IllegalStateException){
                    call.respond(HttpStatusCode.BadRequest,"Error en el formado de envío de los datos o lectura del cuerpo.")
                } catch (e: JsonConvertException){
                    call.respond(HttpStatusCode.BadRequest,"Error en el formado de json")
                }
            }


            delete("{employeeDni}"){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ") //token el header
                val validate = call.validateToken(token!!)  //si llega aqúi, es porque el token se ha verificado antes automaticamente
                if (!validate)
                    return@delete  //Ya se ha mandado el responde dentro de la validación

                val dni = call.parameters["employeeDni"]
                ProviderUseCase.logger.warn("Queremos borrar el empleado con dni $dni")
                dni?.let{
                    val res = ProviderUseCase.deleteEmployee(dni)
                    if (! res){
                        call.respond(HttpStatusCode.NotFound,"Empleado no encontrado para borrar")  //Montamos un 404 de no encontrado.
                    }else{
                        call.respond(HttpStatusCode.NoContent,)
                    }
                }?:run{
                    call.respond(HttpStatusCode.NoContent,"Debes identintificar el empleado")
                }
                return@delete
            }

        }

    } //fin route ("/employee")


} //fin función