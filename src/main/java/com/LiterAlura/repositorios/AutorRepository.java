package com.LiterAlura.repositorios;

import com.LiterAlura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    boolean existsByNombre(String nombre);
}
