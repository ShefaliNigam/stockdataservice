package com.stockcharting.app.service.test;
import com.stockcharting.app.entity.StockEntity;
import com.stockcharting.app.repository.StockRepository;
import com.stockcharting.app.service.StockService;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    public void setUp() {
        stockService = new StockService();
		/*
		 * stockService.stockRepository = stockRepository; stockService.restTemplate =
		 * restTemplate;
		 */
    }

    @Test
    public void testSaveStocks() {
        List<StockEntity> stocks = Arrays.asList(
                new StockEntity(1L, "Company A", 100.0, "NYSE", "COMPA", 10, 1),
                new StockEntity(2L, "Company B", 150.0, "NASDAQ", "COMPB", 20, 2)
        );

       // stockService.saveStocks(stocks);

      //  verify(stockRepository, times(1)).saveAll(stocks);
    }

	/*
	 * @Test public void testGetAllStocks() { StockEntity[] stocksArray = new
	 * StockEntity[]{ new StockEntity(1L, "Company A", 100.0, "NYSE", "COMPA", 10,
	 * 1), new StockEntity(2L, "Company B", 150.0, "NASDAQ", "COMPB", 20, 2) };
	 */

       // when(restTemplate.getForObject("http://localhost:9093/api/stocks/", StockEntity[].class))
               // .thenReturn(stocksArray);

     //   List<StockEntity> result = stockService.getAllStocks();

      /*  assertEquals(2, result.size());
        assertEquals("Company A", result.get(0).getName());
        verify(restTemplate, times(1)).getForObject("http://localhost:9093/api/stocks/", StockEntity[].class);
    }*/

   // @Test
	/*
	 * public void testReadStocksFromExcel() throws IOException { String
	 * excelContent = """ Name,Exchange,Price,Symbol Company A,NYSE,100.0,COMPA
	 * Company B,NASDAQ,150.0,COMPB """;
	 */
     //   MockMultipartFile file = new MockMultipartFile("file", "stocks.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", excelContent.getBytes());

       // List<StockEntity> stocks = stockService.readStocksFromExcel(file);

    /*    assertEquals(2, stocks.size());
        assertEquals("Company A", stocks.get(0).getName());
        assertEquals("NASDAQ", stocks.get(1).getExchange());
    }*/

    @Test
    public void testWriteStocksToExcel() throws IOException {
        List<StockEntity> stocks = Arrays.asList(
                new StockEntity(1L, "Company A", 100.0, "NYSE", "COMPA", 10, 1),
                new StockEntity(2L, "Company B", 150.0, "NASDAQ", "COMPB", 20, 2)
        );

        byte[] excelData = stockService.writeStocksToExcel(stocks);

        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelData))) {
            assertEquals(1, workbook.getNumberOfSheets());
            assertEquals("Stocks", workbook.getSheetName(0));

           // assertEquals(3, workbook.getSheetAt(0).getLastRowNum());
            assertEquals("Name", workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
            assertEquals("Company A", workbook.getSheetAt(0).getRow(1).getCell(0).getStringCellValue());
        }
    }
}
