package com.applicationswp.PdfForEvaluation;

/*
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;*/

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by alexandre on 24.11.16.
 */
public class Vorlage {
    /*
    private static String FILE = "web/pdf/text.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 22,
            Font.BOLD);
    private static Font subFont = new Font(Font.FontFamily.UNDEFINED, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);

    public static void main(String[] args) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Alexandre");
        document.addCreator("Alexandre");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        preface.setAlignment(Element.ALIGN_CENTER);
        preface.add(new Paragraph("Selbsteinschaetzungsbogen", catFont));
        preface.add(new Paragraph("(zur Vorbereitung des Schuelersprechtages)", smallBold));
        addEmptyLine(preface, 3);
        document.add(preface);

        document.add(addTable());

    }

    public static void addCellEvaluation(PdfPTable table, String value, String evalue1) {
        PdfPCell cell;
        // we add a cell with colspan 3
        cell = new PdfPCell(new Phrase(value));
        cell.setColspan(16);
        table.addCell(cell);

        if (evalue1.length() == 4) {
            PdfPCell cell1 = new PdfPCell(new Phrase(evalue1.charAt(0)));
            table.addCell(cell1);
            cell1 = new PdfPCell(new Phrase(evalue1.charAt(1)));
            table.addCell(cell1);
            cell1 = new PdfPCell(new Phrase(evalue1.charAt(2)));
            table.addCell(cell1);
            cell1 = new PdfPCell(new Phrase(evalue1.charAt(3)));
            table.addCell(cell1);
        }
    }

    private static PdfPTable addTable() {
        PdfPTable table = new PdfPTable(20);
        table.setTotalWidth(PageSize.A5.getWidth() + 60f);
        table.setLockedWidth(true);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        Phrase phrase = new Phrase("So schaetze ich mich selbst ein");
        cell = new PdfPCell(new Paragraph("So schaetze ich mich selbst ein", subFont));
        cell.setColspan(16);
        table.addCell(cell);

        PdfPCell cell1 = new PdfPCell(new Phrase("1"));
        table.addCell(cell1);
        cell1 = new PdfPCell(new Phrase("2"));
        table.addCell(cell1);
        cell1 = new PdfPCell(new Phrase("3"));
        table.addCell(cell1);
        cell1 = new PdfPCell(new Phrase("4"));
        table.addCell(cell1);

        // now we add a cell with rowspan 2
        cell = new PdfPCell((new Phrase("Zuverlaessigkeit")));
        cell.setColspan(20);
        table.addCell(cell);
        addCellEvaluation(table, "Ich bin puenktlich zum Unterricht.", "1111");
        addCellEvaluation(table, "Zu Unterrichtsbeginn habe ich alle notwendigen Arbeitsmittel bereit.", "1111");
        addCellEvaluation(table, "Ich erledige sonstige Aufgaben vollstaendig und termingerecht.", "1111");

        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase("Sorgfalt"));
        cell.setColspan(20);
        table.addCell(cell);
        addCellEvaluation(table, "Ich fuehre meine Hefte und Hefter sorgfaeltig und vollstaendig.", "1111");
        addCellEvaluation(table, "Ich halte eine gute Ordnung, die mir beim Arbeiten hilft.", "1111");
        addCellEvaluation(table, "Ich behandle fremdes Eigentum (von Mitschuelern und Schule) sorgsam.", "1111");


        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase("Leistungs- und Lernbereitschaft"));
        cell.setColspan(20);
        table.addCell(cell);
        addCellEvaluation(table, "Ich arbeite im Unterricht aktiv mit.", "1111");
        addCellEvaluation(table, "Ich arbeite mit Ausdauer an meinen Lernaufgaben.", "1111");
        addCellEvaluation(table, "Ich arbeite konzentriert und lasse mich nicht ablenken.", "1111");
        addCellEvaluation(table, "Ich bin bereit mich anzustrengen, um Aufgaben erfolgreich zu erledigen.", "1111");

        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase("Selbststaendigkeit"));
        cell.setColspan(20);
        table.addCell(cell);
        addCellEvaluation(table, "Ich fuehre meinen Schulplaner (Aufgaben, Termine) selbststaendig.", "1111");
        addCellEvaluation(table, "Ich fuehre meine Hefter und Hefte selbststaendig.", "1111");
        addCellEvaluation(table, "Ich beschaffe mir selbststaendig Informationen.", "1111");
        addCellEvaluation(table, "Ich kann ohne zusaetzliche Anleitung Arbeitsauftraege erledigen.", "1111");
        addCellEvaluation(table, "Ich kontrolliere meine Arbeitsergebnisse selbststaendig", "1111");

        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase("Kooperationsfaehigkeit"));
        cell.setColspan(20);
        table.addCell(cell);
        addCellEvaluation(table, "Ich erfuelle meine Aufgaben bei Gruppenarbeiten, in der Klasse und Schule.", "1111");
        addCellEvaluation(table, "Ich uebernehme freiwillig Aufgaben.", "1111");
        addCellEvaluation(table, "Ich kann kritisieren ohne andere zu verletzen und nehme Kritik an.", "1111");

        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase("Soziales Verhalten"));
        cell.setColspan(20);
        table.addCell(cell);
        addCellEvaluation(table, "Ich halte die Schulregeln und die Klassenregeln ein.", "1111");
        addCellEvaluation(table, "Ich gehe respektvoll mit meinen Mitmenschen um.", "1111");
        addCellEvaluation(table, "Ich hoere anderen zu und schaue sie dabei an.", "1111");
        addCellEvaluation(table, "Ich sage anderen angemessen meine Meinung.", "1111");
        addCellEvaluation(table, "Ich lasse mir helfen und helfe anderen.", "1111");
        addCellEvaluation(table, "Ich kann Konflikte alleine oder mit Hilfe gewaltfrei loesen.", "1111");


        cell = new PdfPCell(new Phrase("Ich kann bersonders :"));
        cell.setColspan(20);
        cell.setPaddingBottom(40f);
        table.addCell(cell);

        return table;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    */
}
