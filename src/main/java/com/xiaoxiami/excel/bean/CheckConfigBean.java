package com.xiaoxiami.excel.bean;

/**
 * 校验规则类
 */
public class CheckConfigBean {

    /**
     * 空值校验
     */
    private CheckNullBean checkNullBean;

    /**
     * 长度校验
     */
    private CheckLength checkLength;
    /**
     * 长度校验
     */
    private CheckMaxLength checkMaxLength;
    /**
     * 长度校验
     */
    private CheckMinLength checkMinLength;

    /**
     * 类型校验
     */
    private CheckType checkType;

    /**
     * 正则校验
     */
    private CheckRegex[] checkRegex;

    public CheckNullBean getCheckNullBean() {
        return checkNullBean;
    }

    public void setCheckNullBean(CheckNullBean checkNullBean) {
        this.checkNullBean = checkNullBean;
    }

    public CheckLength getCheckLength() {
        return checkLength;
    }

    public void setCheckLength(CheckLength checkLength) {
        this.checkLength = checkLength;
    }

    public CheckType getCheckType() {
        return checkType;
    }

    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }

    public CheckRegex[] getCheckRegex() {
        return checkRegex;
    }

    public void setCheckRegex(CheckRegex[] checkRegex) {
        this.checkRegex = checkRegex;
    }

    public CheckMaxLength getCheckMaxLength() {
        return checkMaxLength;
    }

    public void setCheckMaxLength(CheckMaxLength checkMaxLength) {
        this.checkMaxLength = checkMaxLength;
    }

    public CheckMinLength getCheckMinLength() {
        return checkMinLength;
    }

    public void setCheckMinLength(CheckMinLength checkMinLength) {
        this.checkMinLength = checkMinLength;
    }
}
