package com.example.demo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/**
 * Json工具
 *
 * @author pengboran
 */
@Slf4j
@SuppressWarnings("unchecked,rawtypes")
public class JsonUtils {

    /**
     * ObjectMapper 最好是被声明为全局唯一单例的。里面的内容搜做了并发处理了。
     * https://stackoverflow.com/questions/3907929/should-i-declare-jacksons-objectmapper-as-a-static-field
     * 通用策略
     */
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 前端序列化策略
     */
    public static final ObjectMapper OBJECT_MAPPER_FOR_WEB = JsonMapper.builder().enable(JsonWriteFeature.WRITE_NUMBERS_AS_STRINGS).build();

    static {
        config(OBJECT_MAPPER);
        config(OBJECT_MAPPER_FOR_WEB);
    }

    /**
     * 配置通用选项
     */
    public static void config(ObjectMapper objectMapper) {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //所有的日期格式都统一为以下的样式
        objectMapper.setTimeZone(TimeZone.getDefault());
        // 里面竟然 clone 了一份来使用，我认为以后会换 jdk8 的新方式
        // https://stackoverflow.com/questions/33672037/is-it-safe-to-use-simpledateformat-in-fasterxmls-objectmapper
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许接受空字符串
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    /**
     * 将非基础类型或者String的 Object 转化成为 Map
     */
    public static Map<String, Object> obj2Map(Object object) {
        if (ClassUtils.isPrimitiveOrWrapper(object.getClass()) || object instanceof String) {
            throw new RuntimeException("Only receive non primitive types and non String.");
        }
        return OBJECT_MAPPER.convertValue(object, Map.class);
    }

    /**
     * 将 Object 转化成为美化之后的 Json String。好处是看上去更好看，坏处是不利于写正则表达式进行解析，需要加入大量的换行符
     *
     * @param object original object
     * @return formatted json string
     */
    public static String obj2StringPretty(Object object) {
        return obj2String(OBJECT_MAPPER, object, true);
    }

    /**
     * 这里使用了前端专用的转换策略。
     *
     * @param object original object
     * @return json string
     */
    public static String obj2StringForWeb(Object object) {
        return obj2String(OBJECT_MAPPER_FOR_WEB, object, false);
    }


    /**
     * 将 Object 转化成为 String。好处是节约空间。
     *
     * @param object original object
     * @return json string
     */
    public static String obj2String(Object object) {
        return obj2String(OBJECT_MAPPER, object, false);
    }

    /**
     * 将 Object 转化成为 String。这里要注意的是，如果传入的本身是一个 String，那么会直接返回。
     *
     * @param object   original object
     * @param isPretty trigger
     * @return json string
     */
    private static String obj2String(ObjectMapper OBJECT_MAPPER, Object object, boolean isPretty) {

        if (object == null) {
            return null;
        }

        try {
            if (object instanceof String) {
                return (String) object;
            }

            if (isPretty) {
                return System.lineSeparator()
                        + OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object)
                        + System.lineSeparator();
            } else {
                return OBJECT_MAPPER.writeValueAsString(object);
            }

        } catch (JsonProcessingException e) {
            log.error(Throwables.getStackTraceAsString(e));
            return object.toString();
        }
    }

    /**
     * string 字符串转对象
     *
     * @param str            json string
     * @param elementClasses Class object
     * @return Target object
     */
    public static <T> T string2Object(String str, Class<T> elementClasses) {
        if (StringUtils.isEmpty(str) || elementClasses == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(str, elementClasses);
        } catch (IOException e) {
            String s = "Deserializing error:" + Throwables.getStackTraceAsString(e);
            throw new RuntimeException(s);
        }
    }

    /**
     * string 转换成为 List
     *
     * @param str            原始数据
     * @param elementClasses 需要转换成为的类型
     * @return 转换完成的对象
     */
    public static <T> List<T> string2List(String str, Class<T> elementClasses) {
        return string2Collection(str, List.class, elementClasses);
    }

    /**
     * string 转换成为 Set
     *
     * @param str            原始数据
     * @param elementClasses 需要转换成为的类型
     * @return 转换完成的对象
     */
    public static <T> Set<T> string2Set(String str, Class<T> elementClasses) {
        return string2Collection(str, Set.class, elementClasses);
    }

    /**
     * string 转换成为 map
     *
     * @param str 原始数据
     * @return 转换完成的对象
     */
    public static Map string2Map(String str) {
        try {
            return OBJECT_MAPPER.readValue(str, Map.class);
        } catch (Exception e) {
            String s = "Deserializing error:" + Throwables.getStackTraceAsString(e);
            throw new RuntimeException(s);
        }
    }

    /**
     * string 转换成为 Collection
     *
     * @param str             json string
     * @param collectionClass Collection
     * @param elementClasses  Target class
     * @return 转换完成的对象
     */
    private static <T> T string2Collection(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        if (StringUtils.isEmpty(str) || elementClasses == null) {
            return null;
        }
        JavaType javaType = getCollectionType(collectionClass, elementClasses);
        try {
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (Exception e) {
            String s = "Deserializing error:" + Throwables.getStackTraceAsString(e);
            throw new RuntimeException(s);
        }
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static JsonNode objectToJsonNode(Object object) {
        try {
            return OBJECT_MAPPER.readTree(obj2String(object));
        } catch (JsonProcessingException e) {
            String s = "JsonProcessingException :" + Throwables.getStackTraceAsString(e);
            throw new RuntimeException(s);
        }
    }

}
