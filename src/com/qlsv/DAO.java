package com.qlsv;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DAO {
	private static String DB_URL = "jdbc:mysql://localhost:3306/qlsv";
	private static String USER = "root";
	private static String PASSWORD = "";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {

			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			//System.out.println("connect successfully!");
		} catch (SQLException e) {
			System.out.println("connect failure!");
			e.printStackTrace();
		}
		return conn;
	}
	
	public void closeConnect() {
		try {
			getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
