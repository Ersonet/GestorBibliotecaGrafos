
package com.mycompany.gestorbiblioteca;


public class NodoUsuario {
    public Usuario usuario;
    public NodoUsuario izquierdo;
    public NodoUsuario derecho;

    public NodoUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.izquierdo = null;
        this.derecho = null;
    }
}