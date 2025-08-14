package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(
        name = "autores",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"nombre", "fechaDeNacimiento", "fechaDeFallecimiento"})
        }
)
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;

    @ManyToMany(mappedBy = "datosAutor")
    private Set<Libro> libros;


    public Autor() { }

    public Autor(String nombre, Integer fechaDeNacimiento, Integer fechaDeFallecimiento) {
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer anioDeNacimiento) {
        this.fechaDeNacimiento = anioDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer anioDeFallecimiento) {
        this.fechaDeFallecimiento = anioDeFallecimiento;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return nombre != null && nombre.equals(autor.nombre)
                && ((fechaDeNacimiento == null && autor.fechaDeNacimiento == null) || (fechaDeNacimiento != null && fechaDeNacimiento.equals(autor.fechaDeNacimiento)))
                && ((fechaDeFallecimiento == null && autor.fechaDeFallecimiento == null) || (fechaDeFallecimiento != null && fechaDeFallecimiento.equals(autor.fechaDeFallecimiento)));
    }

    @Override
    public int hashCode() {
        int result = nombre != null ? nombre.hashCode() : 0;
        result = 31 * result + (fechaDeNacimiento != null ? fechaDeNacimiento.hashCode() : 0);
        result = 31 * result + (fechaDeFallecimiento != null ? fechaDeFallecimiento.hashCode() : 0);
        return result;
    }
}
