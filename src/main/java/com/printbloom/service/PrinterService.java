package com.printbloom.service;

import com.printbloom.config.PrinterConfig;
import com.printbloom.model.PrintOrder;
import com.printbloom.model.PrintType;
import java.awt.print.PrinterJob;
import java.io.File;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Sides;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.stereotype.Service;

/**
 * Service responsible for sending documents to a real printer.
 *
 * IMPORTANT:
 * - Right now, all methods only have comments and empty bodies.
 * - This is intentional so that you can add the actual javax.print
 *   implementation step by step without feeling overwhelmed.
 *
 * The typical flow you will build later is:
 * 1. Find a PrintService (printer) to use.
 * 2. Open the PDF file as an InputStream.
 * 3. Wrap it into a Doc object (using the correct DocFlavor).
 * 4. Use DocPrintJob to submit the print job.
 */
@Service
public class PrinterService {

    private final PrinterConfig printerConfig;

    public PrinterService(PrinterConfig printerConfig) {
        this.printerConfig = printerConfig;
    }

    /**
     * High-level method: print the document for a given PrintOrder.
     *
     * Later, you will probably:
     * - Read order.getFilePath() to know which file to print.
     * - Read order.getPrintType() to choose black & white vs color (if your
     *   printer/driver supports that via attributes).
     * - Call a lower-level method that actually talks to javax.print.
     */
    public void printOrderDocument(PrintOrder order) {
        System.out.println("Initiating print job for order ID: " + order.getId());
        printPdfFile(order.getFilePath(), order.getPrintType(), order.getCopies(), order.getIsDuplex());
    }

    /**
     * Low-level helper: send a PDF file to the OS print system.
     *
     * When you implement this, you will:
     * - Use javax.print APIs to find a PrintService.
     * - Open the file located at filePath as an InputStream.
     * - Wrap it in a javax.print.Doc with an appropriate DocFlavor.
     * - Get a DocPrintJob from the PrintService and submit the Doc.
     *
     * NOTE: javax.print itself does not "understand" PDF content.
     * In practice, printing PDF correctly often depends on:
     * - The OS print spooler
     * - The installed printer driver
     * - Or using an external library to render PDF pages.
     *
     * For your mini project, it's okay to start simple and just
     * try sending the raw PDF stream to the printer.
     */
    public void printPdfFile(String filePath, PrintType printType, int copies, boolean isDuplex) {
        PrintService printService = findPrintService();
        if (printService == null) {
            System.err.println("No PrintService found. Skipping physical print action.");
            return;
        }

        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintService(printService);
            job.setPageable(new PDFPageable(document));

            PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
            attributes.add(new Copies(copies));
            if (isDuplex) {
                attributes.add(Sides.TWO_SIDED_LONG_EDGE); // Adjust as necessary
            } else {
                attributes.add(Sides.ONE_SIDED);
            }
            
            // Color printing vs B&W depends on the specific printer capability.
            // attributes.add(printType == PrintType.COLOR ? Chromaticity.COLOR : Chromaticity.MONOCHROME);

            job.print(attributes);
            System.out.println("Print job submitted successfully.");
        } catch (Exception e) {
            System.err.println("Failed to print document: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Helper method to choose which printer to use.
     *
     * When you implement this, you can:
     * - Use printerConfig.getPreferredPrinterName() to look for a printer
     *   with that name.
     * - If no name is configured, fall back to the system default printer.
     *
     * Returning PrintService (or null) lets you check in calling code
     * whether a suitable printer was found.
     */
    public PrintService findPrintService() {
        String preferred = printerConfig.getPreferredPrinterName();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

        if (preferred != null && !preferred.trim().isEmpty()) {
            for (PrintService service : services) {
                if (service.getName().equalsIgnoreCase(preferred)) {
                    return service;
                }
            }
        }

        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        if (defaultService != null) {
            return defaultService;
        }

        if (services.length > 0) {
            return services[0];
        }

        return null; 
    }

    // createPrintJob not necessary when using awt.print.PrinterJob with PDFBox
}

