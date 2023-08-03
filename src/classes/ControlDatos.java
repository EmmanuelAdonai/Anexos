/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import Models.ModelNumPag;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author emman
 */
public class ControlDatos {
    // -- Datos Encabezado --- //
    public ArrayList<ModeloDatos> llenarDatos() {
         ModeloDatos datos;
         SqlConnection connection = new SqlConnection();
         Connection conn = connection.CONN(Constans.year);
         ArrayList<ModeloDatos> lista = new ArrayList<>();
         
         try {
                  if(conn != null) {
                           String query = "select fte.Siglas, mun.NombreMunicipio, con.NumAE, con.NumConvenio from tblConvenios as con " +
                                    "left join tblCFuentesInversion fte on con.clvfte = fte.clvfte " +
                                    "left join tblMunicipios mun on con.idMunicipio = mun.IdMunicipio " +
                                    "where fte.Descripcion = '"+Constans.fte+"' and mun.NombreMunicipio = '"+Constans.mpio+"' order by con.NumAE asc";
                           Statement stmt = conn.createStatement();
                           ResultSet res = stmt.executeQuery(query);
                           if(res != null) {
                                    while(res.next()) {
                                             datos = new ModeloDatos();
                                             datos.setFte(res.getString("siglas"));
                                             datos.setMpio(res.getString("NombreMunicipio"));
                                             datos.setNumAE(res.getString("NumAE"));
                                             datos.setNumCon(res.getString("NumConvenio"));
                                             lista.add(datos);
                                    }
                           }
                            conn.close();
                  }
         } catch(SQLException e) {
                  JOptionPane.showMessageDialog(null, "Error de Conexión "+e.getMessage());
         }
         return lista;
    }
}
