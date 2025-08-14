package com.alura.literalura.service;

import com.alura.literalura.dto.LibroDTO;
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
    private Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroRepository libroRepository;

    private Optional<DatosLibros> obtenerDatos() {
        var tituloLibro = teclado.nextLine();
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

        // Evitar duplicados de autores (ajusta el método según tus necesidades)
        Set<Autor> autores = libro.getDatosAutor().stream()
                .map(autor -> autorRepository
                        .findByNombreAndFechaDeNacimientoAndFechaDeFallecimiento(
                                autor.getNombre(), autor.getFechaDeNacimiento(), autor.getFechaDeFallecimiento())
                        .orElseGet(() -> autorRepository.save(autor)))
                .collect(Collectors.toSet());
        libro.setDatosAutor(autores);

        libroRepository.save(libro);
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

    public List<LibroDTO> convierteDatos(List<Libro> libro) {
        return libro.stream()
                .map(l -> new LibroDTO(
                        l.getId(),
                        l.getIdGutendex(),
                        l.getTitulo(),
                        l.getNumeroDeDescargas()
                ))
                .collect(Collectors.toList());
    }

    public void mostrarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAllWithAutoresAndIdiomas();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(libro -> {
                System.out.println("-----------LIBRO-----------");
                System.out.println(libro);
                System.out.println("---------------------------");
            });
        }
    }

}
