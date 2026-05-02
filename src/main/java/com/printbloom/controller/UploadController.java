package com.printbloom.controller;

import com.printbloom.model.PrintType;
import com.printbloom.service.CostCalculationService;
import com.printbloom.service.FileStorageService;
import com.printbloom.service.PdfProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/print")
public class UploadController {

    private final FileStorageService fileStorageService;
    private final PdfProcessingService pdfProcessingService;
    private final CostCalculationService costCalculationService;

    public UploadController(FileStorageService fileStorageService,
                            PdfProcessingService pdfProcessingService,
                            CostCalculationService costCalculationService) {
        this.fileStorageService = fileStorageService;
        this.pdfProcessingService = pdfProcessingService;
        this.costCalculationService = costCalculationService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile file,
                                            @RequestParam("printType") PrintType printType,
                                            @RequestParam(value = "copies", defaultValue = "1") int copies,
                                            @RequestParam(value = "isDuplex", defaultValue = "false") boolean isDuplex) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty or missing.");
        }

        Path savedFile = fileStorageService.storeFile(file);
        
        if (!pdfProcessingService.validatePdf(savedFile)) {
            return ResponseEntity.badRequest().body("Invalid or corrupted PDF.");
        }

        int pageCount = pdfProcessingService.countPages(savedFile);
        Double estimatedCost = costCalculationService.calculateCost(pageCount, printType, copies, isDuplex);

        Map<String, Object> response = new HashMap<>();
        response.put("fileName", file.getOriginalFilename());
        response.put("pageCount", pageCount);
        response.put("estimatedCost", estimatedCost);
        response.put("printType", printType);

        return ResponseEntity.ok(response);
    }
}
