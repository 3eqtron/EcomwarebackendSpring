package com.eqtron.Management.System.utils;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class SystemUtils {
    private SystemUtils(){

    }
    public static ResponseEntity<String>getResponseEntity(String response,HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+response+"\"}", httpStatus);

    }
public  static String getUUID(){
    Date date=new Date();
    long time =date.getTime();
    return "FACTURE-"+time;

}
public static JSONArray getJsonrrayFromString(String data)throws JSONException{
        JSONArray jsonArray=new JSONArray();
        return jsonArray;
}

public static Map<String,Object>getMapFromJson(String data) {
    if (!Strings.isNullOrEmpty(data))
        return new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {
        }.getType());
        return new HashMap<>();
    }
public static Boolean isFileExist(String path){
        log.info("inside isFileExist {}",path);
        try {
            File file=new File(path);
            return (file != null && file.exists())? Boolean.TRUE :Boolean.FALSE;


        }catch (Exception ex){
            ex.printStackTrace();
        }return false;
}


}

