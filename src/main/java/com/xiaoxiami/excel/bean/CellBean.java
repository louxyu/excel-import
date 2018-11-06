package com.xiaoxiami.excel.bean;

public class CellBean {

    private String rowIndex;

    private String colIndex;

    private String defaultColName;

    private String cellValue;

    private String tempCellValue;

    /**
     * excel配置唯一表示
     */
    private String excelId;

    /**
     * 名称
     */
    private String excelName;

    /**
     * sheet名称
     */
    private String sheetName;

    /**
     * 数据封装bean
     */
    private String className;


    /**
     * 单元格对应的错误信息
     */
    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public ErrorBean createrError() {
        if(error==null){
            error = createErrorBean();
        }
        return error;
    }

    public String getDefaultColName() {
        return defaultColName;
    }

    public void setDefaultColName(String defaultColName) {
        this.defaultColName = defaultColName;
    }

    private ColConfigBean colConfigBean;

    public String getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(String rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getColIndex() {
        return colIndex;
    }

    public void setColIndex(String colIndex) {
        this.colIndex = colIndex;
    }

    public String getCellValue() {
        return cellValue;
    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }

    public ColConfigBean getColConfigBean() {
        return colConfigBean;
    }

    public void setColConfigBean(ColConfigBean colConfigBean) {
        this.colConfigBean = colConfigBean;
    }

    public String getExcelId() {
        return excelId;
    }

    public void setExcelId(String excelId) {
        this.excelId = excelId;
    }

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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTempCellValue() {
        return tempCellValue;
    }

    public void setTempCellValue(String tempCellValue) {
        this.tempCellValue = tempCellValue;
    }

    private ErrorBean createErrorBean() {
        ErrorBean error = new ErrorBean();
        error.setColName(this.getColConfigBean().getColName());
        error.setColNum(this.getColConfigBean().getIndex());
        error.setRowNum(this.getRowIndex());
        error.setFactLength(this.getCellValue() == null || this.getCellValue().isEmpty() ? "0" : String.valueOf(this.getCellValue().length()));
        error.setExcelName(this.getExcelName());
        error.setSheetName(this.getSheetName());
        error.setCellValue(this.getCellValue());
        error.setDefaultColName(this.getDefaultColName());

        if(this.getColConfigBean().getCheckBean().getCheckLength()!=null){
            error.setMaxLength(this.getColConfigBean().getCheckBean().getCheckLength().getMaxLength()==null?"":this.getColConfigBean().getCheckBean().getCheckLength().getMaxLength().toString());
            error.setMinLength(this.getColConfigBean().getCheckBean().getCheckLength().getMinLength()==null?"":this.getColConfigBean().getCheckBean().getCheckLength().getMinLength().toString());
        }

        if(this.getColConfigBean().getCheckBean().getCheckMaxLength()!=null){
            error.setMaxLength(this.getColConfigBean().getCheckBean().getCheckMaxLength().getMaxLength()==null?"":this.getColConfigBean().getCheckBean().getCheckMaxLength().getMaxLength().toString());
        }

        if(this.getColConfigBean().getCheckBean().getCheckMinLength()!=null){
            error.setMinLength(this.getColConfigBean().getCheckBean().getCheckMinLength().getMinLength()==null?"":this.getColConfigBean().getCheckBean().getCheckMinLength().getMinLength().toString());
        }
        return error;
    }
}
