package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "idiomas")
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String codigo;
    @Column(unique = true)
    private String nombre;

    @ManyToMany(mappedBy = "idiomas")
    private Set<Libro> libros;

    public Idioma() { }

    public Idioma(String codigo) {
        this.codigo = codigo;
        this.nombre = IdiomaEnum.obtenerNombrePorCodigo(codigo);
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Idioma idioma = (Idioma) o;
        return idioma != null && codigo.equals(idioma.codigo);
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }
}
