package com.xiaoxiami.excel.manager;

import com.xiaoxiami.excel.bean.CellBean;
import com.xiaoxiami.excel.bean.ExtendErrorMsg;

public class DefultCheckCellValueManager implements ICheckCellValueManager{
    @Override
    public String checkValue(CellBean cellBean) {
        return null;
    }

    @Override
    public ExtendErrorMsg checkCellValue(CellBean cellBean) {
        return null;
    }
}
