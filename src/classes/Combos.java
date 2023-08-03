/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.util.Calendar;
import java.util.Date;
import javax.swing.JComboBox;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author emmanuel lona
 */
public class Combos {
    // --- Combo de Años --- //
    public void comboYears(JComboBox years) {
        years.removeAllItems();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int yearAct = calendar.get(Calendar.YEAR);
        int yearAnt = yearAct - 1;
        years.addItem("Selecciona...");
        
        for (int i = yearAnt; i <= yearAct; i++) 
            years.addItem(Integer.toString(i));
    }
    
    // --- Combo de Fuentes --- //
    public void comboFtes(JComboBox ftes) {
         ftes.removeAllItems();
         SqlConnection connection = new SqlConnection();
         Connection conn = connection.CONN(Constans.year);
        
         try {
            if(conn != null) {
                  String query = "select fte.Descripcion from tblConvenios as con " +
                        "left join tblCFuentesInversion fte on con.clvfte = fte.clvfte " +
                        "group by fte.Descripcion order by fte.Descripcion asc";
                  Statement stmt = conn.createStatement();
                  ResultSet res = stmt.executeQuery(query);
                  if(res != null) {
                       ftes.addItem("Selecciona...");
                      while (res.next()) {
                          ftes.addItem(res.getString("Descripcion"));
                      }
                  }
                  conn.close();
            }
         } catch (SQLException e) {
                   JOptionPane.showMessageDialog(null, "Error de Conexión "+e.getMessage());
         }
    }
    
    // --- Combo de Municipios --- //
    public void comboMpios(JComboBox mpios) {
        mpios.removeAllItems();
        SqlConnection connection = new SqlConnection();
         Connection conn = connection.CONN(Constans.year);
        
         try {
            if(conn != null) {
                  String query = "select mun.NombreMunicipio from tblConvenios as con " +
                            "left join tblCFuentesInversion fte on con.clvfte = fte.clvfte " +
                            "left join tblMunicipios mun on con.idMunicipio = mun.IdMunicipio " +
                            "where fte.Descripcion = '"+Constans.fte+"' "+
                            "group by mun.NombreMunicipio , mun.IdMunicipio order by mun.IdMunicipio asc";
                  Statement stmt = conn.createStatement();
                  ResultSet res = stmt.executeQuery(query);
                  if(res != null) {
                       mpios.addItem("Selecciona...");
                      while (res.next()) {
                          mpios.addItem(res.getString("NombreMunicipio"));
                      }
                  }
                  conn.close();
            }
         } catch (SQLException e) {
                   JOptionPane.showMessageDialog(null, "Error de Conexión "+e.getMessage());
         }
    }
}
