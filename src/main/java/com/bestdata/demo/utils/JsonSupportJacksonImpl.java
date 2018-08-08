package com.bestdata.demo.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings({"unchecked", "rawtypes"})
public class JsonSupportJacksonImpl implements IJsonSupport{

	static class DyDateFormatSerializer  extends JsonSerializer<Date>{
		//TODO 规范常量定义
		static  String ymdhmsF="yyyy/MM/dd HH:mm:ss" ;  
		static  String ymdF="yyyy-MM-dd" ; 
		static  String hmsF="hh:mm:ss" ; 
	
	
		@Override
		public void serialize(Date value, JsonGenerator jGen,
				SerializerProvider paramSerializerProvider) throws IOException,
				JsonProcessingException {
			String dateStr=null;
			if (value instanceof Date){
				dateStr=DateFormatUtils.format(value,ymdF);			
			}
			if (value instanceof Timestamp){
				 dateStr=DateFormatUtils.format(value,ymdhmsF);
			}
			if (value instanceof Time){
				dateStr=DateFormatUtils.format(value,hmsF);
			}
			jGen.writeObject(dateStr);
		}
	
	}

	static ObjectMapper objMapp = new ObjectMapper();   
	static {		
		CustomSerializerFactory serializerFactory1=new CustomSerializerFactory();
		serializerFactory1.addGenericMapping(Date.class, new DyDateFormatSerializer());
		objMapp.setSerializerFactory(serializerFactory1);
//		DateFormat df=new SimpleDateFormat(DateTimeUtil.DEFAULT_SYSTEM_DATE_FORMAT1);
//		objMapp.setDateFormat(df); 
		
	}
	public <T> T parse(String json,Class<T> clazz) {
		try {
			return objMapp.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new AppException(e);
		} catch (JsonMappingException e) {
			throw new AppException(e);
		} catch (IOException e) {
			throw new AppException(e);
		}
	}
	public Map<String, Object> parseObject(String json) {
		try {
			return objMapp.readValue(json, HashMap.class);
		} catch (JsonParseException e) {
			throw new AppException(e);
		} catch (JsonMappingException e) {
			throw new AppException(e);
		} catch (IOException e) {
			throw new AppException(e);
		}
	}
	public List<Object> parseArray(String json) {
		try {
			return objMapp.readValue(json, new TypeReference<List<HashMap>>(){});
		} catch (JsonParseException e) {
			throw new AppException(e);
		} catch (JsonMappingException e) {
			throw new AppException(e);
		} catch (IOException e) {
			throw new AppException(e);
		}   
	}
	public String toJsonString(Object o) {
		Writer writer = new StringWriter();
		try {
			objMapp.writeValue(writer, o);
		} catch (JsonGenerationException e) {
			throw new AppException(e);
		} catch (JsonMappingException e) {
			throw new AppException(e);
		} catch (IOException e) {
			throw new AppException(e);
		}
		return writer.toString();
	}


	public String toJsonString(Object o, String dateFormat){
		DateFormat df=new SimpleDateFormat(dateFormat);
		DateFormat staticDf=objMapp.getSerializationConfig().getDateFormat();
		objMapp.setDateFormat(df); 
		String result=toJsonString(o);
		objMapp.setDateFormat(staticDf); 
		return result;
	}
	

	public String toJsonString(Object o, String dateFormat, String timeFormat,
			String timestampFormat) {
		DyDateFormatSerializer.ymdhmsF=timestampFormat;
		DyDateFormatSerializer.ymdF=dateFormat;
		DyDateFormatSerializer.hmsF=timeFormat;
		String result=toJsonString(o);		
		return result;
	}

	
	
}
