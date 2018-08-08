package com.bestdata.demo.utils;

import java.util.List;
import java.util.Map;

public interface IJsonSupport {
	/**
	    * 对象转json
	    * @param aInstance
	    * @param o
	    * @return
	    * @throws Exception
	    * @roseuid 4CF4C7D3034B
	    */
	   public  String toJsonString(Object o) ;



	   /**
	    * @param o
	    * @param dateFormat
	    * @return String
	    * @roseuid 4F6C245502B9
	    */
	   public String toJsonString(Object o, String dateFormat);



	   /**
	    * @param o
	    * @param dateFormat
	    * @param timeFormat
	    * @param timestampFormat
	    * @return String
	    * @roseuid 4F6C2530036A
	    */
	   public String toJsonString(Object o, String dateFormat, String timeFormat, String timestampFormat);


	   /**
	    * @param json
	    * @return Object
	    * @roseuid 4F6C273D00FA
	    */
	   public <T> T parse(String json, Class<T> clazz);
	   
	  
	   
	   /**
	    * @param json
	    * @return Map<String,Object>
	    * @roseuid 4F6C27CC0093
	    */
	   public Map<String,Object> parseObject(String json);
	   
	   public List<Object> parseArray(String json);

}
