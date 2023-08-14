package com.microservicio.Procesador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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

        return contenido;

    }

}

