package com.bestdata.demo.utils;


import java.util.List;
import java.util.Map;

/**
 * Java对象与Json字符串互相转换的工具类，所有Json相关的工具api都应通过本api类进行调用，禁止直接调用Json库或其它Json相关的工具类。
 * @author lihl2
 *
 */
public class JsonUtil {

	private static IJsonSupport jsonUtil = new JsonSupportJacksonImpl();



	/**
	 * 对象转json，支持Map等集合类型，支持appframe生成的Bean对象。日期类型（Date、Time、Timestamp）输出格式为数字，可用new Date(数字)进行恢复。
	 *
	 * @param
	 * @param o
	 * @return
	 * @throws Exception
	 * @roseuid 4CF4C7D3034B
	 */
	public static String toJsonString(Object o) {
		return jsonUtil.toJsonString(o);
	}

	/**
	 * 对象转json，支持Map等集合类型，支持appframe生成的Bean对象
	 * @param o
	 * @param dateFormat 指定日期类型（Date、Time、Timestamp）的输出格式
	 * @return String
	 * @roseuid 4F6C245502B9
	 */
	public static String toJsonString(Object o, String dateFormat) {
		return jsonUtil.toJsonString(o, dateFormat);
	}

	

	/**
	 * @param o
	 * @param dateFormat 指定日期类型（Date）的输出格式
	 * @param timeFormat 指定日期类型（Time）的输出格式
	 * @param timestampFormat 指定日期类型（Timestamp）的输出格式
	 * @return String
	 * @throws Exception
	 * @roseuid 4F6C2530036A
	 */
	public static String toJsonString(Object o, String dateFormat,
			String timeFormat, String timestampFormat) {
		return jsonUtil
				.toJsonString(o, dateFormat, timeFormat, timestampFormat);
	}

	

	/**
	 * @param json
	 * @return Object 简单数据返回类型为List<Object>，对象数组的返回类型为List<Map<String,
	 *         Object>>，对象的返回类型为Map<String,Object>
	 * @throws Exception
	 * @roseuid 4F6C273D00FA
	 */
	public static Object parse(String json) {
		return jsonUtil.parseObject(json);
	}

	/**
	 * @param json
	 * @return List<Map<String,Object>>
	 * @throws Exception
	 * @roseuid 4F6C27770214
	 */
	public static List<Object> parseArray(String json) {
		return jsonUtil.parseArray(json);
	}

	/**
	 * @param json
	 * @return Map<String,Object>
	 * @throws Exception
	 * @roseuid 4F6C27CC0093
	 */
	public static Map<String, Object> parseObject(String json) {
		return jsonUtil.parseObject(json);
	}

	public static <T> T parseObject(String json,Class<T> clazz) {
		return jsonUtil.parse(json, clazz);
	}

}