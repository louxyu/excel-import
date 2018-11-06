package com.xiaoxiami.excel.util;

import org.apache.poi.ss.usermodel.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class ExcelUtil {
    private static DecimalFormat decimalFormat = new DecimalFormat("0");

    /**
     * 获取单元格数据
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String o = null;
        if(cell==null)return "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                o = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                short dateFormat = cell.getCellStyle().getDataFormat();
                if(dateFormat==31||dateFormat==58||dateFormat==32){
                    o = String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()));
                }else if (DateUtil.isCellDateFormatted(cell)) {
                    o = String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()));
                } else {
                    o = decimalFormat.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                o = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                o = cell.getCellFormula();
                break;
            default:
                o = "";
        }
        return o;
    }

    /**
     * 创建单元格
     *
     * @param wb
     * @param row
     * @param column
     * @param halign
     * @param valign
     * @param value
     */
    public static void createCell(Workbook wb, Row row, short column, short halign, short valign, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 获取一列对应的字母。例如：ColumnNum=1，则返回值为A 列号转字母
     */
    public static String getColumnName(int columnNum) {
        int first;
        int last;
        String result = "";
        if (columnNum > 256)
            columnNum = 256;
        first = columnNum / 27;
        last = columnNum - (first * 26);

        if (first > 0)
            result = String.valueOf((char) (first + 64));

        if (last > 0)
            result = result + String.valueOf((char) (last + 64));

        return result;
    }
}
