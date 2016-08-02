package br.com.emerson.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.emerson.model.Organization;

public class ExcelRead {

    private List<Organization> getXLSX(InputStream inputStream) {
        List<Organization> list = new ArrayList<Organization>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Organization organization = new Organization();
                list.add(organization);

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            organization.setName(cell.getStringCellValue());
                            break;
                        case 1:
                            organization.setStatus(cell.getBooleanCellValue());
                            break;
                    }
                }
            }
            inputStream.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<Organization> getXLS(InputStream inputStream) {
        List<Organization> list = new ArrayList<Organization>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Organization organization = new Organization();
                list.add(organization);

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            organization.setName(cell.getStringCellValue());
                            break;
                        case 1:
                            organization.setStatus(cell.getBooleanCellValue());
                            break;
                    }
                }
            }
            inputStream.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Organization> read(InputStream inputStream, ExcelType type) {
        List<Organization> list = new ArrayList<Organization>();
        try {
            if (type == ExcelType.XLSX) {
                list = getXLSX(inputStream);
            } else if (type == ExcelType.XLS) {
                list = getXLS(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
