/**   
* @Title: PropertiesUtils.java 
* @Package： com.appvworks.framework.Utils 
* @Description: TODO
* @author：duanwei 
* @date： 2015年7月2日 上午11:09:42  
*/
package com.ywkj.nest.core.utils;

import java.util.Properties;

public class PropertiesUtils {

	public static String getValue(String propertyName) {
		Properties properties =SpringUtils.getInstance(Properties.class, "configProperties");
		return properties.getProperty(propertyName);
	}
	

}
