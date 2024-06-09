package com.mycompany.gestorbiblioteca;

import java.util.*;
import java.util.stream.Collectors;

public class GestorBiblioteca {

    private NodoArbol raiz;
    private NodoUsuario raizUsuarios;
    private Grafo grafoUsuariosLibros;

    // Constructor
    public GestorBiblioteca() {
        raiz = null;
        raizUsuarios = null;
        grafoUsuariosLibros = new Grafo();
    }

    //--------------------------Métodos para Libros-----------------------------
    // Método para agregar un libro al árbol
    public void agregarLibro(Libro libro) {
        raiz = agregarLibroRecursivo(raiz, libro);
        grafoUsuariosLibros.agregarNodo("libro_" + libro.getTitulo());
    }

    private NodoArbol agregarLibroRecursivo(NodoArbol nodo, Libro libro) {
        if (nodo == null) {
            return new NodoArbol(libro);
        }

        if (libro.getTitulo().compareToIgnoreCase(nodo.libro.getTitulo()) < 0) {
            nodo.izquierdo = agregarLibroRecursivo(nodo.izquierdo, libro);
        } else if (libro.getTitulo().compareToIgnoreCase(nodo.libro.getTitulo()) > 0) {
            nodo.derecho = agregarLibroRecursivo(nodo.derecho, libro);
        } else {
            System.out.println("El Libro ya existe en el inventario");
        }

        return nodo;
    }

    // Método para buscar un libro por su título en el árbol
    public Libro buscarLibroPorTitulo(String titulo) {
        return buscarLibroPorTituloRecursivo(raiz, titulo);
    }

    private Libro buscarLibroPorTituloRecursivo(NodoArbol nodo, String titulo) {
        if (nodo == null) {
            return null;
        }

        if (titulo.compareToIgnoreCase(nodo.libro.getTitulo()) == 0) {
            return nodo.libro;
        } else if (titulo.compareToIgnoreCase(nodo.libro.getTitulo()) < 0) {
            return buscarLibroPorTituloRecursivo(nodo.izquierdo, titulo);
        } else {
            return buscarLibroPorTituloRecursivo(nodo.derecho, titulo);
        }
    }

    // Método para prestar un libro a un usuario utilizando el árbol binario de búsqueda
    public boolean pedirLibro(int idUsuario, String tituloLibro) {
        // Buscar el usuario por su ID
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            System.out.println("El usuario no está registrado en la biblioteca.");
            return false;
        }

        // Buscar el libro por su título
        Libro libro = buscarLibroPorTitulo(tituloLibro);
        if (libro == null) {
            System.out.println("El libro no está disponible o no se encuentra en la biblioteca.");
            return false;
        }

