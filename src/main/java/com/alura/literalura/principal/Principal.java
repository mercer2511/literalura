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
                case 3 -> libroService.mostrarAutoresRegistrados();
                case 4 -> listarAutoresVivosEnFecha();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void listarAutoresVivosEnFecha() {
        System.out.print("Introduce el año: ");
        if (teclado.hasNextInt()) {
            int fecha = teclado.nextInt();
            teclado.nextLine();
            libroService.mostrarAutoresVivosEnFecha(fecha);
        } else {
            System.out.println("Año inválido.");
            teclado.nextLine();
        }
    }

    private void listarLibrosPorIdioma() {
        var idiomas = libroService.obtenerIdiomasDisponibles();
        if (idiomas.isEmpty()) {
            System.out.println("No hay idiomas registrados.");
            return;
        }
        System.out.println("Idiomas disponibles:");
        for (int i = 0; i < idiomas.size(); i++) {
            System.out.println((i + 1) + " - " + idiomas.get(i).getNombre());
        }
        System.out.print("Seleccione un idioma: ");
        if (teclado.hasNextInt()) {
            int opcion = teclado.nextInt();
            teclado.nextLine();
            if (opcion < 1 || opcion > idiomas.size()) {
                System.out.println("Opción inválida.");
                return;
            }
            var idiomaSeleccionado = idiomas.get(opcion - 1);
            libroService.mostrarLibrosPorIdioma(idiomaSeleccionado.getCodigo());
        } else {
            System.out.println("Opción inválida.");
            teclado.nextLine();
        }
    }
}
