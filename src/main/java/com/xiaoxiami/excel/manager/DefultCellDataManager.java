package com.xiaoxiami.excel.manager;

import com.xiaoxiami.excel.bean.CellBean;

import java.util.Map;

public class DefultCellDataManager implements IDataManager {
    @Override
    public boolean managerCellData(CellBean cellBean) {
        return true;
    }

    @Override
    public boolean managerRowData(int rowIndex, Map<String, String> rowDataMap) {
        return true;
    }
}
