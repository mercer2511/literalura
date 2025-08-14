package com.alura.literalura.model;

public enum IdiomaEnum {
    EN("en", "Inglés"),
    ES("es", "Español"),
    FR("fr", "Francés");
    // Agrega más idiomas según sea necesario

    private final String codigo;
    private final String nombre;

    IdiomaEnum(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    public static String obtenerNombrePorCodigo(String codigo) {
        for (IdiomaEnum idioma : values()) {
            if (idioma.codigo.equalsIgnoreCase(codigo)) {
                return idioma.nombre;
            }
        }
        return codigo;// Si no encuentra el codigo devuelve el mismo lo que me facilita agregarlo posteriormete al Enum
    }
}
