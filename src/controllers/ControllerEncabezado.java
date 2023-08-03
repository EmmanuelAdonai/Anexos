/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import Models.MdelFirmas;
import Models.ModelEncabezdo;
import Models.ModelFondos;
import Models.ModelMetas;
import Models.ModelNumFon;
import Models.ModelNumMeta;
import Models.ModelNumPag;
import Models.ModelTable;
import Models.ModelTotales;
import classes.Constans;
import classes.SqlConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author emman
 */
public class ControllerEncabezado {
    private ModelEncabezdo model;
    
    public ControllerEncabezado() {
            model = new ModelEncabezdo();
    }
    
    // -- Datos del Encabezado -- //
    public ArrayList<ModelEncabezdo> llenarDatos() {
            ModelEncabezdo datos;
            SqlConnection connection = new SqlConnection();
            Connection conn = connection.CONN(Constans.year);
            ArrayList<ModelEncabezdo> lista = new ArrayList<>();
            
         try {
                  if(conn != null) {
                            String query = "select top 1 v_uDatosAprobados.Fuente,  substring(v_uDatosAprobados.NumConvenio,0, CHARINDEX('@',v_uDatosAprobados.NumConvenio,1)) as NumConvenio, v_uDatosAprobados.NombreMunicipio, "
                                                             + "v_uDatosAprobados.idMunicipio, v_uDatosAprobados.idDependencia ,v_uDatosAprobados.DepEjecutora as Dependencia, v_uDatosAprobados.DescRubro as Vertiente,tblZonas.descripcion as Region, "
                                                             + "Case WHEN tblConvenios.Modificado != '' then CONCAT(tblConvenios.NumAE,'-',tblConvenios.Modificado) else v_uDatosAprobados.NumAE end as NumAE, v_uDatosAprobados.FechaEmitido "
                                                             + "from v_uDatosAprobados "
                                                             + "inner join tblZonas on v_uDatosAprobados.region = tblZonas.idzona "
                                                             + "left join tblConvenios on v_uDatosAprobados.numae = tblConvenios.NumAE "
                                                             + "where v_uDatosAprobados.clvfte not in(64, 67, 117) and v_uDatosAprobados.NumAE = '"+Constans.AE+"'";
                                                    Statement stmt = conn.createStatement();
                                                    ResultSet r = stmt.executeQuery(query);
                                                    while(r.next()) {
                                                               datos = new ModelEncabezdo();
                                                               datos.setFte(r.getString("Fuente"));
                                                               datos.setNumConvenio(r.getString("NumConvenio"));
                                                               datos.setMpio(r.getString("NombreMunicipio"));
                                                               datos.setIdMpio(r.getInt("idMunicipio"));
                                                               datos.setIdDepen(r.getInt("idDependencia"));
                                                               datos.setDepen(r.getString("Dependencia"));
                                                               datos.setVertiente(r.getString("Vertiente"));
                                                               datos.setRegion(r.getString("Region"));
                                                               datos.setNumE(r.getString("NumAE"));
                                                               datos.setFecha(r.getString("FechaEmitido"));
                                                               lista.add(datos);
                                                    }
                  }
         } catch(SQLException e) {
                  JOptionPane.showMessageDialog(null, "Error de Conexión "+e.getMessage());
         }
         return lista;
    }
    
    // -- Numero paginas -- //
    public ArrayList<ModelNumPag> numPage() {
        ModelNumPag datos;
        SqlConnection connection = new SqlConnection();
        Connection conn = connection.CONN(Constans.year);
        ArrayList<ModelNumPag> lista = new ArrayList<>();
        
        try {
                  String query = "select count(ClaveObraAprobadaNoMun) as Numero, count(ClaveObraAprobadaNoMun)/5 as Pag from tblPropuestaAprobacion where clvfte not in(64, 67, 117) and NumAE = '"+Constans.AE+"'";
                  Statement stmt = conn.createStatement();
                  ResultSet res = stmt.executeQuery(query);
                  if(res != null) {
                           while (res.next()) {
                                    datos = new ModelNumPag();
                                    datos.setNumero(res.getInt("Numero"));
                                    datos.setPag(res.getInt("Pag"));
                                    lista.add(datos);
                           }
                  }
                  conn.close();
        } catch(SQLException e) {
                  JOptionPane.showMessageDialog(null, "Error de Conexión "+e.getMessage());
         }
         return lista;
    }
    
