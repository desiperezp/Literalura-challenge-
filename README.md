# Proyecto LiterAlura Challenge

Este es un proyecto de consola desarrollado en Java utilizando Spring Boot y PostgreSQL para interactuar con la API de Gutendex. El objetivo del proyecto es permitir a los usuarios buscar libros por título, listar libros y autores registrados, y realizar consultas basadas en criterios específicos como el idioma del libro o el año en que los autores estaban vivos.

 📖 Tabla de Contenidos
- [Descripción](#descripción)
- [Funcionalidades](#funcionalidades)
- [Requisitos](#requisitos)

## Descripción

El proyecto LiterAlura Challenge es una aplicación de consola que permite interactuar con la API de Gutendex para buscar información sobre libros y autores. Utiliza Spring Boot para la configuración del servidor y PostgreSQL para la persistencia de datos.

## Funcionalidades

1. **Buscar libro por título**
   - Permite al usuario buscar un libro por su título.
   - Si el libro ya está registrado en la base de datos, muestra la información del libro.
   - Si el libro no está registrado, busca la información en la API de Gutendex, la guarda en la base de datos y muestra la información del libro.

2. **Listar libros registrados**
   - Muestra una lista de todos los libros registrados en la base de datos.

3. **Listar autores registrados**
   - Muestra una lista de todos los autores registrados en la base de datos junto con sus libros asociados.

4. **Listar autores vivos en un determinado año**
   - Permite al usuario ingresar un año y muestra una lista de autores que estaban vivos en ese año.

5. **Listar libros por idioma**
   - Permite al usuario ingresar un idioma (es, en, fr, pt) y muestra una lista de libros registrados en ese idioma.

## Requisitos

- Java 17 o superior
- Maven 4 o superior
- PostgreSQL 16 o superior
- IntelliJ IDEA (opcional, recomendado para desarrollo)
