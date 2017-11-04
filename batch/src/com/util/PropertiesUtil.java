package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * properties文件工具类
 * @author 朱传奇
 *
 */
public class PropertiesUtil {
	
	private static Properties  prop= new Properties();
	
	//初始化文件资源
	static {
		InputStream in = PropertiesUtil.class.getClass().getResourceAsStream("/source.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//通过properties的key获取对应value值
	public static String getValue(String key) {
		String value = "";
		value = prop.getProperty(key);
		return value;
	}

	public static void main(String[] args) {
		System.out.println(getValue("jndiDataSourceNum"));
	}

}
