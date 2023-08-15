package com.microservicio.validador;
import java.time.LocalDate; // Importación de LocalDate
import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    public Boolean validarRegistroCSV(List<String> registro) {

        Boolean respuesta= true;

        //? Saltar el primer registro que es el que tiene la cabacera (header)del archivo y no se le necesit hacer validación
        if (registro.get(0).equals("Index")) {

            System.out.println("Cabecera del archivo");
            respuesta= false;

        }
        else{

            // Estructura registro [Index, User Id, First Name, Last Name, Sex, Email, Phone, Date of birth, Job Title]

            String email = registro.get(5);
            String date = registro.get(7);
            String jobTitle = registro.get(8);
            String[] jobTitles = {"Haematologist", "Phytotherapist", "Building surveyor", "Insurance account manager", "Educational psychologist"};
            LocalDate dateFormat = LocalDate.parse(date); // pasar el string a formato date

            // Validar email que tenga la estructura correcta
            if (!email.matches("^[A-Za-z0-9]+@(.+)$")) {

                System.out.println("ID: " + registro.get(0) + " |  Email no valido " + email );
                respuesta= false;

            }
            // Validar Date of Birth que sea mayor a 1980
            else if(dateFormat.isBefore(LocalDate.of(1980, 1, 1))) {

                System.out.println("ID: " + registro.get(0) + " |  Fecha no valida " + date );
                return false;
            }

            boolean isValidJobTitle = false;
            for (String title : jobTitles) { // recorrer el arreglo de jobTitles
                if (title.equals(jobTitle)) { // si el jobTitle es igual a alguno de los jobTitles del arreglo
                    isValidJobTitle = true;
                    break;
                }
                respuesta= isValidJobTitle;
            }

        }

        return respuesta;
    }

    public Boolean validarRegistroXLSX(List<String> registro) {

        Boolean respuesta= true;
        String[] reportTypes = {"Near Miss", "Lost Time", "First Aid"};

        String injuryLocation=registro.get(1);
        String reportType=registro.get(7);

        if (injuryLocation.equals("N/A")) {
            respuesta = false;
            System.out.println("Injury Location no valida " + injuryLocation);
        }

        boolean isValidReport = false;
        for (String report : reportTypes) { // recorrer el arreglo
            if (reportType.equals(report)) { // si el reportType es igual a alguno de los reportTypes del arreglo
                isValidReport = true;
                System.out.println("Report Type valido " + reportType);
                break;
            }

            respuesta= isValidReport;
        }

        return respuesta;

    }
}
