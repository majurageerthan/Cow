package com.shankeerthan;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportWriter {
    String path;
    double height;
    double width;
    Document document;

    public ReportWriter(String path, float pageWidth, float pageHeight) {
        this.path = path;
        this.width = pageWidth;
        this.height = pageHeight;

        Rectangle pagesize = new Rectangle(pageWidth, pageHeight);
        document = new Document(pagesize, 36f, 72f, 108f, 180f);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(path)).setInitialLeading(16f);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();


    }

    /*
    This method will close the document after invoke this method you cannot write in doucument
     */
    public void closeDocument() {
        document.close();
    }

    public void writeTitle(String title) {
        Phrase director = new Phrase();

        Chunk titleChunk = new Chunk();
        titleChunk.setTextRise(28f);
        try {
            BaseFont timesbd = BaseFont.createFont("c:/windows/fonts/timesbd.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
            titleChunk.setFont(new Font(timesbd, 24));
            director.add(new Chunk(title));
            document.add(director);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    /*
    method arguments nameOfImages and wounds must be same length
     */
    public void makeDataTable(File[] nameOfImages, int[] wounds) {

        try {
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(150);
        table.addCell(new Phrase("Images"));
        table.addCell(new Phrase("No of Regions"));
        for (int i = 0; i < nameOfImages.length; i++) {
            table.addCell(nameOfImages[i].getName());
            table.addCell(Integer.toString(wounds[i]));
        }
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void makeMetaDataTable(double[] metaData, String colorPallete, String temUnit) {
        /*metaData array
        index 0:scale max
              1:scale Min
              2:range Max
              3:range min
        */
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(150);
        table.addCell(new Phrase("App Configurations"));
        table.addCell(new Phrase("Values"));
        table.addCell("TEMPARATURE UNIT");
        table.addCell(temUnit);
        table.addCell("SCALE MAX");
        table.addCell(Double.toString(metaData[0]));
        table.addCell("SCALE MIN");
        table.addCell(Double.toString(metaData[1]));
        table.addCell("RANGE MAX");
        table.addCell(Double.toString(metaData[2]));
        table.addCell("RANGE MIN");
        table.addCell(Double.toString(metaData[3]));

        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
