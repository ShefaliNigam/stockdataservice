package com.stockcharting.app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.stockcharting.app.entity.StockEntity;
import com.stockcharting.app.service.StockService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;
    @PostMapping("/bulk/upload")
    public ResponseEntity<String> uploadStocks(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file uploaded");
            }

            List<StockEntity> stocks = stockService.readStocksFromExcel(file);
            stockService.saveStocks(stocks);
            return ResponseEntity.ok("Stocks uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to upload stocks: " + e.getMessage());
        }
    }

    @GetMapping("/bulk/download")
    public ResponseEntity<byte[]> downloadStocks() {
        List<StockEntity> stocks = stockService.getAllStocks();
        byte[] excelFile = stockService.writeStocksToExcel(stocks);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stocks.xlsx");
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }
}

