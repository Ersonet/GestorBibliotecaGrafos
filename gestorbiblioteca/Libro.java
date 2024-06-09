package com.mycompany.gestorbiblioteca;

public class Libro {
    private String titulo;
    private String autor;
    private int codigo;
    private int cantidad;

    // Constructor
    public Libro(String titulo, String autor, int codigo, int cantidad) {
        this.titulo = titulo;
        this.autor = autor;
        this.codigo = codigo;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
