package com.LiterAlura.repositorios;

import com.LiterAlura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloIgnoreCase(String titulo);
    long countByIdiomaIgnoreCase(String idioma);

}
