package com.microservicio.Procesador;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import java.io.*;
import java.util.Scanner;



@org.springframework.stereotype.Service
public class Service {

    public List<String> leerArchivoTXT(String urlArchivo) {

         List<String> contenido = new ArrayList<>();

            try {
                File archivo = new File(urlArchivo); //  crea un objeto que representa el archivo local en la ruta especificada
                BufferedReader lector = new BufferedReader(new FileReader(archivo)); // crea un objeto que permite leer el archivo

                String linea; // variable que almacena cada linea del archivo
                while ((linea = lector.readLine()) != null) { // lee cada linea del archivo  hasta que no haya mas lineas
                    contenido.add(linea); // agrega la linea leida a la lista
                }

                lector.close(); // cierra el objeto que permite leer el archivo

            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
                e.printStackTrace();
                // Manejo de error en caso de no poder leer el archivo
            }

            return contenido;
        }

    public List<String[]> leerArchivoCSV(String rutaArchivo) {

        List<String[]> contenido = new ArrayList<>();

        try {

            File archivo = new File(rutaArchivo);
            Scanner lector = new Scanner(archivo);
            lector.useDelimiter(",|\n");

            while (lector.hasNext()) {
                String[] fila = lector.nextLine().split(",");
                contenido.add(fila);
            }

            lector.close();

        } catch (IOException e) {

            System.out.println("Error al leer el archivo: " + e.getMessage());
            e.printStackTrace();
            // Manejo de error en caso de no poder leer el archivo

        }

        // devuelve una lista de arreglos de string donde cada arreglo es una fila del archivo
        return contenido;

    }

    // leer archivo xlsx

    public List<String[]> leerArchivoXLSX(String rutaArchivo) {
        List<String[]> contenido = new ArrayList<>();

        try {
            FileInputStream archivo = new FileInputStream(new File(rutaArchivo)); //  crea un objeto que representa el archivo local en la ruta especificada
            XSSFWorkbook libro = new XSSFWorkbook(archivo);   // Crear un objeto XSSFWorkbook a partir del archivo
            XSSFSheet hoja = libro.getSheetAt(0);      // Obtener la primera hoja del libro

            Iterator<Row> filas = hoja.iterator();   // Crear un iterador para recorrer todas las filas de la hoja
            while (filas.hasNext()) {
                Row fila = filas.next();             // Obtener la siguiente fila
                Iterator<Cell> celdas = fila.iterator();   // Crear un iterador para recorrer todas las celdas de la fila
                List<String> registro = new ArrayList<>();
                while (celdas.hasNext()) {
                    Cell celda = celdas.next();
                    String valorCelda = "";

                    switch (celda.getCellType()) {
                        case STRING:
                            valorCelda = celda.getStringCellValue();
                            break;
                        case NUMERIC:
                            valorCelda = String.valueOf(celda.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            valorCelda = String.valueOf(celda.getBooleanCellValue());
                            break;
                        default:
                            break;
                    }

                    registro.add(valorCelda);
                }

                contenido.add(registro.toArray(new String[0]));
            }

            libro.close();
            archivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // devuelve una lista de arreglos de string donde cada arreglo es una fila del archivo
        return contenido;
    }


    // Devuelve una lista con el numero de registros correctos y la cantidad de registros
    public List<Integer> restTemplate(List<String[]> contenido,String endPoint) {

        List<Integer> respuestaValidacion = new ArrayList<>();

        int numeroRegistrosCorrectos=0;
        int cantidadRegistros=0;

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

            // ? Enviar el registro al servicio validador por cada registro

            HttpEntity<List<String>> request = new HttpEntity<>(registroList, headers); // crea una entidad http que contiene el registroList y los headers

            // Enviar la solicitud al servicio validador
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    serviceBUrl + endPoint,
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
        }

        // ?  se crea una lista que contiene el numero de registros correctos y la cantidad de registros
        respuestaValidacion.add(cantidadRegistros);
        respuestaValidacion.add(numeroRegistrosCorrectos);

        return respuestaValidacion;
    }
}





