package com.xiaoxiami.excel.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 对应校验配置sheet数据
 */
public class SheetConfigBean {
    /**
     * sheet名称
     */
    private String sheetName;
    /**
     * 索引
     */
    private String index;

    /**
     * 数据封装bean
     */
    private String className;

    /**
     * 数据处理类名
     */
    private String manageClassName;

    /**
     * 列总数
     */
    private int colCount;

    /**
     * 行开始索引
     */
    private int rowStartIndex;

    /**
     * 列开始索引
     */
    private int colStartIndex;

    /**
     * 对应列
     */
    private Map<String,ColConfigBean> colMap=new HashMap<>();

    public ColConfigBean getColConfigBean(String key){
        return colMap.get(key);
    }
    public String getSheetName() {
        return sheetName;
    }
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
    public String getIndex() {
        return index;
    }
    public void setIndex(String index) {
        this.index = index;
    }
    public Map<String, ColConfigBean> getColMap() {
        return colMap;
    }
    public void setColMap(Map<String, ColConfigBean> colMap) {
        this.colMap = colMap;
    }
    public void addCol(String key,ColConfigBean cb){
        this.colMap.put(key, cb);
    }
    public int getColCount() {
        return colCount;
    }
    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getRowStartIndex() {
        return rowStartIndex;
    }

    public void setRowStartIndex(int rowStartIndex) {
        this.rowStartIndex = rowStartIndex;
    }

    public int getColStartIndex() {
        return colStartIndex;
    }

    public void setColStartIndex(int colStartIndex) {
        this.colStartIndex = colStartIndex;
    }

    public String getManageClassName() {
        return manageClassName;
    }

    public void setManageClassName(String manageClassName) {
        this.manageClassName = manageClassName;
    }
}
