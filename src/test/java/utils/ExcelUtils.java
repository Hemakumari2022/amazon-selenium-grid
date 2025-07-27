package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private Workbook workbook;
    private Sheet sheet;

    public ExcelUtils(String excelPath, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(excelPath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get all employee names from the specified column index
    public List<String> getEmployeeNames(int columnIndex) {
        List<String> employeeNames = new ArrayList<>();
        
        if (sheet == null) {
            System.out.println("Sheet is null. Check if the sheet name is correct.");
            return employeeNames;
        }

        int rows = sheet.getPhysicalNumberOfRows();
        System.out.println("Total rows in sheet: " + rows);

        for (int i = 1; i < rows; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(columnIndex);
                if (cell != null) {
                    String name = cell.getStringCellValue();
                    System.out.println("Reading name from row " + i + ": " + name); // 👈 Debug print
                    employeeNames.add(name);
                } else {
                    System.out.println("Cell is null in row: " + i);
                }
            } else {
                System.out.println("Row is null at index: " + i);
            }
        }

        System.out.println("Final list of employee names: " + employeeNames); // 👈 Final check
        return employeeNames;
    }

    // Optional: close workbook
    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
