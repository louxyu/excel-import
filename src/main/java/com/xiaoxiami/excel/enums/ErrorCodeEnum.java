package com.xiaoxiami.excel.enums;

public enum ErrorCodeEnum {
    E0000("E0000"),
    E1001("E1001"),
    E1002("E1002"),
    E1003("E1003"),
    E1004("E1004"),
    E2001("E2001"),
    E3001("E3001"),
    ;
    private String code;

    ErrorCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
