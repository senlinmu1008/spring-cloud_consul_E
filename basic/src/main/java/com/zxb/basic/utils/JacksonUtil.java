package com.zxb.basic.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhaoxb
 * @date 2019-12-24 16:58
 * @return
 */
public class JacksonUtil {

    public final static String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
    public final static String XML_VERSION = "xml version";

    /**
     * javaBean,list,array convert to json string
     */
    public static String obj2json(ObjectMapper objMapper, Object obj) throws IOException {
        /** 如果存在节点没有实体中属性与之对应，解析不报错 **/
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    public static String obj2json(Object obj) throws IOException {
        ObjectMapper objMapper = new ObjectMapper();
        return obj2json(objMapper, obj);
    }

    public static String obj2json(Object obj, boolean needRoot) throws IOException {
        ObjectMapper objMapper = new ObjectMapper();
        if (needRoot) {
            objMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        }
        return obj2json(objMapper, obj);
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojo(ObjectMapper objectMapper, String jsonStr, Class<T> clazz) throws IOException {
        return objectMapper.readValue(jsonStr, clazz);
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return json2pojo(objectMapper, jsonStr, clazz);
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz, boolean needRoot) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (needRoot) {
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        }
        return json2pojo(objectMapper, jsonStr, clazz);
    }

    /**
     * json string convert to map
     */
    public static <T> Map<String, Object> json2map(String jsonStr) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return json2pojo(objectMapper, jsonStr, Map.class);
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr, new TypeReference<Map<String, T>>() {
        });
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr, new TypeReference<List<T>>() {
        });
        List<T> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            result.add(map2pojo(map, clazz));
        }
        return result;
    }

    /**
     * map convert to javaBean
     */
    public static <T> T map2pojo(Map map, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * json string convert to xml string
     */
    public static String json2xml(String jsonStr) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode root = objectMapper.readTree(jsonStr);
        String xml = xmlMapper.writeValueAsString(root);
        return xml;
    }

    /**
     * xml string convert to json string
     */
    public static String xml2json(String xml) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();
        StringWriter w = new StringWriter();
        JsonParser jp = xmlMapper.getFactory().createParser(xml);
        JsonGenerator jg = objectMapper.getFactory().createGenerator(w);
        while (jp.nextToken() != null) {
            jg.copyCurrentEvent(jp);
        }
        jp.close();
        jg.close();
        return w.toString();
    }

    public static <T> T json2pojo(ObjectMapper objectMapper, String jsonStr, TypeReference<?> objTypeRef) throws IOException {
        return objectMapper.readValue(jsonStr, objTypeRef);
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojo(String jsonStr, TypeReference<?> objTypeRef) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return json2pojo(objectMapper, jsonStr, objTypeRef);
    }

    /**
     * xml转换为实体类，支持泛型
     *
     * @param xml
     * @param objTypeRef
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xml2Pojo(String xml, TypeReference<?> objTypeRef) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        /** 如果存在节点没有实体中属性与之对应，解析不报错 **/
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return xmlMapper.readValue(xml, objTypeRef);
    }

    /**
     * xml转换为实体类
     *
     * @param xml
     * @param clz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xml2Pojo(String xml, Class<T> clz) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        /** 如果存在节点没有实体中属性与之对应，解析不报错 **/
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return xmlMapper.readValue(xml, clz);
    }

    public static String obj2xml(Object obj) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    /**
     * 是否需要xml version
     *
     * @param obj
     * @param needHead
     * @return
     * @throws Exception
     */
    public static String obj2xml(Object obj, boolean needHead) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        String xml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        if (needHead && !StringUtils.containsIgnoreCase(xml, XML_VERSION)) {
            return XML_HEAD + xml;
        } else {
            return xml;
        }

    }

}
