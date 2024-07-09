package com.alura.literalura_challenge.principal;

import com.alura.literalura_challenge.servicio.ConsumoAPI;
import com.alura.literalura_challenge.modelo.Libro;
import com.alura.literalura_challenge.modelo.Autor;
import com.alura.literalura_challenge.servicio.LibroService;
import com.alura.literalura_challenge.servicio.AutorService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private ConsumoAPI consumoApi;

    private Scanner scanner = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/";

    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("----------------------------");
            System.out.println("Elija la opción a través de su número:");
            System.out.println("1- buscar libro por título");
            System.out.println("2- listar libros registrados");
            System.out.println("3- listar autores registrados");
            System.out.println("4- listar autores vivos en un determinado año");
            System.out.println("5- listar libros por idioma");
            System.out.println("0- salir");
            System.out.print("Opción: ");
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
            } else {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.nextLine(); // Limpiar la entrada no válida
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnAno();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.print("Ingrese el título del libro que desea buscar: ");
        String titulo = scanner.nextLine();
        List<Libro> librosExistentes = libroService.buscarLibroPorTitulo(titulo);

        if (!librosExistentes.isEmpty()) {
            System.out.println("El libro ya está registrado en la base de datos:");
            librosExistentes.forEach(libro -> {
                System.out.println("----- LIBRO -----");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                System.out.println("-----------------");
            });
            return;
        }

        String url = URL_BASE + "?search=" + titulo;
        // System.out.println("URL de solicitud: " + url);  // Paso de depuración
        var json = consumoApi.obtenerDatos(url);
        // System.out.println("Respuesta JSON de la API: " + json);  // Paso de depuración
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode resultsNode = jsonNode.get("results");
            if (resultsNode == null || !resultsNode.isArray() || resultsNode.size() == 0) {
                System.out.println("No se encontraron libros con ese título.");
                return;
            }

            JsonNode bookNode = resultsNode.get(0);
            Libro libro = new Libro();
            libro.setTitulo(bookNode.get("title").asText());
            libro.setIdioma(bookNode.get("languages").get(0).asText()); // Consideramos el primer idioma
            libro.setNumeroDescargas(bookNode.get("download_count").asInt());

            JsonNode authorNode = bookNode.get("authors").get(0); // Consideramos el primer autor
            Autor autor = new Autor();
            autor.setNombre(authorNode.get("name").asText());
            autor.setAnoNacimiento(authorNode.has("birth_year") && !authorNode.get("birth_year").isNull() ? authorNode.get("birth_year").asInt() : null);
            autor.setAnoFallecimiento(authorNode.has("death_year") && !authorNode.get("death_year").isNull() ? authorNode.get("death_year").asInt() : null);

            // Verificamos si el autor ya existe en la base de datos
            Autor autorExistente = autorService.buscarPorNombre(autor.getNombre());
            if (autorExistente == null) {
                autorExistente = autorService.guardarAutor(autor);
            }
            libro.setAutor(autorExistente);

            libroService.guardarLibro(libro);
            System.out.println("Libro guardado: ");
            System.out.println("----- LIBRO -----");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Número de descargas: " + libro.getNumeroDescargas());
            System.out.println("-----------------");
        } catch (Exception e) {
            System.err.println("Error al procesar los datos: " + e.getMessage());
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroService.listarLibros();
        libros.forEach(libro -> {
            System.out.println("----- LIBRO -----");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Número de descargas: " + libro.getNumeroDescargas());
            System.out.println("-----------------");
        });
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorService.listarAutores();
        autores.forEach(autor -> {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getAnoNacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.getAnoFallecimiento());
            System.out.println("Libros: " + (autor.getLibros() != null ? autor.getLibros().stream().map(Libro::getTitulo).toList() : "No tiene libros registrados"));
            System.out.println("-----------------");
        });
    }

    private void listarAutoresVivosEnAno() {
        System.out.print("Ingrese el año: ");
        int ano = scanner.nextInt();
        List<Autor> autores = autorService.listarAutoresVivosEnAno(ano);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + ano);
        } else {
            autores.forEach(autor -> {
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getAnoNacimiento());
                System.out.println("Fecha de fallecimiento: " + autor.getAnoFallecimiento());
                System.out.println("Libros: " + (autor.getLibros() != null ? autor.getLibros().stream().map(Libro::getTitulo).toList() : "No tiene libros registrados"));
                System.out.println("-----------------");
            });
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma para buscar los libros:");
        System.out.println("es- español");
        System.out.println("en- inglés");
        System.out.println("fr- francés");
        System.out.println("pt- portugués");
        String idioma = scanner.nextLine().trim(); // Consumir la entrada y eliminar espacios en blanco
        List<Libro> libros = libroService.listarLibrosPorIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma " + idioma);
        } else {
            libros.forEach(libro -> {
                System.out.println("----- LIBRO -----");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                System.out.println("-----------------");
            });
        }
        System.out.println(); // Agregar una línea en blanco antes de mostrar el menú nuevamente
    }
}