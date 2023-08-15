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
        String endPoint;

        List<String[]> contenido = new ArrayList<>();

        // saber que tipo de archivo es por la extencion, si es csv o txt o xls
        String[] partes = rutaArchivo.split("\\.");
        String extension = partes[partes.length - 1];

        //? Verificar extención del archivo
        if (extension.equals("csv")) {
            contenido = service.leerArchivoCSV(rutaArchivo);
            endPoint = "/validador/validarRegistroCSV";
        }
        else if (extension.equals("xlsx")) {
            contenido = service.leerArchivoXLSX(rutaArchivo);
            endPoint = "/validador/validarRegistroXLSX";
        }
//        else if (extension.equals("txt")) {
//            archivo.setTipoArchivo("xls");
//        }
        else {

            // responder con un response entity
            String mensajeError = "Error: El archivo no es de tipo CSV  o XLS";
            return new ResponseEntity<>(mensajeError, HttpStatus.BAD_REQUEST);
        }

        // Validar si hubo un error porque no existe el archivo
        if (contenido == null) {
            String mensajeError = "Error: El archivo no existe";
            return new ResponseEntity<>(mensajeError, HttpStatus.BAD_REQUEST);
        }

        // Enviar los registros al servicio que lo envia por medio de resTemplate al microseServicio validador
        List<Integer> informacionRegistros = new ArrayList<>();
        informacionRegistros=service.restTemplate(contenido, endPoint);

        // Obtener la respuesta del servicio validador
        cantidadRegistros= informacionRegistros.get(0);
        numeroRegistrosCorrectos= informacionRegistros.get(1);


        String mensajeFinal= "El numero de registros correctos es: " + numeroRegistrosCorrectos + " de " + cantidadRegistros + " registros";
        return new ResponseEntity<>(mensajeFinal, HttpStatus.OK);

    }

}
