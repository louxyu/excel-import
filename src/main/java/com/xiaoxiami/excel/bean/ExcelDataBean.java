package com.xiaoxiami.excel.bean;

import com.xiaoxiami.excel.message.ExcelImportErrorMessage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelDataBean {
    //用于存放excel中数据的集合
    private List<SheetDataBean> sheetData = new ArrayList<>();

    //用于存放所有excel中数据的错误信息
    private List<ErrorBean> errors=new ArrayList<>();

    private StringBuilder errorCsv = new StringBuilder();

    private long errorCsvSize = 0;

    public List<SheetDataBean> getSheetData() {
        return sheetData;
    }

    public void setSheetData(List<SheetDataBean> sheetData) {
        this.sheetData = sheetData;
    }

    public List<ErrorBean> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorBean> errors) {
        this.errors = errors;
    }

    public void addError(ErrorBean errorBean) {
        if (errorBean != null) {
            addErrorCsv(errorBean);
            this.errors.add(errorBean);
        }
    }

    public void addAllError(List<ErrorBean> errors) {
        if (errors != null) {
            addErrorCsv(errors);
            this.errors.addAll(errors);
        }
    }

    public void addSheetData(SheetDataBean sheetDataBean) {
        this.sheetData.add(sheetDataBean);
    }

    public String getErrorCsv() {
        return errorCsv.toString();
    }

    public boolean isNoError(){
        return this.errors.isEmpty();
    }

    public InputStream getErrorCsvInputStream() throws Exception {
        ByteArrayOutputStream outputStream = getErrorCsvOutStream();
        this.errorCsvSize = outputStream.size();
        return new BufferedInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
    }


    public ByteArrayOutputStream getErrorCsvOutStream() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter bw = new OutputStreamWriter(outputStream,"UTF-16LE");
        bw.write(65279);
        bw.write(errorCsv.toString());
        bw.flush();
        this.errorCsvSize = outputStream.size();

        return outputStream;
    }

    public long getErrorCsvSize() {
        return errorCsvSize;
    }

    private void addErrorCsv(ErrorBean errorBean) {
        if (errorBean==null) return;

        if (errorCsv.length() == 0) {
            errorCsv.append(ExcelImportErrorMessage.getMsg("E0000"));
            errorCsv.append("\t");
            errorCsv.append("\n");
        }
        errorCsv.append(errorBean.getErrorCsv());
    }

    private void addErrorCsv(List<ErrorBean> errors) {
        if (errors != null && !errors.isEmpty()) {
            for (ErrorBean error : errors) {
                addErrorCsv(error);
            }
        }
    }
}
