package com.alura.literalura_challenge.servicio;

import com.alura.literalura_challenge.modelo.Autor;
import com.alura.literalura_challenge.repositorio.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public Autor guardarAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    public Autor buscarPorNombre(String nombre) {
        return autorRepository.findByNombre(nombre);
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutoresVivosEnAno(int ano) {
        List<Autor> autores = autorRepository.findByAnoNacimientoBeforeAndAnoFallecimientoAfterOrAnoFallecimientoIsNull(ano, ano);
        return autores != null ? autores : List.of();
    }
}
