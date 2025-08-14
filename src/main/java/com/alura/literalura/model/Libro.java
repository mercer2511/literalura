package com.alura.literalura.model;

import java.util.List;
import java.util.stream.Collectors;

public class Libro {
    private String titulo;
    private List<Autor> datosAutor;
    private List<Idioma> idiomas;
    private Integer numeroDeDescargas;


    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.datosAutor = datosLibros.datosAutor().stream()
                .map(d -> new Autor(
                        d.nombre(),
                        d.anioDeNacimiento(),
                        d.anioDeFallecimiento()
                ))
                .collect(Collectors.toList());
        this.idiomas = datosLibros.idiomas().stream()
                .map(Idioma::new)
                .collect(Collectors.toList());
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getDatosAutor() {
        return datosAutor;
    }

    public void setDatosAutor(List<Autor> datosAutor) {
        this.datosAutor = datosAutor;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        String autores = String.join("& ", datosAutor.stream().map(Autor::toString).toList());
        String idiomasStr = String.join(", ", idiomas.stream().map(Idioma::getNombre).toList());
        return "Título: " + titulo + "\n" +
                "Autor: " + autores + "\n" +
                "Idioma: " + idiomasStr + "\n" +
                "Número de descargas: " + numeroDeDescargas + "\n";
    }
}
