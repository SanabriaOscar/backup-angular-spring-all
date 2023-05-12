/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Oscar Jesus Sanabria
 */
public class TestDatabase {

    public static void main(String[] args) throws SQLException {
        // Configuración de la conexión a la base de datos
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "";

        // Intentar establecer la conexión
        try {
            // Cargar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            Connection connection = DriverManager.getConnection(url, username, password);

            // Si la conexión se establece correctamente, muestra un mensaje de éxito
            System.out.println("Conexión exitosa a la base de datos MySQL");

            // Cerrar la conexión
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}


