package com.xiaoxiami.excel.bean;

import com.xiaoxiami.excel.message.ExcelImportErrorMessage;

import java.util.HashMap;
import java.util.Map;

public class ExtendErrorMsg {

    /**
     * 扩展错误信息
     */
    private Map<String, String> extendErrorValue = new HashMap<>();

    public ExtendErrorMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 错误code
     */
    private String code;

    /**
     * 错误消息
     */
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ExtendErrorMsg addExtendErrorValue(String key,String value) {
        this.extendErrorValue.put(key, value);
        return this;
    }

    public String getErrorMsg(){
        String tempErrorMsg= ExcelImportErrorMessage.getMsg(this.getCode(),false);
        if(tempErrorMsg==null || tempErrorMsg.isEmpty()){
            tempErrorMsg =ExcelImportErrorMessage.getMsg(this.getMsg());
        }
        if(tempErrorMsg!=null && !tempErrorMsg.isEmpty()){
            for (Map.Entry<String, String> entry : extendErrorValue.entrySet()) {
                tempErrorMsg = tempErrorMsg.replace("{" + entry.getKey() + "}", entry.getValue());
            }
            return tempErrorMsg;
        }else{
            return null;
        }
    }


}
