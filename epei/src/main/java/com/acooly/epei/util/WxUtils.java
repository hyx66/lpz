package com.acooly.epei.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.beanutils.PropertyUtilsBean;

public class WxUtils {

	/**
     * 随机字符串，不长于32 位
     */
    public static String randomStr() {
        String template = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        while (buffer.length() < 32) {
            int index = random.nextInt(36);
            char c = template.charAt(index);
            buffer.append(c);
        }
        return buffer.toString();
    }
    
    
    /**
     * 签名
     * 
     * @param map 数据
     * @param password 密钥
     * @throws Exception 
     */
    public static String getSign(Map<String, Object> map, String password) throws Exception {
        return getSign(map, password, "");
    }
    
    /**
     * 签名
     * 
     * @param map 数据
     * @param password 密钥
     * @param ignore_keys 忽略的key
     * @throws Exception 
     */
    public static String getSign(Map<String, Object> map, String password, String... ignore_keys) throws Exception {
        String result = "";
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            /*if (StringUtils.isNotEmpty(ignore_key) && ignore_key.equals(entry.getKey())) {
                continue;
            }*/
            if (ignore_keys != null) {
                for (int i = 0; i < ignore_keys.length; i++) {
                    if (ignore_keys[i].equals(entry.getKey())) {
                        continue;
                    }
                }
            }
            if (entry.getValue() != null && !"".equals(entry.getValue())) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        if (size > 0) {
            String[] arrayToSort = list.toArray(new String[size]);
            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(arrayToSort[i]);
            }
            result = sb.toString();
            result += "key=" + password;
            System.out.println(result);
            result = MD5.MD5Encode(result);
            result = result.toUpperCase();
        }
        return result;
    }

  //将javabean实体类转为map类型，然后返回一个map类型的值
    public static Map<String, Object> beanToMap(Object obj) { 
            Map<String, Object> params = new HashMap<String, Object>(0); 
            try { 
                PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
                PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
                for (int i = 0; i < descriptors.length; i++) { 
                    String name = descriptors[i].getName(); 
                    if (!"class".equals(name)) { 
                        params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                    } 
                } 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
            return params; 
    }
    
}
