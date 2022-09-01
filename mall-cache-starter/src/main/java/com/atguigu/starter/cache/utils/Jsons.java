package com.atguigu.starter.cache.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

/**
 * @Author lg
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/9/1 20:22
 */
public class Jsons {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 讲对象转换为JSON字符串
     * @param object
     * @return
     */
    public static String toStr(Object object) {
        try {
            String s = mapper.writeValueAsString(object);
            return s;
        } catch (JsonProcessingException e) {
            return null;
        }
    }


    /**
     * 将字符串转换为简单对象
     * @param str
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T toObj(String str,Class<T> clz){
        if (StringUtils.isEmpty(str)) return null;
        try {
            return mapper.readValue(str, clz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
    /**
     * 将字符串转换为复杂对象
     * @param str
     * @param tr
     * @param <T>
     * @return
     */
    public static <T> T toObj(String str, TypeReference<T> tr){
        if (StringUtils.isEmpty(str)) return null;
        try {
            return mapper.readValue(str, tr);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
