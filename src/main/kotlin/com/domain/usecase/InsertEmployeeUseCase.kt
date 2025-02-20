package com.domain.usecase

import com.domain.models.Employee
import com.domain.repository.EmployeeInterface
import kotlinx.coroutines.CoroutineStart
import kotlin.io.encoding.Base64

class InsertEmployeeUseCase  (val repository : EmployeeInterface){

    var employee : Employee? = null

    //todo. Recibo los datos del employee. Saco el fichero, si lo tiene.
    //todo. Decodifico la imagen y persisto en Employee
    //todo. Si la imagen, ya existe hay que sobreescribirla.
    //todo. debo mandar en new.urlImage, la url completa a partir de pathUrl
    suspend operator fun invoke() : Employee ? {
        /*
        Si devuelve null, es que ya existe el empleado
         */
        val em = repository.getEmployeeByDni(employee!!.dni)
        return if (em!=null)    null
        else{
            val img = employee!!.urlImage
            img?.let{
                employee!!.urlImage = reateBase64ToImg(it)  //creamos la imagen, a partir del Base64 y devolvemos su http
            }
            //aquí tengo que tener la imagen creada y el name en employee!!.urlImage
            val new = repository.postEmployee(employee!!)
            return new
        }


    }


    /*
    Esta función, recibe el base64 u otra cosa.
     */
    private fun createBase64ToImg(img: String) : String?{
        /*
        primer grupo. sacamos el tipo de la imagen.
        segiundo grupo. sacamos el cuerpo.
         */
        val regex = "data:(image/[^;]+);base64,(.+)".toRegex()  //expresión regular. dos grupos.
        val result = regex.find(img)

        return if (result != null) {
                    //todo ya tengo que crear la imagen
                    val type = result.groupValues[1]
                    val body = result.groupValues[2]
                    try {
                        val imgBytes = Base64.decode(img, CoroutineStart.DEFAULT)

                    }catch (e : Exception) {
                        e.printStackTrace()
                    }
                    null

                }else null

    }

}