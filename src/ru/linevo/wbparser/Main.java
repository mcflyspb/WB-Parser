package ru.linevo.wbparser;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        File f = new File("C:\\Users\\d.kalashnikov\\Downloads");
        String lastWbFile = findLastWbFile(f);
        parseFile(new File("C:\\Users\\d.kalashnikov\\Downloads\\"  + lastWbFile));
        System.out.println();
        System.out.println(lastWbFile);
    }

    private static void parseFile(File file) {
        try {
            FileInputStream fs = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fs);
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;

            List<String> art = new ArrayList<>();
            List<Integer> count = new ArrayList<>();


            int rows; // No of rows
            int cells;
            rows = sheet.getPhysicalNumberOfRows();

            for (int i = 2; i < rows; i++) {
                row = sheet.getRow(i);
                //cells = sheet.getRow(i).getPhysicalNumberOfCells();
                //System.out.println(row.getCell(5) + " " + row.getCell(7) + " " + row.getCell(8) + " " + row.getCell(16));
                String artikul = row.getCell(5).toString() + "-" + row.getCell(8).toString();
                String stringCount = row.getCell(16).toString();
                int cellCeount = (int)Float.parseFloat(stringCount);
                putToArray(art, count, artikul, cellCeount);
            }

            for (int i = 0; i < art.size(); i++) {
                System.out.println(art.get(i) + " " + count.get(i));
            }

        } catch(Exception ioe) {
            ioe.printStackTrace();
        }
    }

    private static void putToArray(List<String> art, List<Integer> integers, String artikul, int count) {
        boolean n = true;
        for (int i = 0; i < art.size(); i++) {
            if (Objects.equals(art.get(i), artikul)) {
                integers.set(i,integers.get(i) + count);
                n = false;
            }
        }
        if (n) {
            art.add(artikul);
            integers.add(count);
        }

    }

    private static String findLastWbFile(File f) {
        String[] paths;
        int tempDate = 0;
        int currentDate = 0;
        String lastFile = "";
        try {
            // array of files and directory
            paths = f.list();
            // for each name in the path array
            for(String path : paths) {
                // prints filename and directory name
                if (Objects.equals(path.substring(0,8)   ,"supplier") && path.length() > 20) {
                    String[] fileToChar = path.split("-");
                    currentDate = 10000 * Integer.parseInt(fileToChar[6]) + 100 * Integer.parseInt(fileToChar[7]) + Integer.parseInt(fileToChar[8]);
                    if (currentDate > tempDate) {
                        lastFile = path;
                    }
                    //System.out.println(fileToChar[6] + " " + fileToChar[7] + " " + fileToChar[8]);
                }
            }
        } catch(Exception e) {
            // if any error occurs
            e.printStackTrace();
        }
        return lastFile;
    }
}