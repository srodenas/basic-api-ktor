package com.ktor.routing

import com.ktor.ApplicationContext
import com.ktor.validateToken
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.imgRouting(){
    route("/protected/{dni}/{imageName}") {
        authenticate("jwt-auth") {

            //todo probar código.

            get() {
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ") //token el header
                val validate = call.validateToken(token!!)  //si llega aqúi, es porque el token se ha verificado antes automaticamente
                if (!validate)
                    return@get  //Ya se ha mandado el responde dentro de la validación

                val dni = call.parameters["dni"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Necesitamos el DNI")
                val nameImage = call.parameters["image"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Necesitamos la imagen"
                )
                //ya tengo el dni y el nombre de la imagen. También he validado correctamente el token.
                //Necesito comprobar si existe el fichero y en su caso, devolverlo.
                val path = ApplicationContext.context.environment.config.property("ktor.path.images").getString() + "/$dni"
                val file = File(path, nameImage)  //Ya tengo la imagen
                if (!file.exists()){
                    return@get call.respond(HttpStatusCode.BadRequest, "Imagen no encontrada")
                }
                /*
                La imagen existe y por tanto,
                tengo que devolverle la url de dicha imagen.
                 */


                val pathUrl = ApplicationContext.context.environment.config.property("ktor.pathUrl").getString() + "/$dni" + "/$nameImage"
                call.respondText(pathUrl)  //mandamos el recurso en forma de http
            }
        }
    }
}