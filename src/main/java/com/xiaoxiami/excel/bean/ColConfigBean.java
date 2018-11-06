package com.xiaoxiami.excel.bean;

public class ColConfigBean {
	/**
	 * 索引
	 */
	private String index;

	/**
	 * 对应属性名称
	 */
	private String name;

	/**
	 * 列名
	 */
	private String colName;

    /**
     * 校验规则
     */
    private CheckConfigBean checkBean;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public CheckConfigBean getCheckBean() {
        return checkBean;
    }

    public void setCheckBean(CheckConfigBean checkBean) {
        this.checkBean = checkBean;
    }
}
