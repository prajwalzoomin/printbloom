package com.printbloom.service;

import com.printbloom.dto.PrintOrderResponseDTO;
import com.printbloom.model.PrintOrder;
import com.printbloom.model.PrintStatus;
import com.printbloom.model.PrintType;
import com.printbloom.repository.PrintOrderRepository;
import com.printbloom.exception.FileStorageException;
import com.printbloom.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrintOrderService {

    private final FileStorageService fileStorageService;
    private final PdfProcessingService pdfProcessingService;
    private final CostCalculationService costCalculationService;
    private final QueueManagementService queueManagementService;
    private final PrintOrderRepository printOrderRepository;

    public PrintOrderService(FileStorageService fileStorageService,
                             PdfProcessingService pdfProcessingService,
                             CostCalculationService costCalculationService,
                             QueueManagementService queueManagementService,
                             PrintOrderRepository printOrderRepository) {
        this.fileStorageService = fileStorageService;
        this.pdfProcessingService = pdfProcessingService;
        this.costCalculationService = costCalculationService;
        this.queueManagementService = queueManagementService;
        this.printOrderRepository = printOrderRepository;
    }

    public PrintOrderResponseDTO createPrintOrder(MultipartFile file, PrintType printType, int copies, boolean isDuplex) {
        if (file == null || file.isEmpty() || printType == null || copies < 1) {
            throw new IllegalArgumentException("File, PrintType, and valid copies must be provided.");
        }

        // 1. Validate and store the file
        Path savedPath = fileStorageService.storeFile(file);
        
        // 2 & 3. Process the PDF and count pages
        if (!pdfProcessingService.validatePdf(savedPath)) {
            throw new FileStorageException("Invalid or corrupted PDF file.");
        }
        int pageCount = pdfProcessingService.countPages(savedPath);
        
        // 4. Calculate cost
        Double cost = costCalculationService.calculateCost(pageCount, printType, copies, isDuplex);

        // 5. Create new PrintOrder entity
        PrintOrder order = new PrintOrder();
        order.setFileName(file.getOriginalFilename());
        order.setFilePath(savedPath.toString());
        order.setPageCount(pageCount);
        order.setPrintType(printType);
        order.setCopies(copies);
        order.setIsDuplex(isDuplex);
        order.setCost(cost);
        order.setStatus(PrintStatus.AWAITING_PAYMENT); // Initially hidden from the live Queue
        order.setCreatedAt(LocalDateTime.now());

        // 6. Save PrintOrder to DB
        PrintOrder savedOrder = printOrderRepository.save(order);
        
        // 7. Get queue position
        int queuePosition = queueManagementService.getQueuePosition(savedOrder.getId());

        // 8. Return response DTO
        return new PrintOrderResponseDTO(
                savedOrder.getId(),
                savedOrder.getStatus().name(),
                savedOrder.getCost(),
                queuePosition,
                savedOrder.getCopies(),
                savedOrder.getIsDuplex()
        );
    }

    public List<PrintOrder> getOrderHistory() {
        return printOrderRepository.findAll();
    }

    public PrintOrderResponseDTO reprintOrder(Long originalOrderId) {
        PrintOrder originalOrder = getOrderById(originalOrderId);

        PrintOrder newOrder = new PrintOrder();
        newOrder.setFileName(originalOrder.getFileName());
        newOrder.setFilePath(originalOrder.getFilePath());
        newOrder.setPageCount(originalOrder.getPageCount());
        newOrder.setPrintType(originalOrder.getPrintType());
        newOrder.setCopies(originalOrder.getCopies());
        newOrder.setIsDuplex(originalOrder.getIsDuplex());
        newOrder.setCost(originalOrder.getCost());
        newOrder.setStatus(PrintStatus.AWAITING_PAYMENT); // Initially hidden from the live Queue
        newOrder.setCreatedAt(LocalDateTime.now());

        PrintOrder savedOrder = printOrderRepository.save(newOrder);
        int queuePosition = queueManagementService.getQueuePosition(savedOrder.getId());

        return new PrintOrderResponseDTO(
                savedOrder.getId(),
                savedOrder.getStatus().name(),
                savedOrder.getCost(),
                queuePosition,
                savedOrder.getCopies(),
                savedOrder.getIsDuplex()
        );
    }

    public PrintOrder getOrderById(Long orderId) {
        return printOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("PrintOrder not found with id: " + orderId));
    }
}
