package com.alura.literalura.model;

public class Autor {
    private String nombre;
    private Integer anioDeNacimiento;
    private Integer anioDeFallecimiento;


    public Autor() { }

    public Autor(String nombre, Integer anioDeNacimiento, Integer anioDeFallecimiento) {
        this.nombre = nombre;
        this.anioDeNacimiento = anioDeNacimiento;
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioDeNacimiento() {
        return anioDeNacimiento;
    }

    public void setAnioDeNacimiento(Integer anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public Integer getAnioDeFallecimiento() {
        return anioDeFallecimiento;
    }

    public void setAnioDeFallecimiento(Integer anioDeFallecimiento) {
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