        // Verificar si hay ejemplares disponibles del libro
        if (libro.getCantidad() > 0) {
            // Decrementar la cantidad de ejemplares disponibles del libro
            libro.setCantidad(libro.getCantidad() - 1);
            // Registrar el préstamo en el historial del usuario (opcional)
            usuario.agregarLibroPrestado(tituloLibro);
            grafoUsuariosLibros.agregarArista("usuario_" + idUsuario, "libro_" + tituloLibro, 1.0);
            System.out.println("Libro prestado correctamente.");
            return true;
        } else {
            System.out.println("El libro no está disponible en este momento.");
            return false;
        }
    }

    // Método para devolver un libro
    public boolean devolverLibro(int idUsuario, String tituloLibro) {
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            System.out.println("El usuario no está registrado en la biblioteca.");
            return false;
        }

        Libro libro = buscarLibroPorTitulo(tituloLibro);
        if (libro != null) {
            libro.setCantidad(libro.getCantidad() + 1);
            grafoUsuariosLibros.agregarArista("usuario_" + idUsuario, "libro_" + tituloLibro, -1.0);
            return true;
        } else {
            return false; // Libro no encontrado
        }
    }

    //Método eliminar libro
    public boolean eliminarLibro(String tituloLibro) {
        return eliminarLibroRecursivo(raiz, tituloLibro) != null;
    }

    private NodoArbol eliminarLibroRecursivo(NodoArbol nodo, String tituloLibro) {
        if (nodo == null) {
            return null;
        }

        if (tituloLibro.compareToIgnoreCase(nodo.libro.getTitulo()) < 0) {
            nodo.izquierdo = eliminarLibroRecursivo(nodo.izquierdo, tituloLibro);
        } else if (tituloLibro.compareToIgnoreCase(nodo.libro.getTitulo()) > 0) {
            nodo.derecho = eliminarLibroRecursivo(nodo.derecho, tituloLibro);
        } else {
            // Caso 1: Nodo sin hijos
            if (nodo.izquierdo == null && nodo.derecho == null) {
                return null;
            }
            // Caso 2: Nodo con un hijo
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            }
            if (nodo.derecho == null) {
                return nodo.izquierdo;
            }
            // Caso 3: Nodo con dos hijos
            NodoArbol sucesor = encontrarSucesor(nodo.derecho);
            nodo.libro = sucesor.libro;
            nodo.derecho = eliminarLibroRecursivo(nodo.derecho, sucesor.libro.getTitulo());
        }

        return nodo;
    }

    private NodoArbol encontrarSucesor(NodoArbol nodo) {
        NodoArbol actual = nodo;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }

    //Método editar libro
    public boolean editarLibro(String tituloLibro, Libro nuevoLibro) {
        NodoArbol nodoLibro = buscarNodoLibroPorTitulo(raiz, tituloLibro);
        if (nodoLibro != null) {
            nodoLibro.libro.setTitulo(nuevoLibro.getTitulo());
            nodoLibro.libro.setAutor(nuevoLibro.getAutor());
            nodoLibro.libro.setCodigo(nuevoLibro.getCodigo());
            nodoLibro.libro.setCantidad(nuevoLibro.getCantidad());
            return true;
        }
        return false;
    }

    private NodoArbol buscarNodoLibroPorTitulo(NodoArbol nodo, String titulo) {
        if (nodo == null) {
            return null;
        }

        if (titulo.compareToIgnoreCase(nodo.libro.getTitulo()) == 0) {
            return nodo;
        } else if (titulo.compareToIgnoreCase(nodo.libro.getTitulo()) < 0) {
            return buscarNodoLibroPorTitulo(nodo.izquierdo, titulo);
        } else {
            return buscarNodoLibroPorTitulo(nodo.derecho, titulo);
        }
    }



    //---------------------Métodos para Usuarios-------------------------------

    // Método para registrar un usuario en el árbol de usuarios
    public void registrarUsuario(Usuario usuario) {
        raizUsuarios = insertarUsuario(raizUsuarios, usuario);
        grafoUsuariosLibros.agregarNodo("usuario_" + usuario.getId());
    }

    private NodoUsuario insertarUsuario(NodoUsuario nodo, Usuario usuario) {
        if (nodo == null) {
            return new NodoUsuario(usuario);
        }

        if (usuario.getId() < nodo.usuario.getId()) {
            nodo.izquierdo = insertarUsuario(nodo.izquierdo, usuario);
        } else if (usuario.getId() > nodo.usuario.getId()) {
            nodo.derecho = insertarUsuario(nodo.derecho, usuario);
        } else {
            System.out.println("El usuario ya está registrado en la biblioteca.");
        }

        return nodo;
    }

    // Método para buscar un usuario por su ID en el árbol de usuarios
    public Usuario buscarUsuarioPorId(int id) {
        return buscarUsuarioPorIdRecursivo(raizUsuarios, id);
    }

    private Usuario buscarUsuarioPorIdRecursivo(NodoUsuario nodo, int id) {
        if (nodo == null) {
            return null;
        }

        if (id == nodo.usuario.getId()) {
            return nodo.usuario;
        } else if (id < nodo.usuario.getId()) {
            return buscarUsuarioPorIdRecursivo(nodo.izquierdo, id);
        } else {
            return buscarUsuarioPorIdRecursivo(nodo.derecho, id);
        }
    }

    public boolean eliminarUsuarioPorId(int idUsuario) {
        raizUsuarios = eliminarUsuarioPorIdRecursivo(raizUsuarios, idUsuario);
        return true; // Opcional: podrías devolver un booleano para indicar si se eliminó o no el usuario
    }

    private NodoUsuario eliminarUsuarioPorIdRecursivo(NodoUsuario nodo, int idUsuario) {
        if (nodo == null) {
            return null;
        }

        if (idUsuario < nodo.usuario.getId()) {
            nodo.izquierdo = eliminarUsuarioPorIdRecursivo(nodo.izquierdo, idUsuario);
        } else if (idUsuario > nodo.usuario.getId()) {
            nodo.derecho = eliminarUsuarioPorIdRecursivo(nodo.derecho, idUsuario);
        } else {
            // Caso 1: Nodo sin hijos o con un solo hijo
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            } else if (nodo.derecho == null) {
                return nodo.izquierdo;
            }

            // Caso 2: Nodo con dos hijos
            NodoUsuario sucesor = encontrarMinimo(nodo.derecho);
            nodo.usuario = sucesor.usuario;
            nodo.derecho = eliminarUsuarioPorIdRecursivo(nodo.derecho, sucesor.usuario.getId());
        }

        return nodo;
    }

    private NodoUsuario encontrarMinimo(NodoUsuario nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo;
    }

    // Método para editar usuario por ID
    public boolean editarUsuarioPorId(int idUsuario, Usuario nuevoUsuario) {
        NodoUsuario nodoUsuario = buscarNodoUsuarioPorId(raizUsuarios, idUsuario);
        if (nodoUsuario != null) {
            nodoUsuario.usuario.setNombre(nuevoUsuario.getNombre());
            // Aquí podrías modificar otros atributos del usuario si fuera necesario
            return true;
        } else {
            return false; // Usuario no encontrado
        }
    }

    private NodoUsuario buscarNodoUsuarioPorId(NodoUsuario nodo, int idUsuario) {
        if (nodo == null) {
            return null;
        }

        if (idUsuario == nodo.usuario.getId()) {
            return nodo;
        } else if (idUsuario < nodo.usuario.getId()) {
            return buscarNodoUsuarioPorId(nodo.izquierdo, idUsuario);
        } else {
            return buscarNodoUsuarioPorId(nodo.derecho, idUsuario);
        }
    }


    //-------------Método para recomendar libros-----------//

    // Método para recomendar libros basados en préstamos similares
    public List<String> recomendarLibros(int idUsuario) {
        List<String> recomendaciones = new ArrayList<>();
        NodoGrafo nodoUsuario = grafoUsuariosLibros.nodos.get("usuario_" + idUsuario);

        if (nodoUsuario != null) {
            Set<String> librosPrestados = new HashSet<>();
            for (Arista arista : nodoUsuario.adyacentes) {
                librosPrestados.add(arista.destino.id);
            }

            Map<String, Integer> libroFrecuencia = new HashMap<>();
            for (String libroPrestado : librosPrestados) {
                NodoGrafo nodoLibro = grafoUsuariosLibros.nodos.get(libroPrestado);
                if (nodoLibro != null) {
                    for (Arista arista : nodoLibro.adyacentes) {
                        if (!arista.destino.id.startsWith("usuario_")) continue;
                        for (Arista aristaUsuario : arista.destino.adyacentes) {
                            if (!librosPrestados.contains(aristaUsuario.destino.id)) {
                                libroFrecuencia.put(aristaUsuario.destino.id, libroFrecuencia.getOrDefault(aristaUsuario.destino.id, 0) + 1);
                            }
                        }
                    }
                }
            }

            recomendaciones = libroFrecuencia.entrySet().stream()
                    .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }

        return recomendaciones;
    }
}
