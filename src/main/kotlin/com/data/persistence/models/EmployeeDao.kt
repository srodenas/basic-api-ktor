package com.data.persistence.models


/*
1.- Esta tabla, le dice a Exposed, como mappear los objetos kotlin/viceversa dentro la BBDD, Es por tanto
la tabla que necesita el ORM.
Cada fila de datos en la tabla, será un objeto de tipo EmployeeDao, por tanto.

2.- EmployeeDao, debe heredar de Entity de Exposed, para que le proporcione las funciones necesarias que necesita,
para interactual con una fila de la BBDD. Por tanto, cada instancia de EmployeeDao, representa un registro o fila en la BBDD.

3.- Entity de Exposed es la base para las entidades mapeadas de la BBDD (fila de la BBDD).

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
3.- Entity<Int> es la superclase que implementa el mapeo objeto-relacional. Esta clase, representará una entidad cuya clave primaria es de tipo Int.

 ¿Qué hace companion object : EntityClass<Int, EmployeDAO>(EmployeeTable)   ?
 1.- Nuestra clase, adquirirá herencia de métodos estáticos a partir de otra clase.
 */
class EmployeeDao {
}