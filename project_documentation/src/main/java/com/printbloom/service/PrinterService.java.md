# `PrinterService.java`

## 1. Purpose
The `PrinterService` is the bridge between the digital web application and the physical hardware. It is responsible for taking a verified PDF file and sending it to a real, physical printer connected to the server.

## 2. Key Concepts

### Java Print Service API (`javax.print`)
Java has a built-in library for finding and communicating with printers. This service uses classes like `PrintServiceLookup` to search the computer's operating system for installed printer drivers.

### Apache PDFBox Printing (`PDFPageable`)
While Java knows how to send data to a printer, the printer doesn't necessarily know how to draw a PDF file on paper. Apache PDFBox provides a class called `PDFPageable` which takes the complex PDF document and renders it into standard printable image "pages" that any printer can understand.

## 3. Code Walkthrough

1. **`printOrderDocument(PrintOrder order)`**:
   - This is the high-level method called by the `PaymentController` when a payment succeeds. It extracts the file path, print type, copies, and duplex settings from the order and passes them to the lower-level method.

2. **`findPrintService()`**:
   - Looks at the `PrinterConfig` to see if a specific printer name was requested.
   - Asks the operating system (`PrintServiceLookup.lookupPrintServices()`) for a list of all attached printers.
   - If it finds the preferred printer, it returns it.
   - If not, it falls back to the system's default printer.

3. **`printPdfFile()`**:
   - This is where the physical printing happens.
   - It finds the target printer.
   - It loads the PDF document from the hard drive.
   - It sets up a `PrinterJob` and attaches the PDF to it using `PDFPageable`.
   - It builds an "Attribute Set" containing the user's preferences (like how many copies to print, and whether to print on both sides using `Sides.TWO_SIDED_LONG_EDGE`).
   - Finally, it calls `job.print(attributes)` to beam the data to the printer hardware.
