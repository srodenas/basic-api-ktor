package com.ktor.routing

import com.ktor.ApplicationContext
import com.ktor.validateToken
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.imgRouting(){
    route("/images/{dni}/{image}") {
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
                val img = File(path, nameImage)  //Ya tengo la imagen
                if (!img.exists()){
                    return@get call.respond(HttpStatusCode.BadRequest, "Imagen no encontrada")
                }
                /*
                La imagen existe y por tanto,
                tengo que devolverle la url de dicha imagen.
                 */
                call.respondFile(img)  //mandamos la imagen completa.

                /*
                Esto es estupendo, porque Glide, hace la solicitud http y recibe el binario.
                El mismo, lo convierte a bitMap para mostrarlo en una view.

                En la misma solicitud http de Glide, hay que añadirle un header con el token y para ello se debe
                especificar que meterá una cabecera personalizada, con el token. Pongo código para que no se me olvide.

                val token =  "Bearer"+ token

Glide.with(context)
    .load(GlideUrl("http://ip/images/$dni/$imageName", LazyHeaders.Builder()
        .addHeader("Authorization", token)      //tenemos que incluir el token en el header.
        .build()))
    .into(imageView) // Renderiza la imagen en el ImageView
      */

            }
        }
    }
}