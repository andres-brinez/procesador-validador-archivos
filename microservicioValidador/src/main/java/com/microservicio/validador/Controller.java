package com.microservicio.validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/validador")
public class Controller {

    @Autowired
    Service service;

    @PostMapping("/validarRegistroCSV")
    public Boolean validarRegistro(@RequestBody List<String> registro) {

        return service.validarRegistro(registro);
    }
}
