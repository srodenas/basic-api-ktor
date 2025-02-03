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


    routing {
        authenticate("jwt-auth") {
            get("/protected") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.getClaim("username", String::class)
                call.respondText("Hello, $username! You are authenticated.")
            }
        }
    }
}