    // -- Datos de la tabla Principal -- //
    public ArrayList<ModelTable> tabla() {
        ModelTable d;
        SqlConnection connection = new SqlConnection();
        Connection conn = connection.CONN(Constans.year);
        ArrayList<ModelTable> lista = new ArrayList<>();
        
        try {
                  String query = "select ClaveObraAprobadaNoMun as NumeroObra, substring(clvProg,2,2) as Programa , Subp as Subprograma ,"
                                                      + "NumeroLocalidad = CASE WHEN dbo.fncAgebAprobada(v_uDatosAprobados.ClaveObraAprobada) <> '' Then dbo.fncAgebAprobada(v_uDatosAprobados.ClaveObraAprobada) ELSE cast(v_uDatosAprobados.NumeroLocalidad As nVarchar(20)) END, "
                                                      + "NombreLocalidad, DesCorta,idEdoObra as Situacion, ModEje as Modalidad, Partida, "
                                                      + "R20+Est+Mpal+Benef+Otros as total,R20,Est,Mpal,Benef,Otros,"
                                                      + "UnidadBenef as medida2,NoMujeres,NoHombres,NoFamilias_Benef,NoViviendas, ClaveObra, ClaveObraAprobada "
                                                      + "from v_uDatosAprobados "
                                                      + "where v_uDatosAprobados.clvfte not in(64, 67, 117) and v_uDatosAprobados.NumAE = '"+Constans.AE+"' order by ClaveObraAprobadaNoMun asc";
                  Statement stmt = conn.createStatement();
                  ResultSet r = stmt.executeQuery(query);
                  if(r != null) {
                           while(r.next()) {
                                    d = new ModelTable();
                                    d.setNumObra(r.getString("NumeroObra"));
                                    d.setProg(r.getString("Programa"));
                                    d.setSubProg(r.getString("SubPrograma"));
                                    d.setNumLoc(r.getString("NumeroLocalidad"));
                                    d.setLoc(r.getString("NombreLocalidad"));
                                    d.setDesc(r.getString("DesCorta"));
                                    d.setSit(r.getString("Situacion"));
                                    d.setMod(r.getString("Modalidad"));
                                    d.setPart(r.getString("Partida"));
                                    d.setTot(r.getFloat("total"));
                                    d.setR20(r.getFloat("R20"));
                                    d.setEst(r.getFloat("Est"));
                                    d.setMpal(r.getFloat("Mpal"));
                                    d.setBenef(r.getFloat("Benef"));
                                    d.setOtros(r.getFloat("Otros"));
                                    d.setUnMed(r.getString("medida2"));
                                    d.setNumH(r.getInt("NoMujeres"));
                                    d.setNumH(r.getInt("NoHombres"));
                                    d.setNumF(r.getInt("NoFamilias_Benef"));
                                    d.setNumV(r.getInt("NoViviendas"));
                                    d.setClvObra(r.getString("ClaveObra"));
                                    d.setClvObraA(r.getString("ClaveObraAprobada"));
                                    lista.add(d);
                           }
                  }
        }  catch(SQLException e) {
                  JOptionPane.showMessageDialog(null, "Error de Conexión "+e.getMessage());
         }
         return lista;
    }
    
    // -- Numero de Fondos -- //
    public ArrayList<ModelNumFon> numFond() {
        ModelNumFon datos;
        SqlConnection connection = new SqlConnection();
        Connection conn = connection.CONN(Constans.year);
        ArrayList<ModelNumFon> lista = new ArrayList<>();
        
        try {
                  String query = "select count(tblOrigen_RecursoEstatal.idOrigen) as NoFondos from tblOrigen_RecursoEstatal "
                          + "inner join tblPropuestaAprobacion on tblOrigen_RecursoEstatal.ClaveObraAprobada = tblPropuestaAprobacion.ClaveObraAprobada "
                          + "where tblPropuestaAprobacion.NumAE = '"+Constans.AE+"'";
                  Statement stmt = conn.createStatement();
                  ResultSet res = stmt.executeQuery(query);
                  if(res != null) {
                           while (res.next()) {
                                    datos = new ModelNumFon();
                                    datos.setNumFondo(res.getInt("NoFondos"));
                                    lista.add(datos);
                           }
                  }
                  conn.close();
        } catch(SQLException e) {
                  JOptionPane.showMessageDialog(null, "Error de Conexión "+e.getMessage());
         }
         return lista;
    }
    
