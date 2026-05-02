package com.printbloom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Basic configuration holder for printer-related settings.
 *
 * As a beginner, you can think of this class as a place where
 * you read values from application.properties and then inject
 * them into services that need them.
 */
@Configuration
public class PrinterConfig {

    /**
     * Name of the preferred printer.
     *
     * If this is left empty, the PrinterService can later fall back
     * to using the system's default printer.
     */
    @Value("${printbloom.printer.preferred-name:}")
    private String preferredPrinterName;

    public String getPreferredPrinterName() {
        return preferredPrinterName;
    }
}

