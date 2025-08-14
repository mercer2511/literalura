package com.alura.literalura.repository;

import com.alura.literalura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
    Optional<Idioma> findByCodigo(String codigo);
}
