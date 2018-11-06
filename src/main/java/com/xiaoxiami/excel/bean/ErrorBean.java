package com.xiaoxiami.excel.bean;

import com.xiaoxiami.excel.message.ExcelImportErrorMessage;

import java.util.ArrayList;
import java.util.List;

public class ErrorBean {
    /**
     * Excel名称
     */
    private String excelName = "";

    /**
     * sheet名称
     */
    private String sheetName = "";

    /**
     * 列名
     */
    private String colName = "";

    /**
     * 默认列序号
     */
    private String defaultColName = "";

    /**
     * 行号
     */
    private String rowNum = "";

    /**
     * 列号
     */
    private String colNum = "";

    /**
     * 允许最小长度
     */
    private String minLength = "";

    /**
     * 允许最大长度
     */
    private String maxLength = "";

    /**
     * 输入值长度
     */
    private String factLength = "";

    /**
     * 单元格值
     */
    private String cellValue = "";

    /**
     * 错误消息
     */
    private List<String> errorMsgs = new ArrayList<>();

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getCellValue() {
        return cellValue;
    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum =(Integer.valueOf(rowNum)+1)+"";
    }

    public String getColNum() {
        return colNum;
    }

    public void setColNum(String colNum) {
        this.colNum = (Integer.valueOf(colNum)+1)+"";
    }

    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public String getFactLength() {
        return factLength;
    }

    public void setFactLength(String factLength) {
        this.factLength = factLength;
    }

    public List<String> getErrorMsgs() {
        return errorMsgs;
    }

    public ErrorBean addErrorMsg(String msg) {
        this.errorMsgs.add(msg);
        return this;
    }

    public ErrorBean addErrorMsgCode(String code) {
        this.addErrorMsg(ExcelImportErrorMessage.getMsg(code));
        return this;
    }

    public ErrorBean addExtendErrorMsg(ExtendErrorMsg extendErrorMsg) {
        this.addErrorMsg(extendErrorMsg.getErrorMsg());
        return this;
    }

    public String getDefaultColName() {
        return defaultColName;
    }

    public void setDefaultColName(String defaultColName) {
        this.defaultColName = defaultColName;
    }

    public StringBuilder getErrorCsv() {
        StringBuilder sb = new StringBuilder();
        for (String errorMsg : errorMsgs) {
            sb.append(errorMsg.replace("{sheetName}", this.sheetName)
                    .replace("{rowNum}", this.rowNum)
                    .replace("{colName}", this.colName)
                    .replace("{minLength}", this.minLength)
                    .replace("{maxLength}", this.maxLength)
                    .replace("{factLength}", this.factLength)
                    .replace("{cellValue}", this.cellValue)
                    .replace("{defaultColName}", this.defaultColName)
                    .replace("{t}", "\t")
            );
            sb.append("\t");
            sb.append("\n");
        }
        return sb;
    }
}