    // -- Fondos -- //
    public ArrayList<ModelFondos> fondos() {
        ModelFondos d;
        SqlConnection connection = new SqlConnection();
        Connection conn = connection.CONN(Constans.year);
        ArrayList<ModelFondos> lista = new ArrayList<>();
        
        try {
                  String query = "select tblOrigen_RecursoEstatal.claveobraaprobada, tblPropuestaAprobacion.ClaveObraAprobadaNoMun, tblCOrigen_RecursoEstatal.Descripcion as fondos,"
                                             + "tblOrigen_RecursoEstatal.idorigen, aportacion as Total, tiporecurso, case when tiporecurso = 'Federal' then Aportacion else '0.00' end as FFederal, "
                                             + "case when tiporecurso = 'Estatal' then Aportacion else '0.00' end as FEstatal, case when tiporecurso = 'Municipal' then Aportacion else '0.00' end as FMunicipal, "
                                             + "case when tiporecurso = 'Beneficiarios' then Aportacion else '0.00' end as FBeneficiarios, case when tiporecurso = 'Otros' then Aportacion else '0.00' end as FOtros "
                                             + "from tblOrigen_RecursoEstatal "
                                             + "inner join tblPropuestaAprobacion on tblOrigen_RecursoEstatal.ClaveObraAprobada = tblPropuestaAprobacion.ClaveObraAprobada "
                                             + "inner join tblCOrigen_RecursoEstatal on tblOrigen_RecursoEstatal.idOrigen = tblCOrigen_RecursoEstatal.idOrigen "
                                             + "where tblPropuestaAprobacion.clvfte not in(64, 67, 117) and tblPropuestaAprobacion.NumAE = '"+Constans.AE+"' order by ClaveObraAprobadaNoMun asc";
                  Statement stmt = conn.createStatement();
                  ResultSet r = stmt.executeQuery(query);
                  if(r != null) {
                           while(r.next()) {
                                    d = new ModelFondos();
                                    d.setClvObra(r.getString("ClaveObraAprobada"));
                                    d.setClvObraNo(r.getString("ClaveObraAprobadaNoMun"));
                                    d.setDesc(r.getString("fondos"));
                                    d.setId(r.getInt("idOrigen"));
                                    d.setTot(r.getFloat("Total"));
                                    d.setTipo(r.getString("tiporecurso"));
                                    d.setFed(r.getFloat("FFederal"));
                                    d.setEst(r.getFloat("FEstatal"));
                                    d.setMpal(r.getFloat("FMunicipal"));
                                    d.setBene(r.getFloat("FBeneficiarios"));
                                    d.setOtr(r.getFloat("FOtros"));
                                    lista.add(d);
                           }
                  }
         } catch(SQLException e) {
                  JOptionPane.showMessageDialog(null, "Error de Conexión "+e.getMessage());
         }
         return lista;
    }
    
    // -- Numero de Metas -- //
    public ArrayList<ModelNumMeta> numMeta() {
        ModelNumMeta datos;
        SqlConnection connection = new SqlConnection();
        Connection conn = connection.CONN(Constans.year);
        ArrayList<ModelNumMeta> lista = new ArrayList<>();
        
        try {
                  String query = "select count(tblMetasAprobadas.ClaveObraAprobada) as Num from tblMetasAprobadas "
                          + "inner join tblPropuestaAprobacion on tblMetasAprobadas.ClaveObraAprobada = tblPropuestaAprobacion.ClaveObraAprobada "
                          + "where tblPropuestaAprobacion.clvfte not in(64, 67, 117) and tblPropuestaAprobacion.NumAE = '"+Constans.AE+"'";
                  Statement stmt = conn.createStatement();
                  ResultSet res = stmt.executeQuery(query);
                  if(res != null) {
                           while (res.next()) {
                                    datos = new ModelNumMeta();
                                    datos.setNum(res.getInt("Num"));
                                    lista.add(datos);
                           }
                  }
                  conn.close();
        } catch(SQLException e) {
                  JOptionPane.showMessageDialog(null, "Error de Conexión "+e.getMessage());
         }
         return lista;
    }
    
