package com.mycompany.gestorbiblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuInterfaz extends JFrame {
    private GestorBiblioteca gestor;

    private JButton adminLibrosButton;
    private JButton adminUsuariosButton;
    private JPanel mainPanel;
    private JTextField inputField;

    public MenuInterfaz() {
        setTitle("Menú");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gestor = new GestorBiblioteca();

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("Gestor Biblioteca");
        inputField = new JTextField();

        adminLibrosButton = new JButton("Administrar libros");
        adminUsuariosButton = new JButton("Administrar usuarios");

        mainPanel.add(label);
        mainPanel.add(inputField);
        mainPanel.add(adminLibrosButton);
        mainPanel.add(adminUsuariosButton);

        add(mainPanel);

        adminLibrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tituloLibro = inputField.getText();
                gestionarLibros(tituloLibro);
            }
        });

        adminUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionarUsuarios();
            }
        });
    }

    private void gestionarLibros(String tituloLibro) {
        String[] opciones = {"Agregar Libro", "Buscar Libro", "Editar Libro", "Prestar Libro", "Devolver Libro"};
        int opcionSeleccionada = JOptionPane.showOptionDialog(this, "Seleccione una acción para administrar libros", "Administrar Libros", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        switch (opcionSeleccionada) {
            case 0:
                agregarLibro();
                break;
            case 1:
                buscarLibroPorTitulo();
                break;
            case 2:
                editarLibro();
                break;
            case 3:
                prestarLibro();
                break;
            case 4:
                devolverLibro();
                break;
            default:
                break;
        }
    }

    private void gestionarUsuarios() {
        String[] opciones = {"Agregar Usuario", "Buscar Usuario", "Eliminar Usuario", "Editar Usuario"};
        int opcionSeleccionada = JOptionPane.showOptionDialog(this, "Seleccione una acción para administrar usuarios", "Administrar Usuarios", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        switch (opcionSeleccionada) {
            case 0:
                agregarUsuario();
                break;
            case 1:
                buscarUsuarioPorId();
                break;
            case 2:
                eliminarUsuarioPorId();
                break;
            case 3:
                editarUsuarioPorId();
                break;
            default:
                break;
        }
    }

    // Método para agregar un libro
    private void agregarLibro() {
        String titulo = JOptionPane.showInputDialog(this, "Ingrese el título del libro:");
        String autor = JOptionPane.showInputDialog(this, "Ingrese el autor del libro:");
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el código del libro:"));
        int cantidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese la cantidad de libros:"));

        gestor.agregarLibro(new Libro(titulo, autor, codigo, cantidad));
        JOptionPane.showMessageDialog(this, "Libro(s) agregado(s) correctamente.");
    }

    // Método para buscar un libro por su título
    private void buscarLibroPorTitulo() {
        String titulo = JOptionPane.showInputDialog(this, "Ingrese el título del libro a buscar:");
        Libro libroEncontrado = gestor.buscarLibroPorTitulo(titulo);

        if (libroEncontrado != null) {
            JOptionPane.showMessageDialog(this, "Libro encontrado:\n" +
                    "Título: " + libroEncontrado.getTitulo() + "\n" +
                    "Autor: " + libroEncontrado.getAutor() + "\n" +
                    "Código: " + libroEncontrado.getCodigo() + "\n" +
                    "Cantidad: " + libroEncontrado.getCantidad());
        } else {
            JOptionPane.showMessageDialog(this, "Libro no encontrado.");
        }
    }

    // Método para eliminar un libro por su título
    private void eliminarLibro() {
        String nombreLibro = JOptionPane.showInputDialog(this, "Ingrese el título del libro que desea eliminar:");
        boolean eliminado = gestor.eliminarLibro(nombreLibro);

        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Libro eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "El libro no se encuentra en la biblioteca.");
        }
    }

    // Método para editar un libro por su título
    private void editarLibro() {
        String tituloLibro = JOptionPane.showInputDialog(this, "Ingrese el título del libro que desea editar:");
        Libro libroExistente = gestor.buscarLibroPorTitulo(tituloLibro);

        if (libroExistente != null) {
            String nuevoTitulo = JOptionPane.showInputDialog(this, "Ingrese el nuevo título del libro:");
            String nuevoAutor = JOptionPane.showInputDialog(this, "Ingrese el nuevo autor del libro:");
            int nuevoCodigo = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el nuevo código del libro:"));
            int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese la nueva cantidad de libros:"));

            Libro nuevoLibro = new Libro(nuevoTitulo, nuevoAutor, nuevoCodigo, nuevaCantidad);
            boolean editado = gestor.editarLibro(tituloLibro, nuevoLibro);

            if (editado) {
                JOptionPane.showMessageDialog(this, "Libro editado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "El libro no se encuentra en la biblioteca.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "El libro no se encuentra en la biblioteca.");
        }
    }

    // Método para prestar un libro
    private void prestarLibro() {
        int idUsuario = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el ID del usuario:"));
        String tituloLibro = JOptionPane.showInputDialog(this, "Ingrese el título del libro que desea prestar:");

        boolean prestado = gestor.pedirLibro(idUsuario, tituloLibro);

        if (prestado) {
            JOptionPane.showMessageDialog(this, "Libro prestado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "El libro no está disponible o el usuario no está registrado.");
        }
    }

    // Método para devolver un libro
    private void devolverLibro() {
        int idUsuario = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el ID del usuario:"));
        String tituloLibro = JOptionPane.showInputDialog(this, "Ingrese el título del libro que desea devolver:");

        boolean devuelto = gestor.devolverLibro(idUsuario, tituloLibro);

        if (devuelto) {
            JOptionPane.showMessageDialog(this, "Libro devuelto correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "El libro no se encuentra en la biblioteca o el usuario no está registrado.");
        }
    }

    // Método para agregar un usuario
    private void agregarUsuario() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del usuario:");
        int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el ID del usuario:"));

        gestor.registrarUsuario(new Usuario(nombre, id));
        JOptionPane.showMessageDialog(this, "Usuario agregado correctamente.");
    }

    // Método para buscar un usuario por su ID
    private void buscarUsuarioPorId() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el ID del usuario a buscar:"));
        Usuario usuarioEncontrado = gestor.buscarUsuarioPorId(id);

        if (usuarioEncontrado != null) {
            JOptionPane.showMessageDialog(this, "Usuario encontrado:\n" +
                    "Nombre: " + usuarioEncontrado.getNombre() + "\n" +
                    "ID: " + usuarioEncontrado.getId());
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
        }
    }

    // Método para eliminar Usuario por su ID
    private void eliminarUsuarioPorId() {
        int idUsuario = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el ID del Usuario que desea eliminar:"));
        boolean eliminado = gestor.eliminarUsuarioPorId(idUsuario);

        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "El Usuario no se encuentra en la biblioteca.");
        }
    }

    // Método para editar un usuario por su ID
    private void editarUsuarioPorId() {
        int idUsuario = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el ID del usuario que desea editar:"));
        Usuario usuarioExistente = gestor.buscarUsuarioPorId(idUsuario);

        if (usuarioExistente != null) {
            String nuevoNombre = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre del usuario:");
            Usuario nuevoUsuario = new Usuario(nuevoNombre, idUsuario);
            boolean editado = gestor.editarUsuarioPorId(idUsuario, nuevoUsuario);

            if (editado) {
                JOptionPane.showMessageDialog(this, "Usuario editado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "El usuario no se encuentra registrado en la biblioteca.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "El usuario no se encuentra registrado en la biblioteca.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuInterfaz menuInterfaz = new MenuInterfaz();
                menuInterfaz.setVisible(true);
            }
        });
    }
}
