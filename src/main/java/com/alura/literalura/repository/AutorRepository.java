package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNombre(String nombre);

    Optional<Autor> findByNombreAndFechaDeNacimientoAndFechaDeFallecimiento(
            String nombre, Integer fechaDeNacimiento, Integer fechaDeFallecimiento
    );

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllWithLibros();

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros WHERE a.fechaDeNacimiento <= :fecha AND (a.fechaDeFallecimiento IS NULL OR a.fechaDeFallecimiento > :fecha)")
    List<Autor> findAutoresVivosEnFecha(@Param("fecha") Integer fecha);
}
