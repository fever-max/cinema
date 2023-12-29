package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {

	private static Connection dbConn;

	public static Connection getConnection() {

		if (dbConn == null) {
			try {
				// thin ���� ���� ����
				// �������̷� ����� localhost�� ����
				String url = "jdbc:oracle:thin:@192.168.16.6:1521:xe";
				String user = "suzi";
				String pwd = "a123";

				// Ŭ������ �о��
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
					// �������� ������ �ݱ�
					dbConn.close();
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		// �ʱ�ȭ (�ٽ� �����Ϸ��� �ʿ���)
		dbConn = null;
	}

}
