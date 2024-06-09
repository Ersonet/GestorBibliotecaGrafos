package com.mycompany.gestorbiblioteca;

import java.util.*;

class NodoGrafo {
    String id;
    List<Arista> adyacentes;

    public NodoGrafo(String id) {
        this.id = id;
        this.adyacentes = new ArrayList<>();
    }

    public void agregarAdyacente(NodoGrafo nodoDestino, double peso) {
        adyacentes.add(new Arista(this, nodoDestino, peso));
    }
}

class Arista {
    NodoGrafo origen;
    NodoGrafo destino;
    double peso;

    public Arista(NodoGrafo origen, NodoGrafo destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }
}

class Grafo {
    Map<String, NodoGrafo> nodos;

    public Grafo() {
        this.nodos = new HashMap<>();
    }

    public void agregarNodo(String id) {
        nodos.putIfAbsent(id, new NodoGrafo(id));
    }

    public void agregarArista(String idOrigen, String idDestino, double peso) {
        NodoGrafo nodoOrigen = nodos.get(idOrigen);
        NodoGrafo nodoDestino = nodos.get(idDestino);
        if (nodoOrigen != null && nodoDestino != null) {
            nodoOrigen.agregarAdyacente(nodoDestino, peso);
        }
    }
}
