package com.xiaoxiami.excel.bean;

import java.util.HashMap;
import java.util.Map;

public class ExcelConfigBean {
    /**
     * excel配置唯一表示
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 对应sheet配置
     */
    private Map<String, SheetConfigBean> sheets = new HashMap<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, SheetConfigBean> getSheets() {
        return sheets;
    }

    public void addSheet(String key,SheetConfigBean sheetBean){
        this.sheets.put(key, sheetBean);
    }

    public SheetConfigBean getSheetConfigByIndex(String sheetIndex) {
        return this.sheets.get(sheetIndex);
    }
}
