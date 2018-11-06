package com.xiaoxiami.excel.manager;

import com.xiaoxiami.excel.bean.CellBean;
import com.xiaoxiami.excel.bean.ExtendErrorMsg;

public interface ICheckCellValueManager {
    String checkValue(CellBean cellBean);

    ExtendErrorMsg checkCellValue(CellBean cellBean);
}
