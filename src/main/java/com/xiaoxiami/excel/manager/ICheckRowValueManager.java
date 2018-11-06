package com.xiaoxiami.excel.manager;


import com.xiaoxiami.excel.bean.CellBean;

import java.util.Map;

public interface ICheckRowValueManager {
    void checkRowValue(int rowIndex, Map<String, CellBean> rowDataMap);
}
