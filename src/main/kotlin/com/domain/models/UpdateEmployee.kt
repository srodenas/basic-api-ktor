package com.domain.models

import kotlinx.serialization.Serializable

/*
Sólo para serializar en consultas
patch
 */
@Serializable
data class UpdateEmployee (
    val dni: String? = null,
    var password: String? = null,
    val name: String? = null,
    var description: String? = null,
    var salary: Salary? = null,
    var phone: String? = null,
    var urlImage: String ? = null,
    var disponible: Boolean ? = true,
    var token:String ? = null
)