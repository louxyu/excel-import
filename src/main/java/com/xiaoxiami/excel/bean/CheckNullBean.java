package com.xiaoxiami.excel.bean;

import com.xiaoxiami.excel.enums.ErrorCodeEnum;
import com.xiaoxiami.excel.message.ExcelImportErrorMessage;

public class CheckNullBean {
    /**
     * 是否允许为空
     */
    private Boolean notNull = false;

    /**
     * 错误消息
     */
    private String errorMsg = ExcelImportErrorMessage.getMsg(ErrorCodeEnum.E1001.getCode());

    public Boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(Boolean notNull) {
        this.notNull = notNull;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
