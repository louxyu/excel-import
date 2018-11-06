package com.xiaoxiami.excel.config;

import com.xiaoxiami.excel.message.ExcelImportErrorMessage;

/**
 * 加载配置信息
 */
public class ExcelImportConfig {

    private static ExcelImportConfig excelImportConfig;

    public ExcelImportConfig() {
        this(null, (String[]) null);
    }

    public ExcelImportConfig(String errorMsgPath) {
        this(errorMsgPath, (String[]) null);
    }

    public ExcelImportConfig(String[] checkXmlPaths) {
        this(null, checkXmlPaths);
    }

    public ExcelImportConfig(String errorMsgPath, String checkXmlPath) {
        this(null, new String[]{checkXmlPath});
    }

    public ExcelImportConfig(String errorMsgPath, String[] checkXmlPaths) {
        this.errorMsgPath = errorMsgPath;
        this.checkXmlPaths = checkXmlPaths;
        ExcelImportErrorMessage.init(this);
        ExcelImportCheckConfig.init(this);
    }

    private String errorMsgPath;
    private String[] checkXmlPaths;


    public String getErrorMsgPath() {
        return errorMsgPath;
    }

    public void setErrorMsgPath(String errorMsgPath) {
        this.errorMsgPath = errorMsgPath;
    }

    public static ExcelImportConfig getExcelImportConfig() {
        return excelImportConfig;
    }

    public String[] getCheckXmlPaths() {
        return checkXmlPaths;
    }

    public void setCheckXmlPaths(String[] checkXmlPaths) {
        this.checkXmlPaths = checkXmlPaths;
    }
}
