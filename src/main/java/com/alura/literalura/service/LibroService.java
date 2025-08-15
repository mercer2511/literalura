package com.alura.literalura.service;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.IdiomaRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LibroService {
    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroRepository libroRepository;

    private Optional<DatosLibros> obtenerDatos() {
        var tituloLibro = teclado.nextLine();
        String URL_BASE = "https://gutendex.com/books/";
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        return datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
    }

    public void guardarLibro(Libro libro) {
        // Evitar duplicados de idiomas
        Set<Idioma> idiomas = libro.getIdiomas().stream()
                .map(idioma -> idiomaRepository.findByCodigo(idioma.getCodigo())
                        .orElseGet(() -> idiomaRepository.save(idioma)))
                .collect(Collectors.toSet());
        libro.setIdiomas(idiomas);
        // Evitar duplicados de autores
        Set<Autor> autores = libro.getDatosAutor().stream()
                .map(autor -> autorRepository
                        .findByNombreAndFechaDeNacimientoAndFechaDeFallecimiento(
                                autor.getNombre(), autor.getFechaDeNacimiento(), autor.getFechaDeFallecimiento())
                        .orElseGet(() -> autorRepository.save(autor)))
                .collect(Collectors.toSet());
        libro.setDatosAutor(autores);

        libroRepository.save(libro);
    }

    private void imprimirLibros(List<Libro> libros, String mensajeVacio) {
        if (libros.isEmpty()) {
            System.out.println(mensajeVacio);
        } else {
            libros.forEach(libro -> {
                System.out.println("-----------LIBRO-----------");
                System.out.println(libro);
                System.out.println("---------------------------");
            });
        }
    }

    public void buscarLibroPorTitulo() {
        System.out.println("Ingresa el título del libro a buscar:");
        Optional<DatosLibros> datos = obtenerDatos();
        if (datos.isPresent()) {
            Libro libro = new Libro(datos.get());
            System.out.println("-----------LIBRO-----------");
            System.out.println(libro);
            System.out.println("---------------------------");
            guardarLibro(libro);
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    public void mostrarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAllWithAutoresAndIdiomas();
        imprimirLibros(libros, "No hay libros registrados.");
    }


    private void imprimirAutores(List<Autor> autores, String mensajeVacio) {
        if (autores.isEmpty()) {
            System.out.println(mensajeVacio);
        } else {
            autores.forEach(autor -> {
                System.out.println("---------------------------");
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Fecha de Nacimiento: " + autor.getFechaDeNacimiento());
                System.out.println("Fecha de Fallecimiento: " + autor.getFechaDeFallecimiento());
                System.out.println("Libros:");
                if (autor.getLibros().isEmpty()) {
                    System.out.println("  Ninguno");
                } else {
                    autor.getLibros().forEach(libro ->
                            System.out.println("  - " + libro.getTitulo())
                    );
                }
                System.out.println("---------------------------");
            });
        }
    }

    public void mostrarAutoresRegistrados() {
        var autores = autorRepository.findAllWithLibros();
        imprimirAutores(autores, "No hay autores registrados.");
    }

    public void mostrarAutoresVivosEnFecha(Integer fecha) {
        var autores = autorRepository.findAutoresVivosEnFecha(fecha);
        imprimirAutores(autores, "No hay autores vivos en el año " + fecha + ".");
    }

    public List<Idioma> obtenerIdiomasDisponibles() {
        return idiomaRepository.findAll();
    }

    public void mostrarLibrosPorIdioma(String codigo) {
        var idioma = idiomaRepository.findByCodigo(codigo);
        var libros = libroRepository.encontrarLibrosPorIdioma(codigo);
        System.out.println("Libros en " + idioma.get().getNombre() + ":");
        imprimirLibros(libros, "No hay libros en " + idioma.get().getNombre() + ".");

    }
}
