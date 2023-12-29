package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {

	private static Connection dbConn;

	public static Connection getConnection() {

		if (dbConn == null) {
			try {
				// thin 가장 작은 연결
				// 와이파이로 연결시 localhost로 연결
				String url = "jdbc:oracle:thin:@192.168.16.6:1521:xe";
				String user = "suzi";
				String pwd = "a123";

				// 클래스를 읽어옴
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);

			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		return dbConn;
	}

	public static void close() {
		if (dbConn != null) {
			try {
				if (!dbConn.isClosed()) {
					// 닫혀있지 않으면 닫기
					dbConn.close();
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		// 초기화 (다시 연결하려면 필요함)
		dbConn = null;
	}

}
