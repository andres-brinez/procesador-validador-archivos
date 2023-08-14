package com.microservicio.validador;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/validador")
public class Controller {

    @PostMapping("/validarRegistro")
    public Boolean validarRegistro(@RequestBody List<String> registro) {

        System.out.println(registro);
        System.out.println("validando registro");
        return true;
    }
}
