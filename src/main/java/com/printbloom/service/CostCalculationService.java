package com.printbloom.service;

import com.printbloom.model.PrintType;
import org.springframework.stereotype.Service;

@Service
public class CostCalculationService {

    private static final double BLACK_WHITE_PRICE_PER_PAGE = 2.0;
    private static final double COLOR_PRICE_PER_PAGE = 5.0;

    public Double calculateCost(int pageCount, PrintType printType, int copies, boolean isDuplex) {
        if (pageCount <= 0 || copies <= 0) {
            return 0.0;
        }

        double baseCost = 0.0;
        if (printType == PrintType.BLACK_WHITE) {
            baseCost = pageCount * BLACK_WHITE_PRICE_PER_PAGE;
        } else if (printType == PrintType.COLOR) {
            baseCost = pageCount * COLOR_PRICE_PER_PAGE;
        }

        // if duplex could have a discount, we'd apply it here.
        // For now, we charge per printed page, multiplied by the number of copies.
        return baseCost * copies;
    }
}
