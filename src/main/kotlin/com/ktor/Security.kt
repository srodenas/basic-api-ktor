package com.ktor
import com.domain.security.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSecurity(){
    install(Authentication ){
        jwt("jwt-auth") {
            JwtConfig.configureAuthentication(this)
        }
    }


    /*
    1.- Todo lo que pongamos en authenticate("jwt-auth"), requieren autenticación, por tanto
    esas rutas están protegidas. La estrategia de autenticación es por jwt-auth. Sólo los usuarios
    autenticados con un jwt valido, podrán acceder a dicha ruta.
    2.- ktor, automáticamente antes de llamar a la ruta, verifica y valida el token.
    3.- Si el token ha sido aceptado, entonces se procede a sacar del payload el username
    y se manda una respuesta al cliente.

     */

        routing {
                authenticate("jwt-auth") {

                /*
                En este punto, ktor ya ha verificado y validado el token. Ha interceptado
                la petición antes de invocar el endpoint.
                 */
                get("/protected") {
                    val principal = call.principal<JWTPrincipal>()  //accedo a la información del token ya validado.
                    val username = principal?.getClaim("username", String::class) //obtenemos del payload el username
                    call.respondText("Hello, $username! You are authenticated.")  //La respuesta.
                }
            }
    }
}