/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import Models.MdelFirmas;
import Models.ModelEncabezdo;
import Models.ModelFondos;
import Models.ModelMetas;
import Models.ModelNumFon;
import Models.ModelNumMeta;
import Models.ModelNumPag;
import Models.ModelTable;
import Models.ModelTotales;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.UnitValue;
import controllers.ControllerEncabezado;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 *
 * @author emman
 */
public class RepAnexo {
         public static boolean genPdf(String rute, String name) throws FileNotFoundException, MalformedURLException, IOException {
                  int b = 0, limite = 5, paginas = 0, temporal = 0, numero = 0, pag = 0;
                  String reg = "", mpio = "", ent = "", fte = "", conv = "", numE = "", dateEmi = "";
                  float tot = 0, fed = 0, est = 0, mun = 0, ben = 0, otr = 0;
                  File file = new File(rute);
                  file.getParentFile().mkdirs();
                   ControllerEncabezado control = new ControllerEncabezado();
                  
                  /////////////////////////////////////////////////                  
                  // -- Se crea el documento -- //
                  //////////////////////////////////////////////
                  PdfDocument pdfDoc = new PdfDocument(new PdfWriter(rute));
                  Document document = new Document(pdfDoc, PageSize.LEGAL.rotate());
                  document.setMargins(15, 20, 15, 20);
                  
                  ////////////////////////
                  // -- Estilos -- //
                  ///////////////////////
                  PdfFont fontContTitle = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
                  // -- Estilos encabezado -- //
                  Style tituloP = new Style();
                  tituloP.setFont(fontContTitle).setFontSize(9).setBold().setTextAlignment(TextAlignment.CENTER);
                  Style tituloSCenter = new Style();
                  tituloSCenter.setFont(fontContTitle).setFontSize(7).setBold().setTextAlignment(TextAlignment.CENTER);
                  Style tituloSLeft = new Style();
                  tituloSLeft.setFont(fontContTitle).setFontSize(7).setBold().setTextAlignment(TextAlignment.LEFT);
                  // -- Estilo Encabezado tabla -- //
                  Style titles = new Style();
                  titles.setFont(fontContTitle).setFontSize(6).setBold().setTextAlignment(TextAlignment.CENTER);
                  // -- Estilo Contenido tabla -- //
                  Style fondoDetalle = new Style();
                  fondoDetalle.setFont(fontContTitle).setFontSize(4).setTextAlignment(TextAlignment.CENTER);
                  Style fondoDetalleLeft = new Style();
                  fondoDetalleLeft.setFont(fontContTitle).setFontSize(4).setTextAlignment(TextAlignment.LEFT);
                  
                  Style fondo = new Style();
                  fondo.setFont(fontContTitle).setFontSize(4).setTextAlignment(TextAlignment.CENTER);
                  Style fondoSub = new Style();
                  fondoSub.setFont(fontContTitle).setFontSize(8).setTextAlignment(TextAlignment.CENTER);
                  // -- Estilo para el total general -- //
                  Style fondoCelda = new Style();
                  fondoCelda.setFont(fontContTitle).setFontSize(5).setBold().setTextAlignment(TextAlignment.CENTER);
                  // -- Estilos para las firmas -- //
                  Style firmas = new Style();
                  firmas.setFont(fontContTitle).setFontSize(8).setTextAlignment(TextAlignment.CENTER);
                  Style firmasBold = new Style();
                  firmasBold.setFont(fontContTitle).setFontSize(8).setTextAlignment(TextAlignment.CENTER).setBold();
                  
                  // -- Se obtiene el numero de Paginas -- //
                  ArrayList<ModelNumPag> pags;
                  pags = control.numPage();
                  for(int a = 0; a < pags.size(); a++) {
                           paginas = pags.get(a).getPag();
                           numero = pags.get(a).getNumero();
                  }
                  
                  paginas = paginas + 1;
                  
                  // -- Se obtienen los datos del Encabezado -- //
                  ArrayList<ModelEncabezdo> datos;
                  datos = control.llenarDatos();
                  for(int f = 0; f < datos.size(); f++) {
                           reg = datos.get(f).getRegion();
                           mpio = datos.get(f).getMpio();
                           ent = datos.get(f).getDepen();

                           fte = datos.get(f).getFte();
                           conv = datos.get(f).getNumConvenio();
                           numE = datos.get(f).getNumE();
                           dateEmi = datos.get(f).getFecha();
                  }
                  
                  // -- Se obtienen los datos de la tabla de datos -- //
                  ArrayList<ModelTable> d;
                  d = control.tabla();
                  
                  // -- Se obtiene el numero de fondos -- //
                  ArrayList<ModelNumFon> fon;
                  fon = control.numFond();
                  
                  // -- Se obtienen los fondos -- //
                  ArrayList<ModelFondos> fond;
                  fond = control.fondos();
                  
                  // -- Se ontiene el numero de Metas -- //
                  ArrayList<ModelNumMeta> nMeta;
                  nMeta = control.numMeta();
                  
                  // -- Se obtienen las metas -- //
                  ArrayList<ModelMetas> meta;
                  meta = control.metas();
                  
                  // -- Se obtienen los totales del anexo -- //
                  ArrayList<ModelTotales> totales;
                  totales = control.totales();
                  
                  // -- Se obtienen los firmantes -- //
                  ArrayList<MdelFirmas> firma;
                  firma = control.firmas();
             
                  System.out.println("Numero: "+numero);
                  System.out.println("Paginas: "+paginas);
                   
                  for(int i = 0; i < paginas; i++) {
                           ///////////////////////////////////////
                           // -- Logo Secretaria -- //
                           /////////////////////////////////////
                           String imgFile = "./img/gto-logo-nuevo.png";
                           ImageData data = ImageDataFactory.create("./img/gto-logo-nuevo.png");
                           Image img = new Image(ImageDataFactory.create(imgFile));
                           img.setHeight(50);
                           img.setWidth(140);
                           //////////////////////////////////////
                           // --- Encaabezado --- //
                           ////////////////////////////////////
                           Table tblEncabezado = new Table(3);
                           tblEncabezado.setWidth(UnitValue.createPercentValue(100));
                           // -- Fila 1 -- //
                           tblEncabezado.addCell(new Cell(3, 1).setWidth(UnitValue.createPercentValue(25)).setBorder(Border.NO_BORDER));
                           tblEncabezado.addCell(new Cell().setWidth(UnitValue.createPercentValue(50)).addStyle(tituloP).setBorder(Border.NO_BORDER).add(new Paragraph("SECRETARÍA DE DESARROLLO SOCIAL Y HUMANO")));
                           tblEncabezado.addCell(new Cell().setWidth(UnitValue.createPercentValue(25)).addStyle(tituloSCenter).setBorder(Border.NO_BORDER).add(new Paragraph("FSEDESHU-03/23")));
                           // -- Fila 2 -- //
                           tblEncabezado.addCell(new Cell().setWidth(UnitValue.createPercentValue(50)).addStyle(tituloSCenter).setBorder(Border.NO_BORDER).add(new Paragraph(""+fte)));
                           tblEncabezado.addCell(new Cell(6,1).setWidth(UnitValue.createPercentValue(25)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT).add(img));
                           // -- Fila 3 -- //
                           tblEncabezado.addCell(new Cell().setWidth(UnitValue.createPercentValue(100)).setBorder(Border.NO_BORDER));
                           // -- Fila 4 -- //
                           tblEncabezado.addCell(new Cell().setWidth(UnitValue.createPercentValue(25)).addStyle(tituloSLeft).setBorder(Border.NO_BORDER).add(new Paragraph("REGIÓN: "+reg)));
                           tblEncabezado.addCell(new Cell().setWidth(UnitValue.createPercentValue(50)).addStyle(tituloSCenter).setBorder(Border.NO_BORDER).add(new Paragraph("CONVENIO: "+conv)));
                           // -- Fila 5 -- //
                           tblEncabezado.addCell(new Cell().setWidth(UnitValue.createPercentValue(25)).addStyle(tituloSLeft).setBorder(Border.NO_BORDER).add(new Paragraph("MUNICIPIO: "+mpio)));
                           tblEncabezado.addCell(new Cell().setWidth(UnitValue.createPercentValue(50)).addStyle(tituloP).setBorder(Border.NO_BORDER).add(new Paragraph("ANEXO DE EJECUCIÓN: "+numE)));
                           // -- Fila 6 -- //
                           tblEncabezado.addCell(new Cell().setWidth(UnitValue.createPercentValue(25)).addStyle(tituloSLeft).setBorder(Border.NO_BORDER).add(new Paragraph("ENTIDAD EJECUTORA: "+ent)));
                           tblEncabezado.addCell(new Cell().setWidth(UnitValue.createPercentValue(50)).addStyle(tituloSCenter).setBorder(Border.NO_BORDER).add(new Paragraph("FECHA DE EMISIÓN: "+dateEmi)));
                           // -- Fila 7 -- //
                           tblEncabezado.addCell(new Cell(1, 2).setWidth(UnitValue.createPercentValue(100)).setBorder(Border.NO_BORDER));
                           document.add(tblEncabezado);
                           
                           ////////////////////////////////////////////////
                           // --- Tabla de Contenido --- //
                           //////////////////////////////////////////////
                           Table tblContent = new Table(22);
                           tblContent.setWidth(UnitValue.createPercentValue(100));
                           // -- Fila Encabezados Tabla -- //
                           tblContent.addCell(new Cell(2,1).setWidth(30).addStyle(titles).add(new Paragraph("Número de la Obra/Acción")));
                           tblContent.addCell(new Cell(2,1).setWidth(10).addStyle(titles).add(new Paragraph("Prog")));
                           tblContent.addCell(new Cell(2,1).setWidth(10).addStyle(titles).add(new Paragraph("Subp")));
                           tblContent.addCell(new Cell(2,1).setWidth(30).addStyle(titles).add(new Paragraph("Clave Localidad AGEB")));
                           tblContent.addCell(new Cell(2,1).setWidth(50).addStyle(titles).add(new Paragraph("Localidad")));
                           tblContent.addCell(new Cell(2,1).setWidth(105).addStyle(titles).add(new Paragraph("Nombre de la Obra/Acción")));
                           tblContent.addCell(new Cell(2,1).setWidth(10).addStyle(titles).add(new Paragraph("Sit")));
                           tblContent.addCell(new Cell(2,1).setWidth(10).addStyle(titles).add(new Paragraph("ME")));
                           tblContent.addCell(new Cell(2,1).setWidth(40).addStyle(titles).add(new Paragraph("Fuente de Financiamiento")));
                           
                           tblContent.addCell(new Cell(1,6).setWidth(120).addStyle(titles).add(new Paragraph("Inversión Convenida")));
                           tblContent.addCell(new Cell(1,2).setWidth(60).addStyle(titles).add(new Paragraph("Metas")));
                           tblContent.addCell(new Cell(1,5).setWidth(120).addStyle(titles).add(new Paragraph("Beneficiarios")));
                           
                           tblContent.addCell(new Cell().setWidth(20).addStyle(titles).add(new Paragraph("Total")));
                           tblContent.addCell(new Cell().setWidth(20).addStyle(titles).add(new Paragraph("Federal")));
                           tblContent.addCell(new Cell().setWidth(20).addStyle(titles).add(new Paragraph("Estatal")));
                           tblContent.addCell(new Cell().setWidth(20).addStyle(titles).add(new Paragraph("Municipal")));
                           tblContent.addCell(new Cell().setWidth(20).addStyle(titles).add(new Paragraph("Beneficirios")));
                           tblContent.addCell(new Cell().setWidth(20).addStyle(titles).add(new Paragraph("Otros")));
                           
                           tblContent.addCell(new Cell().setWidth(40).addStyle(titles).add(new Paragraph("Unidad de Medida")));
                           tblContent.addCell(new Cell().setWidth(20).addStyle(titles).add(new Paragraph("Cantidad")));
                           
                           tblContent.addCell(new Cell().setWidth(40).addStyle(titles).add(new Paragraph("Unidad Medida")));
                           tblContent.addCell(new Cell().setWidth(20).addStyle(titles).add(new Paragraph("Mujeres")));
                           tblContent.addCell(new Cell().setWidth(20).addStyle(titles).add(new Paragraph("Hombres")));
                           tblContent.addCell(new Cell().setWidth(20).addStyle(titles).add(new Paragraph("Familias")));
                           tblContent.addCell(new Cell().setWidth(20).addStyle(titles).add(new Paragraph("Viviendas")));
                           
                            for(b = temporal; b < limite; b++) {
                                if(b < numero) {
                                    tblContent.addCell(new Cell().setWidth(30).addStyle(fondoDetalle).add(new Paragraph(d.get(b).getNumObra())));
                                    tblContent.addCell(new Cell().setWidth(10).addStyle(fondoDetalle).add(new Paragraph(d.get(b).getProg())));
                                    tblContent.addCell(new Cell().setWidth(10).addStyle(fondoDetalle).add(new Paragraph(d.get(b).getSubProg())));
                                    tblContent.addCell(new Cell().setWidth(30).addStyle(fondoDetalle).add(new Paragraph(d.get(b).getNumLoc())));
                                    tblContent.addCell(new Cell().setWidth(50).addStyle(fondoDetalle).add(new Paragraph(d.get(b).getLoc())));
                                    tblContent.addCell(new Cell().setWidth(105).addStyle(fondoDetalleLeft).add(new Paragraph(d.get(b).getDesc())));
                                    tblContent.addCell(new Cell().setWidth(10).addStyle(fondoDetalle).add(new Paragraph(d.get(b).getPart())));
                                    tblContent.addCell(new Cell().setWidth(10).addStyle(fondoDetalle).add(new Paragraph(d.get(b).getMod())));
                                    
                                    Cell cellFondos = new Cell(1, 7).setWidth(160);
                                    Table tblFondos = new Table(7);
                                     tblFondos.setWidth(UnitValue.createPercentValue(100));
                                    for(int c = 0; c < fon.get(0).getNumFondo(); c++) {
                                             if(fond.get(c).getClvObraNo() == null ? d.get(b).getNumObra() == null : fond.get(c).getClvObraNo().equals(d.get(b).getNumObra())) {
                                                      tblFondos.addCell(new Cell().setWidth(UnitValue.createPercentValue(22)).addStyle(fondo).add(new Paragraph(fond.get(c).getDesc()))
                                                               .setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
                                                      tblFondos.addCell(new Cell().setWidth(UnitValue.createPercentValue(13)).addStyle(fondo).add(new Paragraph("$"+fond.get(c).getTot()))
                                                                .setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
                                                      tblFondos.addCell(new Cell().setWidth(UnitValue.createPercentValue(13)).addStyle(fondo).add(new Paragraph("$"+fond.get(c).getFed()))
                                                                .setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
                                                      tblFondos.addCell(new Cell().setWidth(UnitValue.createPercentValue(13)).addStyle(fondo).add(new Paragraph("$"+fond.get(c).getEst()))
                                                                .setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
                                                      tblFondos.addCell(new Cell().setWidth(UnitValue.createPercentValue(13)).addStyle(fondo).add(new Paragraph("$"+fond.get(c).getMpal()))
                                                                .setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
                                                      tblFondos.addCell(new Cell().setWidth(UnitValue.createPercentValue(13)).addStyle(fondo).add(new Paragraph("$"+fond.get(c).getBene()))
                                                                .setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
                                                      tblFondos.addCell(new Cell().setWidth(UnitValue.createPercentValue(13)).addStyle(fondo).add(new Paragraph("$"+fond.get(c).getOtr()))
                                                                .setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
                                             }
                                    }
                                    
                                    cellFondos.add(tblFondos);
                                    tblContent.addCell(cellFondos);
                                    
                                    Cell cellMetas = new Cell(1, 2).setWidth(60);
                                    Table tblMetas = new Table(2).setWidth(UnitValue.createPercentValue(100));
                                    for(int e = 0; e < nMeta.get(0).getNum(); e++) {
                                             if(meta.get(e).getClvObraNo() == null ? d.get(b).getNumObra() == null : meta.get(e).getClvObraNo().equals(d.get(b).getNumObra())) {
                                                      tblMetas.addCell(new Cell().setWidth(UnitValue.createPercentValue(60)).addStyle(fondo).add(new Paragraph(meta.get(e).getDesc()))
                                                                .setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
                                                      tblMetas.addCell(new Cell().setWidth(UnitValue.createPercentValue(40)).addStyle(fondo).add(new Paragraph(""+meta.get(e).getCan()))
                                                                .setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
                                             }
                                    }
                                    
                                    cellMetas.add(tblMetas);
                                    tblContent.addCell(cellMetas);
                                    
                                    tblContent.addCell(new Cell().setWidth(40).addStyle(fondoDetalle).add(new Paragraph(d.get(b).getUnMed()))); 
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoDetalle).add(new Paragraph(""+d.get(b).getNumM()))); 
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoDetalle).add(new Paragraph(""+d.get(b).getNumH()))); 
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoDetalle).add(new Paragraph(""+d.get(b).getNumF()))); 
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoDetalle).add(new Paragraph(""+d.get(b).getNumV()))); 
                                    
                                    tot = d.get(b).getTot();
                                    fed = d.get(b).getR20();
                                    est = d.get(b).getEst();
                                    mun = d.get(b).getMpal();
                                    ben = d.get(b).getBenef();
                                    otr = d.get(b).getOtros();
                                    
                                    tblContent.addCell(new Cell(1, 9).setWidth(295).setBorder(Border.NO_BORDER));
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoSub).add(new Paragraph("$"+tot)).setBorder(Border.NO_BORDER));
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoSub).add(new Paragraph("$"+fed)).setBorder(Border.NO_BORDER));
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoSub).add(new Paragraph("$"+est)).setBorder(Border.NO_BORDER));
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoSub).add(new Paragraph("$"+mun)).setBorder(Border.NO_BORDER));
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoSub).add(new Paragraph("$"+ben)).setBorder(Border.NO_BORDER));
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoSub).add(new Paragraph("$"+otr)).setBorder(Border.NO_BORDER));
                                    tblContent.addCell(new Cell(1, 7).setWidth(180).setBorder(Border.NO_BORDER));
                                    
                                    if(b + 1 == numero)
                                             tblContent.addCell(new Cell(1,22).setWidth(595).setBorder(Border.NO_BORDER));
                                    else 
                                             tblContent.addCell(new Cell(1,22).setWidth(595).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
                                }
                           }
                            
                           limite = limite + 5;
                           temporal = b;
                           
                           // -- Espacio -- //
                           tblContent.addCell(new Cell(1,6).setWidth(235).setBorder(Border.NO_BORDER));
                           tblContent.addCell(new Cell(1,7).setWidth(140).setBorder(Border.NO_BORDER));
                           tblContent.addCell(new Cell(1,9).setWidth(220).setBorder(Border.NO_BORDER));
                           
                           // -- Totales del Anexo -- //
                           if(i == (paginas - 1)) {
                                    tblContent.addCell(new Cell(1, 8).setWidth(255).setBorder(Border.NO_BORDER));
                                    tblContent.addCell(new Cell().setWidth(40).addStyle(fondoCelda).add(new Paragraph("Total:")).setBorder(new SolidBorder(1)));
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoCelda).add(new Paragraph("$"+totales.get(0).getTot())).setBorder(new SolidBorder(1)));
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoCelda).add(new Paragraph("$"+totales.get(0).getFed())).setBorder(new SolidBorder(1)));
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoCelda).add(new Paragraph("$"+totales.get(0).getEst())).setBorder(new SolidBorder(1)));
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoCelda).add(new Paragraph("$"+totales.get(0).getMun())).setBorder(new SolidBorder(1)));
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoCelda).add(new Paragraph("$"+totales.get(0).getBen())).setBorder(new SolidBorder(1)));
                                    tblContent.addCell(new Cell().setWidth(20).addStyle(fondoCelda).add(new Paragraph("$"+totales.get(0).getOtr())).setBorder(new SolidBorder(1)));
                                    tblContent.addCell(new Cell(1, 7).setWidth(180).setBorder(Border.NO_BORDER));
                           }
                           
                           document.add(tblContent);
                           
                           // -- Firmas -- //
                           //if(firma.get(0).getNom3() != '') {
                                    Table tblFirmas = new Table(3).setWidth(UnitValue.createPercentValue(100));
                                    tblFirmas.addCell(new Cell(1,3).setHeight(70).setWidth(595).setBorder(Border.NO_BORDER));

                                    Cell firm1 = new Cell().setWidth(99).setBorder(Border.NO_BORDER);
                                    Table tblFir1 = new Table(1).setWidth(UnitValue.createPercentValue(100));
                                    tblFir1.addCell(new Cell().setWidth(595).setBorder(Border.NO_BORDER));
                                    tblFir1.addCell(new Cell().setWidth(595).addStyle(firmasBold).setBorder(Border.NO_BORDER).add(new Paragraph("POR LA SECRETARÍA")));
                                    tblFir1.addCell(new Cell().setWidth(595).setBorder(Border.NO_BORDER));
                                    tblFir1.addCell(new Cell().setWidth(595).addStyle(firmas).setBorder(Border.NO_BORDER).add(new Paragraph("MTRA. LIBIA DENNISE GARCÍA MUÑOZ LEDO")));
                                    tblFir1.addCell(new Cell().setWidth(595).setBorder(Border.NO_BORDER));
                                    tblFir1.addCell(new Cell().setWidth(595).addStyle(firmas).setBorder(Border.NO_BORDER).add(new Paragraph("________________________________________________")));
                                    tblFir1.addCell(new Cell().setWidth(595).setBorder(Border.NO_BORDER));
                                    tblFir1.addCell(new Cell().setWidth(595).addStyle(firmas).setBorder(Border.NO_BORDER).add(new Paragraph("SECRETARIA DE DESARROLLO SOCIAL Y HUMANO")));
                                    firm1.add(tblFir1);
                                    tblFirmas.addCell(firm1);

                                    Cell firm2 = new Cell().setWidth(99).setBorder(Border.NO_BORDER);
                                    Table tblFir2 = new Table(1).setWidth(UnitValue.createPercentValue(100));
                                    tblFir2.addCell(new Cell().setWidth(595).setBorder(Border.NO_BORDER));
                                    tblFir2.addCell(new Cell().setWidth(595).addStyle(firmasBold).setBorder(Border.NO_BORDER).add(new Paragraph("POR LA DEPENDENCIA EJECUTORA")));
                                    tblFir2.addCell(new Cell().setWidth(595).setBorder(Border.NO_BORDER));
                                    tblFir2.addCell(new Cell().setWidth(595).addStyle(firmas).setBorder(Border.NO_BORDER).add(new Paragraph(firma.get(0).getName())));
                                    tblFir2.addCell(new Cell().setWidth(595).setBorder(Border.NO_BORDER));
                                    tblFir2.addCell(new Cell().setWidth(595).addStyle(firmas).setBorder(Border.NO_BORDER).add(new Paragraph("________________________________________________")));
                                    tblFir2.addCell(new Cell().setWidth(595).setBorder(Border.NO_BORDER));
                                    tblFir2.addCell(new Cell().setWidth(595).addStyle(firmas).setBorder(Border.NO_BORDER).add(new Paragraph(firma.get(0).getCargo())));
                                    firm2.add(tblFir2);
                                    tblFirmas.addCell(firm2);

                                    Cell firm3 = new Cell().setWidth(99).setBorder(Border.NO_BORDER);
                                    Table tblFir3 = new Table(1).setWidth(UnitValue.createPercentValue(100));
                                    tblFir3.addCell(new Cell().setWidth(595).setBorder(Border.NO_BORDER));
                                    tblFir3.addCell(new Cell().setWidth(595).addStyle(firmasBold).setBorder(Border.NO_BORDER).add(new Paragraph("POR EL MUNICIPIO")));
                                    tblFir3.addCell(new Cell().setWidth(595).setBorder(Border.NO_BORDER));
                                    tblFir3.addCell(new Cell().setWidth(595).addStyle(firmas).setBorder(Border.NO_BORDER).add(new Paragraph(firma.get(0).getNom3())));
                                    tblFir3.addCell(new Cell().setWidth(595).setBorder(Border.NO_BORDER));
                                    tblFir3.addCell(new Cell().setWidth(595).addStyle(firmas).setBorder(Border.NO_BORDER).add(new Paragraph("________________________________________________")));
                                    tblFir3.addCell(new Cell().setWidth(595).setBorder(Border.NO_BORDER));
                                    tblFir3.addCell(new Cell().setWidth(595).addStyle(firmas).setBorder(Border.NO_BORDER).add(new Paragraph(firma.get(0).getCarg3())));
                                    firm3.add(tblFir3);
                                    tblFirmas.addCell(firm3);

                                    document.add(tblFirmas);
                           // }
                           document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                  }
                  document.close();
                  return true;
         }
}
