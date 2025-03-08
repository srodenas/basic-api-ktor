package com.ktor.routing

import com.domain.mapper.toEmployee
import com.domain.mapper.toUpdateEmployee
import com.domain.models.Employee
import com.domain.models.UpdateEmployee
import com.domain.usecase.ProviderUseCase
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/*
Contexto para las rutas de authenticación.
Esta función de extensión, sólo es accesible dentro del contexto de Routing.
Aquí pondremos todas las rutas que tienen que ver con la authenticación, como el
login, registro.
 */
fun Route.authRouting(){

    //Para el login
    route("/auth"){

        post(){
            try{
                val loginRequest = call.receive<UpdateEmployee>()
                val login : Employee? = ProviderUseCase.login(loginRequest.dni, loginRequest.password)  //caso de uso del login

                if (login != null) {
                    val emp = login.toUpdateEmployee()  //lo mapeamos a UpdateEmployee, para actualizar el token.
                    emp.token = login!!.token   //seteamos el nuevo token
                   // val emp = UpdateEmployee(token =login!!.token )  //vacío.
                    emp.msg = "Usuario logueado correctamente"
                    call.respond(HttpStatusCode.OK, emp)
                }
                else
                    call.respond(HttpStatusCode.Unauthorized, "Problema de autenticación")

            }catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest, "Formato de solicitud incorrecto")
                return@post
            }
        } //fin post

    }

    route ("/register"){

        post(){
            try{
                val user = call.receive<UpdateEmployee>()
                ProviderUseCase.logger.warn("Auth-registro: Recibo request.")

                val register = ProviderUseCase.register(user)  //devuelvo un Employee registrado o null sino.

                if (register != null) {
                    ProviderUseCase.logger.warn("Auth-registro: Registra Ok.")
                    val upEmp = register.toUpdateEmployee()  //mapeamos a updateEmployee para la respuesta.
                    upEmp.msg = "Usuario con dni =  ${upEmp.dni}, registrado correctamente. Vuelva a loguearse"
                    call.respond(HttpStatusCode.Created, upEmp)
                }
                else
                    call.respond(HttpStatusCode.Conflict, "No se ha podido realizar el registro")

            } catch (e : IllegalStateException){
                call.respond(HttpStatusCode.BadRequest, "Error en el formato de envío de datos o lectura del cuerpo.")
                e.printStackTrace()
            } catch (e: JsonConvertException){
                call.respond(HttpStatusCode.BadRequest," Problemas en la conversión json")
            }

        } //fin post

    }

}