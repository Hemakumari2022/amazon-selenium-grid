package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExclutilityEmpData {

    public static List<String> getEmployeeNames(String filePath, String sheetName) {
        List<String> names = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header
                Row row = sheet.getRow(i);
                names.add(row.getCell(0).getStringCellValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return names;
    }
}
