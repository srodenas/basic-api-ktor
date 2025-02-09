package com.ktor
import com.domain.security.JwtConfig
import com.domain.usecase.ProviderUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


/*
función de extensión, que configura la utilización de autenticación por jwt.
 */
fun Application.configureSecurity(){

    install(Authentication ){
        jwt("jwt-auth") {
            JwtConfig.configureAuthentication(this) //configuración automática.
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
} //fin función extensión de configurar jwt

/*
Método que llamaremos por cada enpoint protegido
1.- this, es el contexto de las solicitudes HTTP que recibe el servidor.
2.- en esa solicitud, sacamos la información del payload.
3.- principal<JWTPrincipal> es una función de extensión de ApplicationCall. Devuelve
los datos del usuario que hemos pasado en el token.
 */
suspend fun ApplicationCall.validateToken(token: String): Boolean{
    val dataUser = this.principal<JWTPrincipal>()  //Recuperamos el usuario autenticado.
    val dni = dataUser?.payload?.getClaim("dni")?.asString()

    val user = ProviderUseCase.getEmployeeByDni(dni!!)
    if (user == null || token != user.token){
        //El usuario que hay en el token, no existe en la BBDD
        this.respond(HttpStatusCode.Unauthorized, "Token inválido o usuario No disponible")
        return false //El token no coincide con el de la BBDD.
    }else
        return true  //El token es el mismo que el del usuario


}