package com.alura.literalura_challenge.controlador;

import com.alura.literalura_challenge.modelo.Libro;
import com.alura.literalura_challenge.servicio.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping
    public Libro guardarLibro(@RequestBody Libro libro) {
        return libroService.guardarLibro(libro);
    }

    @GetMapping
    public List<Libro> obtenerTodosLosLibros() {
        return libroService.listarLibros();
    }

    @GetMapping("/buscar")
    public List<Libro> buscarLibroPorTitulo(@RequestParam String titulo) {
        return libroService.buscarLibroPorTitulo(titulo);
    }

    @GetMapping("/idiomas")
    public List<Libro> listarLibrosPorIdioma(@RequestParam String idioma) {
        return libroService.listarLibrosPorIdioma(idioma);
    }
}
