package com.xiaoxiami.excel.config;

import com.xiaoxiami.excel.bean.*;
import com.xiaoxiami.excel.core.ExcelImportCore;
import com.xiaoxiami.excel.message.ExcelImportErrorMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelImportCheckConfig {

    //每个配置文件的存储的信息结构
    private static Map<String, ExcelConfigBean> excelBeanMap = new HashMap<>();

    public static void init(ExcelImportConfig excelImportConfig){
        String[] checkXmlPaths = excelImportConfig.getCheckXmlPaths();
        if(checkXmlPaths!=null){
            for (String checkXmlPath : checkXmlPaths) {
                if (checkXmlPath != null && !checkXmlPath.isEmpty()) {
                    //解析xml配置
                    InputStream in = ExcelImportCheckConfig.class.getClassLoader().getResourceAsStream(checkXmlPath);
                    try {
                        SAXReader reader = new SAXReader();

                        //忽略dtd约束文件
                        reader.setEntityResolver(new EntityResolver() {
                            @Override
                            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                                return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
                            }
                        });

                        Document dom = reader.read(in);
                        Element root = dom.getRootElement();

                        ExcelConfigBean excelBean = new ExcelConfigBean();
                        excelBean.setId(root.valueOf("@id"));
                        excelBean.setName(root.valueOf("@name"));

                        List list = root.selectNodes("//sheet");
                        for (int i=0;i<list.size();i++) {
                            SheetConfigBean sheetBean = new SheetConfigBean();
                            Element sheet=(Element)list.get(i);
                            sheetBean.setIndex(sheet.valueOf("@index"));
                            sheetBean.setSheetName(sheet.valueOf("@name"));
                            sheetBean.setClassName(sheet.valueOf("@className"));
                            sheetBean.setManageClassName(sheet.valueOf("@manageClassName"));
                            sheetBean.setColCount(Integer.valueOf(emptyToInteger(sheet.valueOf("@count"))));
                            sheetBean.setRowStartIndex(Integer.valueOf(emptyToInteger(sheet.valueOf("@rowStartIndex"))));
                            sheetBean.setColStartIndex(Integer.valueOf(emptyToInteger(sheet.valueOf("@colStartIndex"))));

                            List listCol = sheet.selectNodes("col");

                            for (int j=0;j<listCol.size();j++) {
                                Node col=(Node)listCol.get(j);
                                ColConfigBean colBean = new ColConfigBean();
                                colBean.setIndex(col.valueOf("@index"));
                                colBean.setName(col.valueOf("@name"));
                                colBean.setColName(col.valueOf("@colName"));

                                CheckConfigBean checkBean = new CheckConfigBean();
                                colBean.setCheckBean(checkBean);
                                Node nullNode=col.selectSingleNode("notNull");
                                if(nullNode!=null && nullNode.getStringValue()!=null && !nullNode.getStringValue().trim().isEmpty()){
                                    CheckNullBean checkNullBean = new CheckNullBean();
                                    checkBean.setCheckNullBean(checkNullBean);
                                    checkNullBean.setNotNull(Boolean.valueOf(nullNode.getStringValue()));
                                    if(nullNode.valueOf("@errorMsgOrCode")!=null && !nullNode.valueOf("@errorMsgOrCode").isEmpty()){
                                        checkNullBean.setErrorMsg(ExcelImportErrorMessage.getMsg(nullNode.valueOf("@errorMsgOrCode")));
                                    }
                                }


                                Node lengthNode=col.selectSingleNode("length");
                                if(lengthNode!=null){
                                    CheckLength checkLength = new CheckLength();
                                    checkBean.setCheckLength(checkLength);
                                    if (lengthNode.valueOf("@errorMsgOrCode") != null && !lengthNode.valueOf("@errorMsgOrCode").isEmpty()) {
                                        checkLength.setErrorMsg(ExcelImportErrorMessage.getMsg(lengthNode.valueOf("@errorMsgOrCode")));
                                    }
                                    checkLength.setMaxLength(Integer.valueOf(emptyToInteger(lengthNode.valueOf("@maxLength"))));
                                    checkLength.setMinLength(Integer.valueOf(emptyToInteger(lengthNode.valueOf("@minLength"))));
                                }


                                Node maxLengthNode=col.selectSingleNode("maxLength");
                                if(maxLengthNode!=null){
                                    CheckMaxLength checkMaxLength = new CheckMaxLength();
                                    checkBean.setCheckMaxLength(checkMaxLength);
                                    if (maxLengthNode.valueOf("@errorMsgOrCode") != null && !maxLengthNode.valueOf("@errorMsgOrCode").isEmpty()) {
                                        checkMaxLength.setErrorMsg(ExcelImportErrorMessage.getMsg(maxLengthNode.valueOf("@errorMsgOrCode")));
                                    }
                                    checkMaxLength.setMaxLength(Integer.valueOf(emptyToInteger(maxLengthNode.valueOf("@length"))));
                                }

                                Node minLengthNode=col.selectSingleNode("minLength");
                                if(minLengthNode!=null){
                                    CheckMinLength checkMinLength = new CheckMinLength();
                                    checkBean.setCheckMinLength(checkMinLength);
                                    if (minLengthNode.valueOf("@errorMsgOrCode") != null && !minLengthNode.valueOf("@errorMsgOrCode").isEmpty()) {
                                        checkMinLength.setErrorMsg(ExcelImportErrorMessage.getMsg(minLengthNode.valueOf("@errorMsgOrCode")));
                                    }
                                    checkMinLength.setMinLength(Integer.valueOf(emptyToInteger(minLengthNode.valueOf("@length"))));
                                }

                                Node typeNode=col.selectSingleNode("type");
                                if(typeNode!=null){
                                    CheckType checkType = new CheckType();
                                    checkBean.setCheckType(checkType);
                                    if (typeNode.valueOf("@errorMsgOrCode") != null && !typeNode.valueOf("@errorMsgOrCode").isEmpty()) {
                                        checkType.setErrorMsg(ExcelImportErrorMessage.getMsg(typeNode.valueOf("@errorMsgOrCode")));
                                    }
                                    checkType.setName(typeNode.valueOf("@name"));
                                }

                                Node regexsNode=col.selectSingleNode("regexs");
                                if(regexsNode!=null){

                                    List listRegex = ((Element) regexsNode).elements("regex");

                                    if(listRegex!=null && listRegex.size()>0){
                                        CheckRegex[] checkRegexs = new CheckRegex[listRegex.size()];
                                        checkBean.setCheckRegex(checkRegexs);
                                        for (int f = 0; f < listRegex.size(); f++) {
                                            if (((Node)listRegex.get(f)).getStringValue()!=null && !((Node)listRegex.get(f)).getStringValue().trim().isEmpty()) {
                                                checkRegexs[f]=new CheckRegex();
                                                if (((Node)listRegex.get(f)).valueOf("@errorMsgOrCode")!=null && !((Node)listRegex.get(f)).valueOf("@errorMsgOrCode").isEmpty()) {
                                                    checkRegexs[f].setErrorMsg(ExcelImportErrorMessage.getMsg(((Node)listRegex.get(f)).valueOf("@errorMsgOrCode")));
                                                }
                                                checkRegexs[f].setRegex(((Node)listRegex.get(f)).getStringValue());
                                            }
                                        }
                                    }
                                }

                                sheetBean.addCol(colBean.getIndex(), colBean);
                            }
                            excelBean.addSheet(sheetBean.getIndex(), sheetBean);
                        }
                        excelBeanMap.put(excelBean.getId(), excelBean);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            excelBeanMap = Collections.unmodifiableMap(excelBeanMap);
        }
    }

    /**
     * 空值转数字
     * @param o
     * @return
     */
    private static Integer emptyToInteger(String o) {
        if(o==null || o.isEmpty()){
            return -1;
        }
        return Integer.valueOf(o);
    }

    public static ExcelConfigBean getExcelConfigById(String excelId) {
        return excelBeanMap.get(excelId);
    }

    public static void main(String[] args) throws Exception {
        new ExcelImportConfig(null,new String[]{"check-config-information.xml"});

        InputStream in = ExcelImportCheckConfig.class.getClassLoader().getResourceAsStream("test.xlsx");

        ExcelDataBean excelDataBean =ExcelImportCore.excelManager(in, "information", null,null);

        if (excelDataBean != null && excelDataBean.getErrors().size() > 0) {
            for (ErrorBean errorBean : excelDataBean.getErrors()) {
                System.out.print(errorBean.getErrorCsv());
            }
        }
    }
}
