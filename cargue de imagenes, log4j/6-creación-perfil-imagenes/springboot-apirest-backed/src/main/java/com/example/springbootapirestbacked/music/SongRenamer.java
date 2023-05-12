package com.example.springbootapirestbacked.music;

import java.io.File;

public class SongRenamer {
    public static void main(String[] args) {
        // Ruta del directorio donde se encuentran las canciones
        String directorioCanciones = "C:\\Users\\Oscar Jesus Sanabria\\Downloads";
        // Texto a eliminar del nombre de las canciones
        String textoAEliminar = "y2mate.com - ";
        // Obtiene una referencia al directorio
        File directorio = new File(directorioCanciones);
        // Verifica que el directorio exista y sea un directorio válido
        if (directorio.exists() && directorio.isDirectory()) {
            // Obtiene la lista de archivos en el directorio
            File[] archivos = directorio.listFiles();
            // Itera sobre cada archivo en el directorio
            for (File archivo : archivos) {
                // Verifica si el archivo es un archivo de música
                if (archivo.isFile() && archivo.getName().endsWith(".mp3")) {
                    // Obtiene el nombre actual del archivo
                    String nombreActual = archivo.getName();
                    // Verifica si el nombre del archivo contiene el texto a eliminar
                    if (nombreActual.contains(textoAEliminar)) {
                        // Genera el nuevo nombre del archivo sin el texto a eliminar
                        String nuevoNombre = nombreActual.replace(textoAEliminar, "");

                        // Crea un nuevo archivo con el nuevo nombre
                        File nuevoArchivo = new File(directorio, nuevoNombre);

                        // Renombra el archivo
                        if (archivo.renameTo(nuevoArchivo)) {
                            System.out.println("Canción renombrada: " + nuevoNombre);
                        } else {
                            System.out.println("Error al renombrar la canción: " + nombreActual);
                        }
                    }
                }
            }
        } else {
            System.out.println("El directorio de canciones no existe o no es un directorio válido.");
        }
    }
}
