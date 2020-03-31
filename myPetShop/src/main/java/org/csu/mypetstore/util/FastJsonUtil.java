package org.csu.mypetstore.util;


import com.alibaba.fastjson.JSON;

public class FastJsonUtil {
    public static String object2json(Object object){
        JSON json = (JSON) JSON.toJSON(object);
        return json.toJSONString();
    }

}
