package com.dataSource;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.util.PropertiesUtil;

public class JndiDataSource {
	private static final String LOGIN_USER = "weblogiUser";
	private static final String LOGIN_PASSWORD = "weblogicPwd";
	private static final String LOGIN_URL = "weblogicUrl";

	public static List<Connection> getConnection() {

		List<Connection> connList = new ArrayList<Connection>();

		// 数据源数量
		int dataSourceNum = 0;

		try {

			Properties pro = new Properties();
			pro.setProperty(Context.PROVIDER_URL, PropertiesUtil.getValue(LOGIN_URL));
			pro.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.T3InitialContextFactory");
			Context context;
			context = new InitialContext(pro);

			// 注意：lookup 中的参数 是你在weblogic中配置的JNDI名称
			for (int i = 0; i < dataSourceNum; i++) {
				// 配置的JNID名
				DataSource ds = (DataSource) context.lookup("jdbc-oracle");
				// 登陆weblogic的用户名、密码
				Connection conn = ds.getConnection(PropertiesUtil.getValue(LOGIN_USER),
						PropertiesUtil.getValue(PropertiesUtil.getValue(LOGIN_PASSWORD)));
				if (conn != null) {
					connList.add(conn);
				}
			}
		} catch (Exception e) {
			System.out.println("获取数据库连接失败！");
			e.printStackTrace();
		}

		return connList;
	}
}
