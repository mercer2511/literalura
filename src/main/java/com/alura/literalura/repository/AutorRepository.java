package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNombre(String nombre);
    Optional<Autor> findByNombreAndFechaDeNacimientoAndFechaDeFallecimiento(
            String nombre, Integer fechaDeNacimiento, Integer fechaDeFallecimiento
    );
}
