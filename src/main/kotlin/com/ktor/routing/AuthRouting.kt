package com.ktor.routing

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
                    val token = login!!.token
                    call.respondText(token!!)
                }
                else
                    call.respond(HttpStatusCode.Unauthorized, "Usuario incorrecto")
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
                val register = ProviderUseCase.register(user)

                if (register != null)
                    call.respond(HttpStatusCode.Created, "Se ha insertado correctamente con dni =  ${register.dni}")
                else
                    call.respond(HttpStatusCode.Conflict, "No se ha podido realizar el registro")

            } catch (e : IllegalStateException){
                call.respond(HttpStatusCode.BadRequest, "Error en el formato de envío de datos o lectura del cuerpo.")
            } catch (e: JsonConvertException){
                call.respond(HttpStatusCode.BadRequest," Problemas en la conversión json")
            }

        } //fin post

    }

}