package com.ktor

import io.ktor.server.application.*

/*
1.- EngineMain.main(args) le indica a ktor que cargue la configuración application.conf, pero antes arranca el servidor Netty.
2.- Se buscará automaticamente el ichero application.conf ó application.yaml por orden.
3.- Se lee la configuración y se establece qué modulos se deberán cargar.
4.- kotlin, utiliza la reflexion para buscar la función module() en tiempo de ejecución y llamarla sin tener que hacerse
de forma explícita.
5.- Recordamos, que en cada fichero del tipo Database.kt, Routing.kt, etc., debemos añadir una fucnión de extensión
asociada a Application y como MyApplication.module, tambíen define una funsión de extensión a Application, es por lo que
podemos llamar a los métodos de configure..., ya que es como si estuviéramos en el this de Application (mismo contexto)
 */

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

/*
ktor trabaja con la clase interna Application que es la que representa el contexto de la aplicación ktor.
1.-Application es el contenedor de la aplicacíón (ciclo de vida). Ktor lo maneja automaticamente.
2.-Proporciona métodos para las sesiones, authentication, serialización/deserialización automática, entre otras.
 */

fun Application.myModule() {
    configureSerialization()
    configureSecurity()
    configureDatabases()
    configureRouting()
}
