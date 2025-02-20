package com.domain.usecase

import com.domain.models.Employee
import com.domain.repository.EmployeeInterface
import com.ktor.ApplicationContext
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.imageio.ImageIO


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
                employee!!.urlImage = createBase64ToImg(it, employee!!.dni)  //creamos la imagen, a partir del Base64 y devolvemos su http
            }
            //aquí tengo que tener la imagen creada y el name en employee!!.urlImage
            val new = repository.postEmployee(employee!!)
            return new
        }


    }


    /*
    Esta función, recibe el base64 u otra cosa.
     */
    private fun createBase64ToImg(img: String , dni: String) : String?{
        /*
        primer grupo. sacamos el tipo de la imagen.
        segiundo grupo. sacamos el cuerpo.
         */
        val regex = "data:(image/[^;]+);base64,(.+)".toRegex()  //expresión regular. dos grupos.
        val result = regex.find(img)

        return if (result != null) {
                    //todo ya tengo que crear la imagen
                    val type = result.groupValues[1]
                    val ext: String = type.split("/")[1]  //nos quedamos sólo con la extensión.
                    val body = result.groupValues[2]
                    if (ext != "jpg" || ext != "jpeg" || ext != "gif")
                        return null
                    try {
                        val imgBytes = Base64.getDecoder().decode(img)  //decodificamos y convertimos a ByteArray
                        val inputStream = ByteArrayInputStream(imgBytes) //Convertimos el array de bytes a flujo de datos
                        val bufferImage:BufferedImage = ImageIO.read(inputStream)  //A partir del flujo de entrada (imagen en bytes), convertimos a imagen.
                        val path : String = ApplicationContext.context.environment.config.property("ktor.path.images") + "/{dni}"
                        val dir = File(path)
                        if (dir.isDirectory){
                            val nameFile: String = path+"/{$dni}_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}.$ext"
                            //ya podemos crear el fichero.
                            ImageIO.write(bufferImage, ext, nameFile)
                        }else{
                            return null
                        }
                    }catch (e : Exception) {
                        e.printStackTrace()
                    }
                    null

                }else null

    }

}