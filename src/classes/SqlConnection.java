/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author emmanuel lona
 */
public class SqlConnection {
    public Connection CONN(String anio) {
        String url;
        Connection conn = null;
        String base = Constans.db+anio;
        
        try {
            Class.forName(Constans.driver);
                           url = "jdbc:jtds:sqlserver://" + Constans.server + ";"
                                    + "databaseName=" + base + ";"
                                    + "user=" + Constans.user + ";"
                                    + "password=" + Constans.pass + ";";
                            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
                  ex.getSQLState();
                  System.out.print("Error: "+ex.getMessage());
         } catch (ClassNotFoundException fe) {
                  System.out.print("Error2: "+fe.getMessage());
         } catch (Exception e) {
                  System.out.print("Error3: "+e.getMessage());
        }
        
        return conn;
    }
}
