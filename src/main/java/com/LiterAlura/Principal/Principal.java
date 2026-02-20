package com.LiterAlura.Principal;

import com.LiterAlura.API.*;
import com.LiterAlura.modelo.Autor;
import com.LiterAlura.modelo.Libro;
import com.LiterAlura.repositorios.AutorRepository;
import com.LiterAlura.repositorios.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class Principal implements CommandLineRunner {
    private Scanner lectura = new Scanner(System.in);
    private ApiComunicador comunicador = new ApiComunicador();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

    //USO DE LISTAS
    //private List<Libro> librosBuscados = new ArrayList<>();
    //private List<Autor> autoresRegistrados = new ArrayList<>();

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public void run(String... args) {
        muestraElMenu();
    }

    public void muestraElMenu() {
        int opcion = -1;

        while (opcion != 0) {

            System.out.println("""
                1 - Buscar libro por título
                2 - Listar libros registrados
                3 - Listar libros por idioma
                4 - Listar autores
                5 - Listar autores vivos en determinado año
                6 - Mostrar estadísticas por idioma

                0 - Salir
                """);

            try {
                opcion = Integer.parseInt(lectura.nextLine());

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
                    case 6:
                        mostrarEstadisticasPorIdioma();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingresa un número válido.");
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

            // ===== VALIDAR SI EL LIBRO YA EXISTE =====
            var libroExistente = libroRepository
                    .findByTituloIgnoreCase(datosLibro.titulo());

            if (libroExistente.isPresent()) {
                System.out.println("El libro ya está registrado en la base de datos.");
                return;
            }

            // ===== AUTOR =====
            Autor autor = null;

            if (!datosLibro.autores().isEmpty()) {

                DatosAutor datosAutor = datosLibro.autores().get(0);

                // 🔎 Buscar si el autor ya existe en la base
                autor = autorRepository.findAll()
                        .stream()
                        .filter(a -> a.getNombre().equalsIgnoreCase(datosAutor.nombre()))
                        .findFirst()
                        .orElse(null);

                // Si no existe, lo creamos
                if (autor == null) {
                    autor = new Autor(
                            datosAutor.nombre(),
                            datosAutor.fechaDeNacimiento(),
                            datosAutor.fechaDeFallecimiento()
                    );

                    autorRepository.save(autor);
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
                    idioma,
                    datosLibro.numeroDeDescargas(),
                    autor
            );

            libroRepository.save(libro);

            System.out.println("Libro guardado en base de datos:");
            System.out.println(libro);

        }
    }

    private void listarLibros() {

        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(System.out::println);
        }
    }


    private void listarPorIdioma() {
        System.out.println("Ingrese el idioma (ej: en, es, fr):");
        String idioma = lectura.nextLine();

        List<Libro> libros = libroRepository.findAll();

        libros.stream()
                .filter(l -> l.getIdioma().equalsIgnoreCase(idioma))
                .forEach(System.out::println);
    }


    private void listarAutores() {

        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnAno() {
        System.out.println("Ingrese el año que desea consultar:");

        int ano;
        try {
            ano = Integer.parseInt(lectura.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Año inválido.");
            return;
        }

        List<Autor> autores = autorRepository.findAll();

        autores.stream()
                .filter(a -> a.estaVivoEnAno(ano))
                .forEach(System.out::println);
    }

    private void mostrarEstadisticasPorIdioma() {

        System.out.println("Ingrese idioma:");
        String idioma = lectura.nextLine();

        long cantidad = libroRepository.countByIdiomaIgnoreCase(idioma);

        System.out.println("Cantidad de libros en \"" + idioma + "\": " + cantidad);
    }
}