package com.printbloom.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class PdfProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(PdfProcessingService.class);

    public int countPages(Path pdfPath) {
        File file = pdfPath.toFile();
        try (PDDocument document = PDDocument.load(file)) {
            return document.getNumberOfPages();
        } catch (IOException e) {
            logger.error("Failed to count pages for PDF: " + pdfPath, e);
            return 0;
        }
    }

    public boolean validatePdf(Path pdfPath) {
        File file = pdfPath.toFile();
        try (PDDocument document = PDDocument.load(file)) {
            return true;
        } catch (IOException e) {
            logger.warn("Invalid or corrupted PDF file: " + pdfPath, e);
            return false;
        }
    }
}
