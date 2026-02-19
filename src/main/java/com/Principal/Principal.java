package com.Principal;

import com.API.*;
import com.modelo.Autor;
import com.modelo.Libro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner lectura = new Scanner(System.in);
    private ApiComunicador comunicador = new ApiComunicador();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";
    private List<Libro> librosBuscados = new ArrayList<>();
    private List<Autor> autoresRegistrados = new ArrayList<>();


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar libros por idioma
                    4 - Listar autores
                    5 - Listar autores vivos en determinado año
                    
                    0 - Salir
                    """;
            System.out.println(menu);

            // Validación de entrada numérica
            try {
                opcion = lectura.nextInt();
                lectura.nextLine(); // Limpiar el buffer

                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        listarLibros();
                        break;
                    case 3:
                        listarPorIdioma();
                        break;
                    case 4:
                        listarAutores();
                        break;
                    case 5:
                        listarAutoresVivosEnAno();
                        break;

                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                System.out.println("Error: Por favor, ingresa un número válido.");
                lectura.nextLine(); // Limpiar el buffer en caso de error
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = lectura.nextLine();

        var json = comunicador.comunicarConAPI(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datos = conversor.obtenerDatos(json, Datos.class);

        if (datos.resultados().isEmpty()) {
            System.out.println("Libro no encontrado.");
        } else {

            DatosLibro datosLibro = datos.resultados().get(0);

            // ===== AUTOR =====
            Autor autorObj = null;
            String nombreAutor = "Autor desconocido";

            if (!datosLibro.autores().isEmpty()) {

                DatosAutor datosAutor = datosLibro.autores().get(0);

                nombreAutor = datosAutor.nombre();

                autorObj = new Autor(
                        datosAutor.nombre(),
                        datosAutor.fechaDeNacimiento(),
                        datosAutor.fechaDeFallecimiento()
                );

                // Evitar duplicados
                boolean existe = autoresRegistrados.stream()
                        .anyMatch(a -> a.getNombre().equalsIgnoreCase(datosAutor.nombre()));


                if (!existe) {
                    autoresRegistrados.add(autorObj);
                }
            }

            // ===== IDIOMA =====
            String idioma = "Desconocido";

            if (!datosLibro.idiomas().isEmpty()) {
                idioma = datosLibro.idiomas().get(0);
            }

            // ===== CREAR LIBRO =====
            Libro libro = new Libro(
                    datosLibro.titulo(),
                    nombreAutor,
                    idioma,
                    datosLibro.numeroDeDescargas()
            );

            librosBuscados.add(libro);

            System.out.println("Libro guardado:");
            System.out.println(libro);
        }
    }

    private void listarLibros() {
        if (librosBuscados.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            librosBuscados.forEach(System.out::println);
        }
    }

    private void listarPorIdioma() {
        System.out.println("Ingrese el idioma (ej: en, es, fr):");
        String idioma = lectura.nextLine();

        librosBuscados.stream()
                .filter(l -> l.getIdioma().equalsIgnoreCase(idioma))
                .forEach(System.out::println);
    }

    private void listarAutores() {
        if (autoresRegistrados.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autoresRegistrados.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnAno() {
        System.out.println("Ingrese el año que desea consultar:");
        int ano = lectura.nextInt();
        lectura.nextLine();

        autoresRegistrados.stream()
                .filter(a -> a.estaVivoEnAnio(ano))
                .forEach(System.out::println);
    }



}