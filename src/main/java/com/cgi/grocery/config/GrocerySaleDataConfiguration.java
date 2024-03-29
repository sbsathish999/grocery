package com.cgi.grocery.config;

import com.cgi.grocery.modal.PriceData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
public class GrocerySaleDataConfiguration {
    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${grocery.file.name}")
    private String fileName;

    @Value("${grocery.file.path}")
    private String filePath;

    private static List<PriceData> priceDataList;

    public synchronized List<PriceData> read() {
        if(priceDataList == null) {
            priceDataList = new ArrayList<>();
            try {
                Resource resource = resourceLoader.getResource("classpath:" + filePath + File.separator + fileName);
                OPCPackage container = OPCPackage.open(resource.getFilename());
                XSSFWorkbook workbook = new XSSFWorkbook(container);
                XSSFSheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    String itemName = getItemName(row.getCell(1));
                    itemName = itemName != null ? itemName.toUpperCase() : null;
                    Date date = getDate(row.getCell(2));
                    Float itemPrice = getItemPrice(row.getCell(3));
                    if (itemName != null && date != null && itemPrice != null) {
                        PriceData priceData = new PriceData(itemName, date, itemPrice);
                        priceDataList.add(priceData);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("unexpected error : " + e.getMessage());
            }
        }
        return priceDataList;
    }

    private String getItemName(Cell itemCell) {
        String itemName = null;
        if(itemCell != null && itemCell.getCellType().equals(CellType.STRING)) {
            itemName = itemCell.getStringCellValue().trim();
        }
        return itemName;
    }

    private Float getItemPrice(Cell priceCell) {
        Float itemPrice = null;
        if(priceCell != null && priceCell.getCellType().equals(CellType.STRING)){
            String priceString = priceCell.getStringCellValue().trim();
            if(priceString != null && !priceString.isEmpty() && !priceString.equalsIgnoreCase("null")) {
                try {
                    itemPrice = Float.valueOf(priceString);
                }catch (Exception e) {
                    log.error("unexpected error : " + e.getMessage());
                }
            }
        } else if(priceCell != null && priceCell.getCellType().equals(CellType.NUMERIC)) {
            try {
                itemPrice = Float.valueOf(String.valueOf(priceCell.getNumericCellValue()).trim());
            } catch (Exception e) {
                log.error("unexpected error : " + e.getMessage());
            }
        }
        return itemPrice;
    }

    private Date getDate(Cell dateCell) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat slashformat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        if(dateCell.getCellType().equals(CellType.STRING)) {
            String dateString = dateCell.getStringCellValue().trim();
            if(dateString != null && !dateString.isEmpty() && !dateString.equalsIgnoreCase("null")) {
                try {
                    if(dateString.contains("-"))
                        date = format.parse(dateString);
                    else
                        date = slashformat.parse(dateString);
                } catch (ParseException e) {
                    log.error("unexpected error : " + e.getMessage());
                }
            }
        } else if(dateCell.getCellType().equals(CellType.NUMERIC)) {
            try{
                date = dateCell.getDateCellValue();
            } catch (Exception e) {
                log.error("unexpected error : " + e.getMessage());
            }
        }
        return date;
    }
}
