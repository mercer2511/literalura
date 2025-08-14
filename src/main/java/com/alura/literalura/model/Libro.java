package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Integer idGutendex;
    private String titulo;
    private Double numeroDeDescargas;

    // No son campos en la tabla solo sirven para relacionar objetos
    @ManyToMany
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> datosAutor;
    @ManyToMany
    @JoinTable(
            name = "libro_idioma",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "idioma_id")
    )
    private Set<Idioma> idiomas;

    public Libro() { }


    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.idGutendex = datosLibros.idGutendex();
        this.datosAutor = datosLibros.datosAutor().stream()
                .map(d -> new Autor(
                        d.nombre(),
                        d.anioDeNacimiento(),
                        d.anioDeFallecimiento()
                ))
                .collect(Collectors.toSet());
        this.idiomas = datosLibros.idiomas().stream()
                .map(Idioma::new)
                .collect(Collectors.toSet());
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    public Long getId() {
        return id;
    }

    public Integer getIdGutendex() {
        return idGutendex;
    }

    private void setIdGutendex(Integer idGutendex) {
        this.idGutendex = idGutendex;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Autor> getDatosAutor() {
        return datosAutor;
    }

    public void agregarAutor(Autor autor) {
        this.datosAutor.add(autor);
    }

    public void eliminarAutor(Autor autor) {
        this.datosAutor.remove(autor);
    }

    public void setDatosAutor(Set<Autor> datosAutor) {
        this.datosAutor = datosAutor;
    }

    public Set<Idioma> getIdiomas() {
        return idiomas;
    }

    public void agregarIdioma(Idioma idioma) {
        this.idiomas.add(idioma);
    }

    public void eliminarIdioma(Idioma idioma) {
        this.idiomas.remove(idioma);
    }

    public void setIdiomas(Set<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
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
