package com.domain.usecase

import com.domain.infraestructure.Utils
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

//todo
//método que sólo debería de implementar el administrador.

class InsertEmployeeUseCase  (val repository : EmployeeInterface){

    var employee : Employee? = null

    suspend operator fun invoke() : Employee ? {
        /*
        Si devuelve null, es que ya existe el empleado
         */
        val em = repository.getEmployeeByDni(employee!!.dni)
        return if (em!=null)    null
        else{
            val isCreateDir = Utils.createDir(employee!!.dni)  //creamos su directorio
            if (isCreateDir){
                val img = employee!!.urlImage
                if (!img.isNullOrBlank()){  //Si tiene imagen, hay que crearla.
                    employee!!.urlImage = Utils.createBase64ToImg(img, employee!!.dni)  //creamos la imagen, a partir del Base64 y devolvemos su http
                }
            }else{
                throw IllegalStateException("No se pudo crear el directorio del empleado. Puede que ya exista")
            }

            val new = repository.postEmployee(employee!!)  //insertamos el employee

            new?.let{  emp->
                     if (!emp.urlImage.isNullOrBlank())   { //Debemos setear la url correctamente.
                         val local = ApplicationContext.context.environment.config.property("ktor.urlPath.baseUrl").getString()
                         val relativePath = ApplicationContext.context.environment.config.property("ktor.urlPath.images").getString()
                         new.urlImage = "$local/$relativePath/${new?.dni}/${emp.urlImage}"
                     }
            }

            return new
        }


    }


    /*
    Esta función, recibe el base64 u otra cosa.
     */
    /*private fun createBase64ToImg(img: String , dni: String) : String?{
        val groupExtension = listOf("jpg", "jpeg", "gif")
        /*
        primer grupo. sacamos el tipo de la imagen.
        segiundo grupo. sacamos el cuerpo.
         */
        val regex = "data:(image/[^;]+);base64,(.+)".toRegex()  //expresión regular. dos grupos.
        val result = regex.find(img)

        return if (result != null) {
                    //todo ya tengo que crear la imagen
                    val type = result.groupValues[1]
                    var ext: String = type.split("/")[1]  //nos quedamos sólo con la extensión.
                    val body = result.groupValues[2]
                    if (ext !in groupExtension)
                        return null
                    try {
                        if (ext =="jpg")
                            ext = "jpeg"//porque ImageIO.write, en caso de jpg, falla.

                        val imgBytes = Base64.getDecoder().decode(body)  //decodificamos y convertimos a ByteArray
                        val inputStream = ByteArrayInputStream(imgBytes) //Convertimos el array de bytes a flujo de datos
                        val bufferImage:BufferedImage = ImageIO.read(inputStream)  //A partir del flujo de entrada (imagen en bytes), convertimos a imagen.
                        val path : String = ApplicationContext.context.environment.config.property("ktor.path.images").getString() + "/$dni"
                        val dir = File(path)
                        if (dir.isDirectory){
                            val nameFile: String = path+"/"+dni+"_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}.$ext"
                            val fileImag = File(nameFile)  //creamos el fichero con el nombre y donde queremos.
                            //ya podemos crear el fichero.
                            ImageIO.write(bufferImage, ext, fileImag)
                            val local = ApplicationContext.context.environment.config.property("ktor.urlPath.baseUrl").getString()
                            val relativePath = ApplicationContext.context.environment.config.property("ktor.path.images").getString()
                            val urlImage = "$local/$relativePath/$nameFile"
                            return urlImage  //se ha creado la imagen y por tanto devolvemos la ubicación y su nombre
                        }else{
                            return null  //no existe el directorio, por tanto al haber un error se devuelve null
                        }
                    }catch (e : Exception) {
                        e.printStackTrace()
                        return null  //ha producido alguna excepción y por tanto devuelve null
                    }
        } else null  // no se ha creado la imagen, por tanto retornamos null

    }
*/
}