package com.alura.literalura_challenge.controlador;

import com.alura.literalura_challenge.modelo.Autor;
import com.alura.literalura_challenge.servicio.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {
    @Autowired
    private AutorService autorService;

    @GetMapping
    public List<Autor> listarAutores() {
        return autorService.listarAutores();
    }

    @GetMapping("/vivos/{ano}")
    public List<Autor> listarAutoresVivosEnAno(@PathVariable Integer ano) {
        return autorService.listarAutoresVivosEnAno(ano);
    }
}
