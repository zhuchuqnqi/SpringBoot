package com.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDataUtil {
	//分库连接
	private Connection connection;
	//主库连接
	private Connection mainConn;
	
	public QueryDataUtil(Connection connection) {
		super();
		this.connection = connection;
	}

	/**
	 * 从分库撮数据
	 * 
	 * @param tableName
	 *            表名
	 * @param filterField
	 *            通过此字段做筛选(是否已经转移到主库标识)
	 * @param start
	 *            开始
	 * @param end
	 *            结束
	 */
	public void queryData(String tableName, String filterField, int start, int end) {

		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM " + "(SELECT * FROM " + tableName + "WHERE "
					+ filterField + "=0) A " + "WHERE ROWNUM <= ? ) " + "WHERE RN >= ?";
			pst = connection.prepareStatement(sql);
			pst.setInt(0, start);
			pst.setInt(1, end);
			ResultSet rsd = pst.executeQuery();

			

			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				pst.close();
				pst = null;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 组装sql
	 * 
	 * @param rsd
	 */
	private String getSql(ResultSet rsd, String tableName) {
//		List<String> sqlList = new ArrayList<String>();
		StringBuffer sql = new StringBuffer("INSERT ");
		try {
			ResultSetMetaData metaData = rsd.getMetaData();
			int colCount = metaData.getColumnCount();
			List<String> colList = new ArrayList<String>();
		
			sql.append(tableName).append("(");
			for (int i = 0; i < colCount; i++) {
				String colName = metaData.getColumnName(i);
				metaData.getColumnType(i);
				
				colList.add(colName);
				if (i == colCount - 1) {
					sql.append(colName).append("VALUES (");
				} else {
					sql.append(colName).append(",");
				}
			}
			
			for (int i = 0; i < colCount; i++) {
				if (i == colCount - 1) {
					sql.append("? )");
				} else {
					sql.append("? ,");
				}
				
			}
			// 拼接值
//			while (rsd.next()) {
//				for (int n = 0; n < colList.size(); n++) {
//					if (n == colList.size() - 1) {
//						sql.append(rsd.getString(colList.get(n))).append(")");
//					} else {
//						sql.append(rsd.getString(colList.get(n))).append(",");
//					}
//				}
//				sqlList.add(sql.toString());
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sql.toString();
	}
}
