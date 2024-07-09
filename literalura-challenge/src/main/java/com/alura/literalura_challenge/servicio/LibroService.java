package com.alura.literalura_challenge.servicio;

import com.alura.literalura_challenge.modelo.Autor;
import com.alura.literalura_challenge.modelo.Libro;
import com.alura.literalura_challenge.repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> buscarLibroPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public List<Autor> listarAutores() {
        // Implementa la lógica para listar autores si es necesario
        return null;
    }

    public List<Autor> listarAutoresVivosEnAno(int ano) {
        // Implementa la lógica para listar autores vivos en un año específico si es necesario
        return null;
    }

    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }
}
