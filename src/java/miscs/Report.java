package utils; 

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import miscs.User;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import utils.Report;

public class Report {

    // Notice we changed the last parameter to a String (the file path)
    public void generate(String username, String password, String role, List<User> allUsers, String savePath) {
        
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

            if (isAdmin) {
                table.addCell(new Phrase("Username", boldTitleFont));
                table.addCell(new Phrase("Role", boldTitleFont));

                if (allUsers != null) {
                    for (User u : allUsers) {
                        String displayUsername = u.getUsername();
                        if (displayUsername.equals(username)) displayUsername += " *";
                        table.addCell(displayUsername);
                        table.addCell(u.getRole());
                    }
                }
            } else {
                table.addCell(new Phrase("Username", boldTitleFont));
                table.addCell(new Phrase("Password", boldTitleFont));
                table.addCell(username);
                table.addCell(password);
            }

            document.add(table);
            document.close();
            
            System.out.println("SUCCESS: Report saved to " + savePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}