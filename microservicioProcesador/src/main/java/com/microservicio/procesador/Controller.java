package com.microservicio.Procesador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/procesador")
public class Controller {

    @Autowired
    private Service service;


    @PostMapping("/procesarArchivo")
    public ResponseEntity<String> procesarArchivo(@RequestBody String rutaArchivo) {

        int numeroRegistrosCorrectos=0;
        int cantidadRegistros=0;


        List<String[]> contenido = new ArrayList<>();

        // saber que tipo de archivo es por la extencion, si es csv o txt o xls
        String[] partes = rutaArchivo.split("\\.");
        String extension = partes[partes.length - 1];

        if (extension.equals("csv")) {
            contenido = service.leerArchivoCSV(rutaArchivo);
        }
        else if (extension.equals("txt")) {
        }
//        else if (extension.equals("txt")) {
//            archivo.setTipoArchivo("xls");
//        }
        else {

            // responder con un response entity

            String mensajeError = "Error: El archivo no es de tipo CSV  o XLS";
            return new ResponseEntity<>(mensajeError, HttpStatus.BAD_REQUEST);
        }


        // ? restTemplate para la comunicación con el servicio validador

        RestTemplate restTemplate = new RestTemplate();
        String serviceBUrl = "http://localhost:8090";

        // Crear el cuerpo de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        // ? Recorrer cada registro del archivo y enviarlo al servicio validador

        // iterar por cad reigstro que hay en el contenido
        for (String[] registro : contenido) {
            // devuelve  la representación por defecto del objeto String en Java

            List<String> registroList = new ArrayList<>(); // cada vez que hay un registro se crea una lista que guarda los valores

            // obtener cada elemento de registro y se agrega a una lista que contiene los elementos de registro de una manera que se pueda enviar al servicio validador
            for (String valor : registro) {
                registroList.add(valor);         // registroList devuelve algo asi: [Index, User Id, First Name, Last Name, Sex, Email, Phone, Date of birth, Job Title]

            }

            // ? Enviar el registro al servicio validador

            HttpEntity<List<String>> request = new HttpEntity<>(registroList, headers); // crea una entidad http que contiene el registroList y los headers

            // Enviar la solicitud al servicio validador
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    serviceBUrl + "/validador/validarRegistro",
                    HttpMethod.POST,
                    request,
                    Boolean.class
            );

            // ? Recibir la respuesta del servicio validador
            // Maneja la respuesta
            String responseBody = response.getBody().toString();

            cantidadRegistros += 1;

            //?  si la respuesta es ture se cuenta un registro y si es falso no se cuenta el registro
            if (responseBody.equals("true")) {
                numeroRegistrosCorrectos += 1;
            }

            System.out.println(responseBody);

        }

        String mensajeFinal= "El numero de registros correctos es: " + numeroRegistrosCorrectos + " de " + cantidadRegistros + " registros";

        return new ResponseEntity<>(mensajeFinal, HttpStatus.OK);
    }

}
