package com.modelo;

public class Autor {

    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    public Autor(String nombre, Integer fechaNacimiento, Integer fechaFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public String getNombre() { return nombre; }
    public Integer getFechaNacimiento() { return fechaNacimiento; }
    public Integer getFechaFallecimiento() { return fechaFallecimiento; }

    public boolean estaVivoEnAnio(int ano) {
        if (fechaNacimiento == null) return false;

        if (fechaFallecimiento == null) {
            return ano >= fechaNacimiento;
        }

        return ano >= fechaNacimiento && ano <= fechaFallecimiento;
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