    // -- Metas -- //
    public ArrayList<ModelMetas> metas() {
        ModelMetas d;
        SqlConnection connection = new SqlConnection();
        Connection conn = connection.CONN(Constans.year);
        ArrayList<ModelMetas> lista = new ArrayList<>();
        
        try {
                  String query = "select tblPropuestaAprobacion.ClaveObraAprobadaNoMun, tblCMetas.Descripcion, tblMetasAprobadas.cantidad, tblMetasAprobadas.ClaveObraAprobada "
                                    + "from tblMetasAprobadas "
                                    + "inner join tblCMetas on tblMetasAprobadas.IdNombreMetas = tblCMetas.idNombreMetas "
                                    + "left join tblPropuestaAprobacion on tblMetasAprobadas.ClaveObraAprobada = tblPropuestaAprobacion.ClaveObraAprobada "
                                    + "where tblPropuestaAprobacion.clvfte not in(64, 67, 117) and tblPropuestaAprobacion.NumAE = '"+Constans.AE+"' order by tblPropuestaAprobacion.ClaveObraAprobadaNoMun,  tblCMetas.Descripcion asc";
                  Statement stmt = conn.createStatement();
                  ResultSet r = stmt.executeQuery(query);
                  if(r != null) {
                           while (r.next()) {
                                    d = new ModelMetas();
                                    d.setClvObraNo(r.getString("ClaveObraAprobadaNoMun"));
                                    d.setDesc(r.getString("Descripcion"));
                                    d.setCan(r.getInt("cantidad"));
                                    d.setClvObra(r.getString("ClaveObraAprobada"));
                                    lista.add(d);
                           }
                  }
                  conn.close();
        } catch(SQLException e) {
                  JOptionPane.showMessageDialog(null, "Error de Conexión "+e.getMessage());
         }
         return lista;
    }
    
    // -- Totales -- //
    public ArrayList<ModelTotales> totales() {
        ModelTotales d;
        SqlConnection connection = new SqlConnection();
        Connection conn = connection.CONN(Constans.year);
        ArrayList<ModelTotales> lista = new ArrayList<>();
        
        try {
                  String query = "select sum(R20+Est+Mpal+Benef+Otros) as total,sum(R20) as fed, sum(Est) as est, sum(Mpal) as mun, sum(Benef) as ben, sum(Otros) as otr "
                          + "from v_uDatosAprobados "
                                    + "where v_uDatosAprobados.clvfte not in(64, 67, 117) and v_uDatosAprobados.NumAE = '"+Constans.AE+"'";
                  Statement stmt = conn.createStatement();
                  ResultSet r = stmt.executeQuery(query);
                  if(r != null) {
                           while (r.next()) {
                                    d = new ModelTotales();
                                    d.setTot(r.getFloat("total"));
                                    d.setEst(r.getFloat("est"));
                                    d.setMun(r.getFloat("mun"));
                                    d.setBen(r.getFloat("ben"));
                                    d.setOtr(r.getFloat("otr"));
                                    lista.add(d);
                           }
                  }
                  conn.close();
        } catch(SQLException e) {
                  JOptionPane.showMessageDialog(null, "Error de Conexión "+e.getMessage());
         }
         return lista;
    }
    
    // -- Numero de Firmas -- //
    
    // -- Firmas -- //
    public ArrayList<MdelFirmas> firmas() {
        MdelFirmas d;
        SqlConnection connection = new SqlConnection();
        Connection conn = connection.CONN(Constans.year);
        ArrayList<MdelFirmas> lista = new ArrayList<>();
        
        try {
                  String query = "select NombreFirmanteDepEje, CargoFirmanteDepEje, FirmantePor3, CargoFirmante3, NombreFirmante3, FirmantePor5, CargoFirmante5, NombreFirmante5, FirmantePor6, CargoFirmante6, NombreFirmante6 "
                          + "from tblConvenios "
                                    + "where NumAE = '"+Constans.AE+"'";
                  Statement stmt = conn.createStatement();
                  ResultSet r = stmt.executeQuery(query);
                  if(r != null) {
                           while (r.next()) {
                                    d = new MdelFirmas();
                                    d.setName(r.getString("NombreFirmanteDepEje"));
                                    d.setCargo(r.getString("CargoFirmanteDepEje"));
                                    d.setFirm3(r.getString("FirmantePor3"));
                                    d.setCarg3(r.getString("CargoFirmante3"));
                                    d.setNom3(r.getString("NombreFirmante3"));
                                    d.setFirm5(r.getString("FirmantePor5"));
                                    d.setCarg5(r.getString("CargoFirmante5"));
                                    d.setNom5(r.getString("NombreFirmante5"));
                                    d.setFirm6(r.getString("FirmantePor6"));
                                    d.setCarg6(r.getString("CargoFirmante6"));
                                    d.setNom6(r.getString("NombreFirmante6"));
                                    lista.add(d);
                           }
                  }
                  conn.close();
        } catch(SQLException e) {
                  JOptionPane.showMessageDialog(null, "Error de Conexión "+e.getMessage());
         }
         return lista;
    }
}
