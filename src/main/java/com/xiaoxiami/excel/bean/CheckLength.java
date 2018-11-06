package com.xiaoxiami.excel.bean;

import com.xiaoxiami.excel.enums.ErrorCodeEnum;
import com.xiaoxiami.excel.message.ExcelImportErrorMessage;

public class CheckLength {

    /**
     * 允许最大长度
     */
    private Integer maxLength;
    /**
     * 允许最小长度
     */
    private Integer minLength;
    /**
     * 错误消息
     */
    private String errorMsg = ExcelImportErrorMessage.getMsg(ErrorCodeEnum.E1004.getCode());

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
