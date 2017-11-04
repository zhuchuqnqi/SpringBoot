package com.main;

import java.sql.Connection;
import java.util.List;

import com.dataSource.JndiDataSource;
import com.thread.GetDataThread;

public class StartBatch {

	private static void start() {
		//获取数据库存连接
		List<Connection> connList = JndiDataSource.getConnection();

		if (connList != null && connList.size() > 0) {
			for (Connection conn : connList) {
				GetDataThread getDataThread = new GetDataThread(conn);
				Thread t = new Thread(getDataThread);
				t.start();
			}
		} else {
			System.out.println("无数据库连接！");
		}
	}
	
	public static void main(String[] args) {
		start();
	}
}
