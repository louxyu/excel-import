package com.xiaoxiami.excel.bean;

import com.xiaoxiami.excel.enums.ErrorCodeEnum;
import com.xiaoxiami.excel.message.ExcelImportErrorMessage;

public class CheckType {


    /**
     * 类型全名称
     */
    private String name;

    /**
     * 错误消息
     */
    private String errorMsg = ExcelImportErrorMessage.getMsg(ErrorCodeEnum.E2001.getCode());

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
