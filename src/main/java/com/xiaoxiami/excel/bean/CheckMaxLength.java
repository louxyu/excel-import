package com.xiaoxiami.excel.bean;

import com.xiaoxiami.excel.enums.ErrorCodeEnum;
import com.xiaoxiami.excel.message.ExcelImportErrorMessage;

public class CheckMaxLength {

    /**
     * 允许最大长度
     */
    private Integer maxLength;

    /**
     * 错误消息
     */
    private String errorMsg = ExcelImportErrorMessage.getMsg(ErrorCodeEnum.E1002.getCode());

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
