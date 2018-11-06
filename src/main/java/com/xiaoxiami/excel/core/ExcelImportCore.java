package com.xiaoxiami.excel.core;

import com.xiaoxiami.excel.bean.*;
import com.xiaoxiami.excel.config.ExcelImportCheckConfig;
import com.xiaoxiami.excel.enums.ExcelTypeEnum;
import com.xiaoxiami.excel.manager.*;
import com.xiaoxiami.excel.message.ExcelImportErrorMessage;
import com.xiaoxiami.excel.util.ExcelUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelImportCore {


    static {
        DateConverter dateConverter = new DateConverter();
        dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss","yyyy年MM月dd日","yyyy/MM/dd","yyyy年MM月dd"});
        ConvertUtils.register(dateConverter, Date.class);
    }

    public static ExcelDataBean excelManager(InputStream in, String excelId, Map<String, IDataManager> dataManagerMap, Map<String,ICheckCellValueManager> checkCellValueManagerMap) throws Exception {
        return excelManager(in, excelId, dataManagerMap,checkCellValueManagerMap, ExcelTypeEnum.XLSX);
    }

    public static ExcelDataBean excelManager(InputStream in, String excelId,Map<String,IDataManager> dataManagerMap, Map<String,ICheckCellValueManager> checkCellValueManagerMap, ExcelTypeEnum excelTypeEnum) throws Exception {
        return excelManager(in, excelId, dataManagerMap,checkCellValueManagerMap,null, ExcelTypeEnum.XLSX);
    }

    public static ExcelDataBean excelManager(InputStream in, String excelId, Map<String,IDataManager> dataManagerMap, Map<String,ICheckCellValueManager> checkCellValueManagerMap, Map<String,ICheckRowValueManager> checkRowValueManagerMap, ExcelTypeEnum excelTypeEnum) throws Exception {

        ExcelDataBean excelDataBean = new ExcelDataBean();

        ExcelConfigBean excelConfigBean = ExcelImportCheckConfig.getExcelConfigById(excelId);

        Workbook workbook = null;

        if(ExcelTypeEnum.XLSX.equals(excelTypeEnum)){
            workbook = new XSSFWorkbook(in);
        }else{
            workbook = new HSSFWorkbook(in);
        }

        SheetDataBean sheetData = null;

        CellBean cellBean = null;
        Class<?> dataBeanClass = null;
        Class<?> manageClass = null;
        IDataManager dataManager = new DefultCellDataManager();

        ICheckCellValueManager checkCellValueManager = new DefultCheckCellValueManager();
        ICheckRowValueManager checkRowValueManager = new DefultCheckRowValueManager();

        ColConfigBean colConfigBean = null;

        Integer colIndex = null;

        Map<String, String> tempDataMap = null;
        Map<String, CellBean> tempCellBeansMap = null;

        String tempValues = null;

        List<ErrorBean> tempErrors =null;

        Object rowData = null;

        Sheet sheet = null;
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            sheet = workbook.getSheetAt(sheetIndex);

            SheetConfigBean sheetConfigBean =excelConfigBean.getSheetConfigByIndex(String.valueOf(sheetIndex));
            if(sheetConfigBean!=null){

                String rowDataClassName = sheetConfigBean.getClassName();
                if (rowDataClassName != null && !rowDataClassName.trim().isEmpty()) {
                    dataBeanClass= Class.forName(rowDataClassName);
                }
                String manageDataClassName = sheetConfigBean.getManageClassName();
                if (manageDataClassName != null && !manageDataClassName.trim().isEmpty()) {
                    manageClass= Class.forName(manageDataClassName);
                    dataManager = (IDataManager) manageClass.newInstance();
                }

                if (dataManagerMap != null && dataManagerMap.get(String.valueOf(sheetIndex)) != null) {
                    dataManager = dataManagerMap.get(String.valueOf(sheetIndex));
                }

                if (checkCellValueManagerMap != null && checkCellValueManagerMap.get(String.valueOf(sheetIndex)) != null) {
                    checkCellValueManager = checkCellValueManagerMap.get(String.valueOf(sheetIndex));
                }

                if (checkRowValueManagerMap != null && checkRowValueManagerMap.get(String.valueOf(sheetIndex)) != null) {
                    checkRowValueManager = checkRowValueManagerMap.get(String.valueOf(sheetIndex));
                }

                sheetData = new SheetDataBean();

                String sheetName=sheet.getSheetName();

                sheetData.setIndex(String.valueOf(sheetIndex));

                sheetData.setName(sheetName);

                //循环sheet的行
                for(int i=sheetConfigBean.getRowStartIndex();i<=sheet.getLastRowNum();i++){

                    String rowIndex=String.valueOf(i);
                    Row row=sheet.getRow(i);

                    tempDataMap = new HashMap<>();

                    tempCellBeansMap = new HashMap<>();

                    tempErrors = new ArrayList<>();
                    tempValues = "";

                    for (int j = 0; j < sheetConfigBean.getColCount(); j++) {

                        colIndex = j + sheetConfigBean.getColStartIndex();

                        colConfigBean=sheetConfigBean.getColConfigBean(String.valueOf(colIndex));

                        Cell cell=row.getCell(colIndex);

                        //列循环
                        cellBean = new CellBean();
                        cellBean.setCellValue(ExcelUtil.getCellValue(cell));
                        cellBean.setClassName(sheetConfigBean.getClassName());
                        cellBean.setColConfigBean(colConfigBean);
                        cellBean.setColIndex(colIndex.toString());
                        cellBean.setExcelId(excelId);
                        cellBean.setExcelName(excelConfigBean.getName());
                        cellBean.setRowIndex(rowIndex);
                        cellBean.setSheetName(sheetName);
                        cellBean.setDefaultColName(ExcelUtil.getColumnName(colIndex+1));

                        tempValues += cellBean.getCellValue();

                        if(dataManager.managerCellData(cellBean)){
                            /*ErrorBean temp = checkValue(cellBean,checkCellValueManager);
                            if (temp != null) {
                                tempErrors.add(temp);
                            }*/
                            checkValue(cellBean,checkCellValueManager);
                            tempDataMap.put(colConfigBean.getName(), cellBean.getCellValue());
                        }
                        tempCellBeansMap.put(colConfigBean.getName(), cellBean);
                    }
                    //行循环

                    //行级数据校验
                    checkRowValueManager.checkRowValue(i, tempCellBeansMap);
                    //取出错误信息
                    for (Map.Entry<String, CellBean> cellBeanEntry : tempCellBeansMap.entrySet()) {
                        if(cellBeanEntry.getValue().getError()!=null){
                            tempErrors.add(cellBeanEntry.getValue().getError());
                        }
                        if (cellBeanEntry.getValue().getTempCellValue() != null) {
                            tempDataMap.put(cellBeanEntry.getKey(), cellBeanEntry.getValue().getTempCellValue());
                        }
                    }

                    //整行值不为空时
                    if(tempValues!=null && !tempValues.trim().isEmpty()){
                        if(tempErrors.isEmpty()){
                            if(dataManager.managerRowData(i, tempDataMap)){
                                if (dataBeanClass != null) {
                                    rowData = dataBeanClass.newInstance();
                                    BeanUtils.populate(rowData, tempDataMap);
                                    sheetData.addData(rowData);
                                } else {
                                    sheetData.addData(tempDataMap);
                                }
                            }
                        }else{
                            excelDataBean.addAllError(tempErrors);
                        }
                    }

                }
                //sheet循环
                excelDataBean.addSheetData(sheetData);
            }
        }

        return excelDataBean;
    }


    private static ErrorBean checkValue(CellBean cellBean,ICheckCellValueManager checkCellValueManager) {
        try {
            String value = cellBean.getCellValue();
            int valLen = (value == null || value.trim().isEmpty()) ? 0 : value.length();

            //值不能为null
            if ((value == null || "".equals(value.trim())) && cellBean.getColConfigBean().getCheckBean() != null && cellBean.getColConfigBean().getCheckBean().getCheckNullBean() != null && cellBean.getColConfigBean().getCheckBean().getCheckNullBean().getNotNull()) {
                cellBean.createrError().addErrorMsg(cellBean.getColConfigBean().getCheckBean().getCheckNullBean().getErrorMsg());
            }

            CheckLength checkLength =cellBean.getColConfigBean().getCheckBean().getCheckLength();
            CheckMinLength checkMinLength =cellBean.getColConfigBean().getCheckBean().getCheckMinLength();
            CheckMaxLength checkMaxLength =cellBean.getColConfigBean().getCheckBean().getCheckMaxLength();

            //长度验证
            if (checkLength != null && (checkLength.getMaxLength()<valLen ||checkLength.getMinLength()>valLen)) {
                cellBean.createrError().addErrorMsg(cellBean.getColConfigBean().getCheckBean().getCheckLength().getErrorMsg());
            }
            //长度验证
            if (checkMinLength != null && checkMinLength.getMinLength()>valLen) {
                cellBean.createrError().addErrorMsg(cellBean.getColConfigBean().getCheckBean().getCheckMinLength().getErrorMsg());
            }
            //长度验证
            if (checkMaxLength != null && checkMaxLength.getMaxLength()<valLen) {
                cellBean.createrError().addErrorMsg(cellBean.getColConfigBean().getCheckBean().getCheckMaxLength().getErrorMsg());
            }

            //类型验证
            if ((value == null || "".equals(value.trim())) && cellBean.getColConfigBean().getCheckBean().getCheckType()!=null) {
                //TODO 类型待做
            }

            //格式验证
            CheckRegex[] checkRegexs = cellBean.getColConfigBean().getCheckBean().getCheckRegex();
            if (checkRegexs != null && checkRegexs.length > 0 && !"".equals(value.trim())) {

                for (int i = 0; i < checkRegexs.length; i++) {

                    Pattern p = Pattern.compile(checkRegexs[i].getRegex(),Pattern.MULTILINE);
                    Matcher m = p.matcher(value.replace("\n",""));
                    if (!m.matches()) {
                        cellBean.createrError().addErrorMsg(checkRegexs[i].getErrorMsg().replace("{regex}",checkRegexs[i].getRegex()));
                    }
                }
            }

            String errorMsg = checkCellValueManager.checkValue(cellBean);
            if(errorMsg!=null){
                cellBean.createrError().addErrorMsg(ExcelImportErrorMessage.getMsg(errorMsg));
            }

            ExtendErrorMsg extendErrorMsg = checkCellValueManager.checkCellValue(cellBean);
            if(extendErrorMsg!=null && (errorMsg=extendErrorMsg.getErrorMsg())!=null){
                cellBean.createrError().addErrorMsg(errorMsg);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cellBean.getError();
    }


}
