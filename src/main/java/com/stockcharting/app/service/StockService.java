package com.stockcharting.app.service;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.stockcharting.app.entity.StockEntity;
import com.stockcharting.app.repository.StockRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private RestTemplate restTemplate;

   
    public void saveStocks(List<StockEntity> stockEntities) {
        stockRepository.saveAll(stockEntities);
    }

    
    public List<StockEntity> getAllStocks() {

    	
        String url = "http://localhost:9093/api/stocks/" ;

        StockEntity[] stocks = restTemplate.getForObject(url, StockEntity[].class);

       
        return Arrays.asList(stocks);
    	

    }

    public List<StockEntity> readStocksFromExcel(MultipartFile file) throws IOException {
        List<StockEntity> stocks = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                StockEntity stock = new StockEntity();
                stock.setName(row.getCell(0).getStringCellValue());
                stock.setExchange(row.getCell(1).getStringCellValue());
                stock.setPrice(row.getCell(2).getNumericCellValue());
                stock.setSymbol(row.getCell(3).getStringCellValue());

                stocks.add(stock);
            }
        }
        return stocks;
    }

    
    public byte[] writeStocksToExcel(List<StockEntity> stocks) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Stocks");

         
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Name");
            header.createCell(1).setCellValue("Exchange");
            header.createCell(2).setCellValue("Price");
            header.createCell(3).setCellValue("Symbol");

            int rowNum = 1;
            for (StockEntity stock : stocks) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(stock.getName());
                row.createCell(1).setCellValue(stock.getExchange());
                row.createCell(2).setCellValue(stock.getPrice());
                row.createCell(3).setCellValue(stock.getSymbol());
            }

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                workbook.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write stocks to Excel", e);
        }
    }
}
