package com.xiaoxiami.excel.message;

import com.xiaoxiami.excel.config.ExcelImportConfig;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ExcelImportErrorMessage {
    /**
     * 用于存放错误类型对应的错误消息
     */
    private static Map<String,String> errorMessages=new HashMap<>();

    public static void init(ExcelImportConfig excelImportConfig){
        InputStreamReader in=null;
        InputStreamReader in2=null;
        try {
            in=new InputStreamReader(ExcelImportErrorMessage.class.getClassLoader().getResourceAsStream("default-error-message.properties"),"UTF-8");
            Properties pp=new Properties();
            pp.load(in);
            for(String key:pp.stringPropertyNames()){
                errorMessages.put(key, pp.getProperty(key));
            }

            if(excelImportConfig.getErrorMsgPath()!=null && !excelImportConfig.getErrorMsgPath().isEmpty()){
                in2=new InputStreamReader(ExcelImportErrorMessage.class.getClassLoader().getResourceAsStream(excelImportConfig.getErrorMsgPath()),"UTF-8");
                pp=new Properties();
                pp.load(in2);
                for(String key:pp.stringPropertyNames()){
                    errorMessages.put(key, pp.getProperty(key));
                }

            }
            errorMessages= Collections.unmodifiableMap(errorMessages);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally{
            try {
                if(in!=null)
                    in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                if(in2!=null)
                    in2.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 如果错误消息不存在返回key
     * @param key
     * @return
     */
    public static String getMsg(String key) {
        return getMsg(key, true);
    }

    /**
     * 查询错误消息
     * @param key
     * @param nullReturnCode true时 消息不存在返回key,false时 消息不存在返回null
     * @return
     */
    public static String getMsg(String key,boolean nullReturnCode) {
        String tempMsg = errorMessages.get(key);
        if (nullReturnCode) {
            return tempMsg == null || tempMsg.trim().isEmpty() ? key : tempMsg;
        }
        return tempMsg;
    }
}
