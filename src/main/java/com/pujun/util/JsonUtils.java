package com.pujun.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
    private static final ObjectMapper objectMapper = new ObjectMapper();
	
	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //忽略未知属性，而不返回失败
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); //允许非标准的json格式：json字段名称可以不使用"包裹
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true); //允许非标准的json格式：json字段名称和字段值可以使用'包裹
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
	
	public static String writeValueAsString(Object obj) throws JsonProcessingException{
		return objectMapper.writeValueAsString(obj);
	}
	
	public static <T> T readValue(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException{
		return objectMapper.readValue(json, clazz);
	}
	
	public static <T> T readValue(InputStream input, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException{
		return objectMapper.readValue(input, clazz);
	}
    
/*    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }*/
}
