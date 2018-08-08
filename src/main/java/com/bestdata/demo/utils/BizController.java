/**
 * 
 */
package com.bestdata.demo.utils;

import sun.misc.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 所有基于Spring MVC的Web控制器类（Action）的统一父类，提供一些便利的请求处理方法，如返回Json、文本数据等
 * @author lihl2
 *
 */
public class BizController {
	/**
	 * 返回JSon格式的数据
	 *
	 * @param response
	 * @param text
	 * @throws Exception
	 */
	public String returnJson(HttpServletResponse response, CharSequence text) {
		return returnText(response, text, "application/json;charset=UTF-8");
	}

	/**
	 * 返回JSon格式的数据
	 *
	 * @param response
	 * @param data
	 * @throws Exception
	 */
	public String returnJson(HttpServletResponse response, Object data) {
		return returnText(response, JsonUtil.toJsonString(data), "application/json;charset=UTF-8");
	}

	/**
	 * 返回xml格式的数据
	 *
	 * @param response
	 * @param text
	 * @throws Exception
	 */
	public String returnXml(HttpServletResponse response, CharSequence text) {
		return returnText(response, text, "text/xml;charset=UTF-8");
	}

	/**
	 * 返回文本数据
	 * @param response
	 * @param text
	 * @throws Exception
	 */
	public String returnText(HttpServletResponse response, CharSequence text) {
		return returnText(response, text, "text/plain;charset=UTF-8");
	}

	/**
	 * 返回文本数据
	 * @param response
	 * @param text
	 * @param contenttype 内容类型，如：text/plain、text/xml、application/json、text/json、text/javascript、application/javascript（不支持旧浏览器）
	 * @param encoding 字符集编码，如：GB18030、UTF-8，不建议使用GB2312和GBK
	 * @throws Exception
	 */
	public String returnText(HttpServletResponse response, CharSequence text, final String contenttype, final String encoding) {
		return returnText(response, text, contenttype+";charset="+encoding);
	}

	public String returnHtml(HttpServletResponse response, CharSequence text) {
		return returnText(response, text, "text/html;charset=UTF-8");
	}

	/**
	 * 返回文本数据
	 *
	 * @param response
	 * @param text
	 * @param contenttype
	 * @throws IOException
	 * @author lihl2 2011-3-25
	 */
	public String returnText(HttpServletResponse response, CharSequence text,
                             final String contenttype) {
		response.setContentType(contenttype);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(text.toString());
			pw.flush();
		} catch (IOException e) {
			throw new AppException(e);
		} finally {
			if(pw != null) {
			    pw.close();
            }
		}
		return null;
	}

}
