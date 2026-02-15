package com.data.persistence.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID


/*
1.- Esta tabla, le dice a Exposed, como mappear los objetos kotlin/viceversa dentro la BBDD, Es por tanto
la tabla que necesita el ORM.

*****EmployeeDao representa una fila o registro de la tabla (una entidad)*******

2.- Entity de Exposed es la base para las entidades mapeadas de la BBDD (fila de la BBDD).

4.- EmployeeDao(id: EntityID<Int>)  --> el parámetro id del constructor de tipo EntityID<Int>, es un tipo especial de Exposed que
encapsula el identificador de la entidad. Este identificador se usa para recuperar, modificar y guardar la fila en la BBDD. En otras
palabaras, id representa un objeto que hace de identificador único (clave primaria) de una fila en la BBDD y es de tipo entero.

4.- Nuetra clase debe heredar de Entity, que representa la fila de la BBDD y para ello, necesita saber el tipo de la clave primaria, que
en nuestro caso es de tipo Entero,  por ello debe heredar de Entity<Int>. Exposed necesita saber como asociar una fila de la tabla de la
bbdd con un objeto kotlin. Esto se hace a través de la herencia de Entity y el uso de EntityID. Esto permite, que exposed pueda mapear
correctamente las filas de la tabla a objetos kotlin y gestionar la clave primra de manera eficiente.

5.- Heredar de Entity<Int>, nos permite métodos para manipular una fila específica de la tabla, pero no ofrece métodos para realizar consultas
globales a la tabla, como buscar o filtrar por condiciones. EntityClass, srá la encargada de poder realizar ese tipo de operaciones.

Lo volveré a resumir:
1.- ORM y Exposed: Permite trabajar con filas como si fueran objetos kotlin.
2.- id:EntityID<Int>, encapsula el valor de la clave primaria de tipo entero llamado id y se asocia a una fila específica.
3.- EntityID<Int> es la superclase que implementa el mapeo objeto-relacional. Esta clase, representará una entidad cuya clave primaria es de tipo Int.

 ¿Qué hace    ----companion object : IntEntityClass<EmployeeDao>(EmployeeTable)----?
     1.- Al meter un companion object dentro de una clase, estamos incorporando métodos estáticos
     2.- Esta forma de heredar de IntEntityClass..., es la forma de que adquiera nuestro EmployeeDao de
     métodos estáticos heredados, que serán:
         - findById(), find {}, new {}, all()

     3.- El companion, será un singleton y nos viene a representar los métodos a trabajar con la tabla EmployeeTable

 Objeto EmployeDato --> Exposed--> Registro en la Tabla
 Objeto EmployeeDato <-- Exposed <-- Registtro en la Tabla

 Parecido a Hibernate:
 No hacemos EmployeeTable.select, sino:

-------------------------------------------------------------------
 EmployeeDao.findById(1) --> devuelve un objeto por id.
 EmployeeDao.find {..} --> devuelve un objeto filtrado.
 EmployeeDao.new {..}  --> Inserta un objeto.
 EmployeeDao.all () --> devuelve todos los objetos.
 */
class EmployeeDao (id : EntityID<Int>) :  IntEntity(id){
    companion object : IntEntityClass<EmployeeDao>(EmployeeTable) //heredamos todos los métodos estáticos de acceso a los datos de la BBDD

    var name by EmployeeTable.name //Lo necesitamos para mapear con el atributo correspondiente de la tabla
    var dni by EmployeeTable.dni
    var password by EmployeeTable.password
    var description by EmployeeTable.description
    var salary by EmployeeTable.salary
    var phone by EmployeeTable.phone
    var urlImage by EmployeeTable.urlImage
    var isActive by EmployeeTable.disponible
    var token by EmployeeTable.token

}