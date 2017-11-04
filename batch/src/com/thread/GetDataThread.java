package com.thread;

import java.sql.Connection;
import java.sql.SQLException;

public class GetDataThread implements Runnable{
	/**
	 * 数据库连接
	 */
	private Connection connection;

	public GetDataThread(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void run() {
		try {
			connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
