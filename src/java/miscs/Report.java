package miscs; 

import com.itextpdf.text.*;

//import com.itextpdf.text.Document;
//import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
//import com.itextpdf.text.pdf.PdfWriter;
import miscs.User;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Report {

    public void generate(String username, String password, String role, List<String[]> allUsers, String savePath) {
        
        try {
            Document document;
            boolean isAdmin = "admin".equalsIgnoreCase(role);
            
            // 1. Page Size
            if (isAdmin) {
                document = new Document(PageSize.LETTER); 
            } else {
                document = new Document(new Rectangle(400, 300)); 
            }

            // 2. Point the PDF Writer to a File on the Hard Drive
            FileOutputStream fileOut = new FileOutputStream(savePath);
            PdfWriter writer = PdfWriter.getInstance(document, fileOut);
            Font italicFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            final PdfTemplate[] total = new PdfTemplate[1];
            writer.setPageEvent(new PdfPageEventHelper() {
                public void onOpenDocument(PdfWriter writer, Document document) {
                    total[0] = writer.getDirectContent().createTemplate(30, 16);
                }
                
                public void onEndPage(PdfWriter writer, Document document) {
                    PdfContentByte cb = writer.getDirectContent();
                    Phrase name = new Phrase(username, italicFont);
                    ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, 
                        name, document.left(), 
                        document.bottom() - 10, 0);
                    Phrase pageNumber = new Phrase("Page " + writer.getPageNumber() + " of ", italicFont);
                    ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, 
                        pageNumber, document.right() - 15, 
                        document.bottom() - 10, 0);
                    cb.addTemplate(total[0], document.right() - 10, document.bottom() - 10);
                }
                
                public void onCloseDocument(PdfWriter writer, Document document) {
                    total[0].beginText();
                    total[0].setFontAndSize(bf, 10);
                    total[0].showText(String.valueOf(writer.getPageNumber()));
                    total[0].endText();
                }
            });
            document.open();

            // 3. Header & Timestamp
            Font boldTitleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph(isAdmin ? "Admin Report" : "Guest Report", boldTitleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            document.add(new Paragraph("Generated on: " + new Date().toString()));
            document.add(new Paragraph(" ")); // Blank line

            // 4. Content
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
//            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.setHorizontalAlignment(Element.ALIGN_MIDDLE);

            if (isAdmin) {
                PdfPCell cell1 = new PdfPCell(new Phrase("Username", boldTitleFont));
                cell1.setPaddingTop(8f);
                cell1.setPaddingBottom(8f);
                cell1.setPaddingLeft(5f);  // Specific side padding
                PdfPCell cell2 = new PdfPCell(new Phrase("Role", boldTitleFont));
                cell2.setPaddingLeft(5f);  // Specific side padding
                cell2.setPaddingTop(8f);
                cell2.setPaddingBottom(8f);
                table.addCell(cell1);
                table.addCell(cell2);
                if (allUsers != null) {
                    Iterator uit = allUsers.iterator();
                    boolean b = false;
                    while (uit.hasNext()){
                        String[] u = (String[]) uit.next();
                        // iterates through the username, password, role
                        int x = 1;
                        for (String i : u){
                            if (i.equals(username) && !b){
                                i+=" *";
                                b = true;
                            }
                            PdfPCell cell3 = new PdfPCell(new Phrase(i));
                            cell3.setPaddingTop(8f);
                            cell3.setPaddingBottom(8f);
                            cell3.setPaddingLeft(5f);  // Specific side padding
                            if(!(x%3==2))table.addCell(cell3);
                            x++;
                        }
                    }
                }
            } else {
                PdfPCell cell1 = new PdfPCell(new Phrase("Username", boldTitleFont));
                cell1.setPaddingTop(8f);
                cell1.setPaddingBottom(8f);
                cell1.setPaddingLeft(5f);  // Specific side padding
                PdfPCell cell2 = new PdfPCell(new Phrase("Password", boldTitleFont));
                cell2.setPaddingLeft(5f);  // Specific side padding
                cell2.setPaddingTop(8f);
                cell2.setPaddingBottom(8f);
                PdfPCell cell3 = new PdfPCell(new Phrase(username));
                PdfPCell cell4 = new PdfPCell(new Phrase(password));
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }
            
            document.add(table);
            
            document.close();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}