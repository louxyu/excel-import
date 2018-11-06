package com.xiaoxiami.excel.manager;

import com.xiaoxiami.excel.bean.CellBean;

import java.util.Map;

public interface IDataManager {

    /**
     * excel单元格值处理
     * @param cellBean
     * @return true值有效放入数据
     */
    boolean managerCellData(CellBean cellBean);

    /**
     * excel行值处理
     * @param rowIndex
     * @param rowDataMap
     * @return true 值有效加入配置
     */
    boolean managerRowData(int rowIndex,Map<String,String> rowDataMap);
}
