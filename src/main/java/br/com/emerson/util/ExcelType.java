package br.com.emerson.util;

public enum ExcelType {

    XLS("xls"), XLSX("xlsx");

    private final String extension;

    ExcelType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return this.extension;
    }

    public static boolean contains(String extension) {
        for(ExcelType excel:values()) {
            if (excel.name().equals(extension)) {
                return true;
            }
        }
        return false;
    }
}
