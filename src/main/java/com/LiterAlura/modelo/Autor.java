package com.LiterAlura.modelo;


import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    // 🔹 Constructor vacío obligatorio para JPA
    public Autor() {}

    public Autor(String nombre, Integer fechaNacimiento, Integer fechaFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }
    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public boolean estaVivoEnAno(int ano) {

        // Si no tiene fecha de nacimiento pero tampoco fallecimiento,
        // asumimos que sigue activo
        if (fechaNacimiento == null && fechaFallecimiento == null) {
            return true;
        }

        if (fechaNacimiento != null && fechaNacimiento > ano) {
            return false;
        }

        if (fechaFallecimiento == null) {
            return true;
        }

        return fechaFallecimiento >= ano;
    }

    @Override
    public String toString() {
        return """
                -----------------------
                Autor: %s
                Nacimiento: %s
                Fallecimiento: %s
                -----------------------
                """.formatted(
                nombre,
                fechaNacimiento != null ? fechaNacimiento : "Desconocido",
                fechaFallecimiento != null ? fechaFallecimiento : "Sigue vivo"
        );
    }
}

