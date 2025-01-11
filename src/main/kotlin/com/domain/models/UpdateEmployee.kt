package com.domain.models

import kotlinx.serialization.Serializable

/*
Sólo para serializar en consultas
patch
 */
@Serializable
data class UpdateEmployee (
    val dni: Int?,
    val name: String?,
    val description: String?,
    val salary: Salary?
)