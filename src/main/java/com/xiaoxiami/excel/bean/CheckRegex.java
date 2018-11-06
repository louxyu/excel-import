package com.xiaoxiami.excel.bean;

import com.xiaoxiami.excel.enums.ErrorCodeEnum;
import com.xiaoxiami.excel.message.ExcelImportErrorMessage;

public class CheckRegex {

    /**
     *正则规则
     */
    private String regex;


    /**
     * 错误消息
     */
    private String errorMsg = ExcelImportErrorMessage.getMsg(ErrorCodeEnum.E3001.getCode());

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
