package com.atguigu.gmall.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

/**
 * @Author lg
 * @Description 将普通对象转换为Json字符串
 * @Version 1.0.0
 * @Date 2022/8/28 17:37
 */
public class Jsons {
    private static ObjectMapper mapper = new ObjectMapper();
    /**
     * 把对象转为json字符串
     * @param object
     * @return
     */
    public static String toStr(Object object) {
        //jackson
        try {
            String s = mapper.writeValueAsString(object);
            return s;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 把json字符串转为对象
     * @param jsonStr
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T toObj(String jsonStr,Class<T> clz) {
        if(StringUtils.isEmpty(jsonStr)){
            return null;
        }

        T t = null;
        try {
            t = mapper.readValue(jsonStr, clz);
            return t;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
