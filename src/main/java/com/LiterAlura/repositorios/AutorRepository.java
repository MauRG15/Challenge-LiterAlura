package com.LiterAlura.repositorios;

import com.LiterAlura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(
            Integer nacimiento, Integer fallecimiento);
    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoIsNull(
            Integer nacimiento);

    // Autores SIN fecha nacimiento pero fallecidos después del año
    List<Autor> findByFechaNacimientoIsNullAndFechaFallecimientoGreaterThanEqual(
            Integer fallecimiento);

    // Autores SIN fecha nacimiento y siguen vivos
    List<Autor> findByFechaNacimientoIsNullAndFechaFallecimientoIsNull();
}
