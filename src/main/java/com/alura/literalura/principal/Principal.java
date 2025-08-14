package com.alura.literalura.principal;

import com.alura.literalura.service.LibroService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private final LibroService libroService;

    public Principal(LibroService libroService) {
        this.libroService = libroService;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ---------------------------
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                                                      
                    0 - Salir
                    """;
            System.out.println(menu);
            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                teclado.nextLine();
            } else {
                System.out.println("Opción inválida");
                teclado.nextLine();
                continue;
            }

            switch (opcion) {
                case 1 -> libroService.buscarLibroPorTitulo();
                case 2 -> libroService.mostrarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosAño();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void listarAutoresRegistrados() {
        System.out.println("Funcionalidad no implementada aún.");
    }

    private void listarAutoresVivosAño() {
        System.out.println("Funcionalidad no implementada aún.");
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Funcionalidad no implementada aún.");
    }
}
