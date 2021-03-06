/*
 *Esta clase se encargara de gestionar los servicios para generar documentos pdf
 */
package services;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfXrefTable;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JTable;


/**
 *
 * @author march
 */
public class PdfApi {
    final String PATH;
    PdfWriter pdfWriter;
    PdfDocument pdfDocument;
    Document document; 
    JTable information;
    String detailInformacion;
    public PdfApi(String path,JTable table,String detail) throws FileNotFoundException {
        this.PATH = path;
        pdfWriter = new PdfWriter(path);
        detailInformacion = detail;
        pdfDocument = new PdfDocument(pdfWriter);
        document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.LETTER);
        this.information= table;
        
        addHeader();
        document.add(new Paragraph("\n\n"));
        addTable();
        addDetailInfo();
        document.close();
        System.out.println("PDF create");
    }
    
    private void addHeader() {
        float column = 280f;
        float columntWidth[] = {column,column};
        
        Table table = new Table(columntWidth);
        table.setBackgroundColor(new DeviceRgb(63,169,219)).setFontColor(new DeviceRgb(255, 255, 255));
        table.addCell(new Cell().add(new Paragraph("Reporte de credito"))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setMarginTop(30f)
                .setMarginBottom(30f)
                .setFontSize(18f)
                .setBorder(Border.NO_BORDER));
                
        table.addCell(new Cell().add(new Paragraph("Sistema CrediTec\nDesarrollado por: Melanie Jimenez & Froyd Marchena"))
                .setTextAlignment(TextAlignment.RIGHT)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setMarginTop(30f)
                .setMarginBottom(30f)
                .setMarginRight(10f)
                .setFontSize(8f)
                .setBorder(Border.NO_BORDER));
                
        document.add(table);
        
    }
    
    private void addTable(){
        Table table = new Table(information.getColumnCount());
        
        for(int column = 0;column < information.getColumnCount() ; column++) {
            table.addHeaderCell(new Cell().add(new Paragraph(information.getColumnName(column))));
        }
        
        for(int row = 0; row<information.getRowCount();row++) {
            for(int column = 0; column < information.getColumnCount();column++){
                table.addCell(new Cell().add(new Paragraph(information.getValueAt(row, column).toString())));
            }
        }
        document.add(table);
    }
    
    private void addDetailInfo(){
        document.add(new Paragraph("\n\n"+detailInformacion));
    }
    
    public void setDetailInfo(String infomation){
        this.detailInformacion = infomation;
    }
}
