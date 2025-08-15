package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.datosAutor LEFT JOIN FETCH l.idiomas")
    List<Libro> findAllWithAutoresAndIdiomas();

    @Query("SELECT DISTINCT l FROM Libro l LEFT JOIN FETCH l.datosAutor LEFT JOIN FETCH l.idiomas i WHERE i.codigo = :codigo")
    List<Libro> encontrarLibrosPorIdioma(String codigo);
}
