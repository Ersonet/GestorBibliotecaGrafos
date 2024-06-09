package com.mycompany.gestorbiblioteca;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre;
    private int id;
    private List<String> librosPrestados;

    // Constructor
    public Usuario(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
        this.librosPrestados = new ArrayList<>();
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Método para agregar un libro prestado
    public void agregarLibroPrestado(String tituloLibro) {
        this.librosPrestados.add(tituloLibro);
    }
}
