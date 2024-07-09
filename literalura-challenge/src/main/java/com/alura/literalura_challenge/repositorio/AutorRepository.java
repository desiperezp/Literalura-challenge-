package com.alura.literalura_challenge.repositorio;

import com.alura.literalura_challenge.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre(String nombre);
    List<Autor> findByAnoNacimientoBeforeAndAnoFallecimientoAfterOrAnoFallecimientoIsNull(int anoNacimiento, int anoFallecimiento);
}
