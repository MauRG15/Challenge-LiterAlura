package com.modelo;

public class Libro {

    private String titulo;
    private String autor;
    private String idioma;
    private Integer numeroDescargas;

    public Libro(String titulo, String autor, String idioma, Integer numeroDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getIdioma() { return idioma; }
    public Integer getNumeroDescargas() { return numeroDescargas; }

    @Override
    public String toString() {
        return """
                -----------------------
                Título: %s
                Autor: %s
                Idioma: %s
                Descargas: %d
                -----------------------
                """.formatted(titulo, autor, idioma, numeroDescargas);
    }
}

