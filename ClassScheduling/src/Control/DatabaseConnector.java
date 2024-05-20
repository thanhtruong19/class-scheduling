/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector{
    public static Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:mysql://localhost:3306/class_scheduling_db";
        String USER_NAME = "root";
        String PASSWORD = "";
    
    return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
    }
    
     
}

   