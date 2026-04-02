package com.project.sleepfatigue.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class DataService {

    private List<Map<String, Object>> dataset = new ArrayList<>();
    private Random random = new Random();

    public DataService() {
        loadData();
    }

    private void loadData() {
        try {
            // ✅ Load file from project root
            File file = new File("sleep_data.xlsx");

            if (!file.exists()) {
                throw new RuntimeException("Excel file NOT found! Put it in project root.");
            }

            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            // ✅ Read header row
            Row header = sheet.getRow(0);
            Map<String, Integer> columnMap = new HashMap<>();

            for (Cell cell : header) {
                columnMap.put(cell.getStringCellValue().trim(), cell.getColumnIndex());
            }

            System.out.println("Columns found: " + columnMap.keySet());

            // ✅ Read data rows
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                try {
                    Map<String, Object> data = new HashMap<>();

                    // Safe extraction
                    double sleep = getNumericValue(row, columnMap.get("Sleep Duration"));
                    double activity = getNumericValue(row, columnMap.get("Physical Activity Level"));
                    double heart = getNumericValue(row, columnMap.get("Heart Rate"));

                    data.put("sleep", sleep);
                    data.put("activity", activity);
                    data.put("heart", heart);

                    dataset.add(data);

                } catch (Exception e) {
                    // skip bad rows
                }
            }

            System.out.println("Dataset loaded successfully: " + dataset.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Safe cell reader (no crash)
    private double getNumericValue(Row row, Integer colIndex) {
        if (colIndex == null) return 0;

        Cell cell = row.getCell(colIndex);
        if (cell == null) return 0;

        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        }

        if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue());
            } catch (Exception e) {
                return 0;
            }
        }

        return 0;
    }

    // ✅ Get random row
    public Map<String, Object> getRandomData() {
        if (dataset.isEmpty()) {
            throw new RuntimeException("Dataset is EMPTY! Check column names or file.");
        }
        return dataset.get(random.nextInt(dataset.size()));
    }
}