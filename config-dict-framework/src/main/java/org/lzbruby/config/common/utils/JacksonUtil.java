package org.lzbruby.config.common.utils;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

/**
 * 功能描述：JSON操作工具类
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 15/11/11 Time: 10:47
 */
public class JacksonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
    }

    private static final JsonFactory JSONFACTORY = new JsonFactory();

    /**
     * 转换Java Bean 为 json
     */
    public static <T> String beanToJson(T o) {
        StringWriter sw = new StringWriter();
        JsonGenerator jsonGenerator = null;

        try {
            jsonGenerator = JSONFACTORY.createJsonGenerator(sw);
            MAPPER.writeValue(jsonGenerator, o);
            return sw.toString();

        } catch (Exception e) {
            throw new RuntimeException("转换Java Bean 为 json错误");

        } finally {
            if (jsonGenerator != null) {
                try {
                    jsonGenerator.close();
                } catch (Exception e) {
                    throw new RuntimeException("转换Java Bean 为 json错误");
                }
            }
        }
    }

    /**
     * json 转 javabean
     *
     * @param json
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("json 转 javabean错误", e);
        }
    }

    /**
     * 转换Java Bean 为 HashMap
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, Object> beanToMap(T o) {
        try {
            return MAPPER.readValue(beanToJson(o), HashMap.class);
        } catch (Exception e) {
            throw new RuntimeException("转换Java Bean 为 HashMap错误");
        }
    }

    /**
     * 转换Json String 为 HashMap
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String json, boolean collToString) {
        Map<String, Object> map = null;
        try {
            map = MAPPER.readValue(json, HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException("转换Java Bean 为 HashMap错误");
        }
        if (collToString) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof Collection || entry.getValue() instanceof Map) {
                    entry.setValue(beanToJson(entry.getValue()));
                }
            }
        }
        return map;

    }

    /**
     * List 转换成json
     *
     * @param list
     * @return
     */
    public static String listToJson(List<Map<String, String>> list) {
        JsonGenerator jsonGenerator = null;
        StringWriter sw = new StringWriter();
        try {
            jsonGenerator = JSONFACTORY.createJsonGenerator(sw);
            new ObjectMapper().writeValue(jsonGenerator, list);
            jsonGenerator.flush();
            return sw.toString();
        } catch (Exception e) {
            throw new RuntimeException("List 转换成json错误");
        } finally {
            if (jsonGenerator != null) {
                try {
                    jsonGenerator.flush();
                    jsonGenerator.close();
                } catch (Exception e) {
                    throw new RuntimeException("List 转换成json错误");
                }
            }
        }
    }

    /**
     * json 转List
     *
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, String>> jsonToList(String json) {
        try {
            if (json != null && !"".equals(json.trim())) {
                JsonParser jsonParse = JSONFACTORY.createJsonParser(new StringReader(json));

                ArrayList<Map<String, String>> arrayList = ((ArrayList<Map<String, String>>) new ObjectMapper().readValue(jsonParse, ArrayList.class));
                return arrayList;
            } else {
                throw new RuntimeException("json 转List错误");
            }
        } catch (Exception e) {
            throw new RuntimeException("json 转List错误");
        }
    }
}
