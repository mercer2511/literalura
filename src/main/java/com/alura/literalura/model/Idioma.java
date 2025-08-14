package com.alura.literalura.model;

public class Idioma {
    private String codigo;
    private String nombre;

    public Idioma(String codigo) {
        this.codigo = codigo;
        this.nombre = IdiomaEnum.obtenerNombrePorCodigo(codigo);
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
}
