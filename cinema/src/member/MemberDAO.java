package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cinema.CinemaDTO;
import db.DBConn;

public class MemberDAO {

	// ȸ������ ������ �μ�Ʈ
	public int joinData(CinemaDTO dto) {
		int result = 0;

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "insert into members values (?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getName());
			pstmt.setInt(4, dto.getBirth());
			pstmt.setString(5, dto.getTel());

			result = pstmt.executeUpdate();

			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;
	}

	// �α��� �޼���
	public CinemaDTO memberLogin(String id, String pw) {

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		CinemaDTO user = null; // �������� �׸�

		try {
			sql = "SELECT * FROM members WHERE id = ? AND pw = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// ��ġ�� ���� ��ü ���� (������ �ϸ� �����Ⱚ ��)
				user = new CinemaDTO();
				user.setId(rs.getString("id"));
				user.setPw(rs.getString("pw"));
				user.setName(rs.getString("name"));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return user;
	}

	// id �ߺ� üũ
	public boolean isIdDuplicate(String id) {
		boolean ck = false;

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "select id from members where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			ck = rs.next(); // ���� ������ true ����

			pstmt.close();
			rs.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return ck;
	}

	// ������������
	// ȸ������ ��ü���
	public void printAll() {
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<CinemaDTO> membersList = new ArrayList<>();

		try {
			String sql = "SELECT * FROM members";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			// ����� ����Ʈ�� ���� (����: ID�� 'admin'�� ���)
			while (rs.next()) {
				String memberId = rs.getString("id");
				if (!"admin".equals(memberId)) {
					CinemaDTO members = new CinemaDTO();

					members.setId(memberId);
					members.setPw(rs.getString("pw"));
					members.setName(rs.getString("name"));
					members.setBirth(rs.getInt("birth"));
					members.setTel(rs.getString("tel"));

					membersList.add(members);
				}
			}

			if (!membersList.isEmpty()) {
				// Iterator ����Ͽ� ����Ʈ ���
				Iterator<CinemaDTO> it = membersList.iterator();
				System.out.println("   ========================ȸ�� ����========================");
				System.out.printf("    %s     %5s     %5s     %5s\n", "���̵�", "�̸�", "����", "��ȭ��ȣ");
				while (it.hasNext()) {
					CinemaDTO dto = it.next();
					System.out.printf("    %s     %5s    %8d    %10s\n", dto.getId(), dto.getName(), dto.getBirth(),
							dto.getTel());
				}
			} else {
				System.out.println("����� ȸ���� �����ϴ�.");
			}
			System.out.println("   =========================================================");

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	// ȸ��ã��
	public void printOne(String userId) {
		CinemaDTO dto = null;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "select id,pw,name,birth,tel from members where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// ȸ�������� ������
				dto = new CinemaDTO();
				dto.setId(rs.getString(1));
				dto.setPw(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setBirth(rs.getInt(4));
				dto.setTel(rs.getString(5));

				// ȸ�� ���� ���
				System.out.println("   ========================ȸ�� ����========================");
				System.out.println("   ID: " + dto.getId());
				System.out.println("   PW: " + dto.getPw());
				System.out.println("   Name: " + dto.getName());
				System.out.println("   Birth: " + dto.getBirth());
				System.out.println("   Tel: " + dto.getTel());
				System.out.println("   =========================================================");

			} else {
				System.out.println("   �ش��ϴ� ȸ���� �����ϴ�.");
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	// ȸ�� ���� ����
	public void reserveAll() {
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CinemaDTO> bookingList = new ArrayList<>();

		try {
			String sql = "select A.id, A.name, A.tel, B.movieName from members A, booking B where A.id = B.userId";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CinemaDTO booking = new CinemaDTO();

				booking.setId(rs.getString(1));
				booking.setName(rs.getString(2));
				booking.setTel(rs.getString(3));
				booking.setMovieName(rs.getString(4));

				bookingList.add(booking);
			}

			if (!bookingList.isEmpty()) {

				System.out.println("   ========================���� ����========================");
				System.out.printf("    %s %6s   %9s   %8s\n", "���̵�", "�̸�", "��ȭ��ȣ", "��ȭ�̸�");
				for (CinemaDTO booking : bookingList) {
					System.out.printf("    %s  %5s   %3s     %s\n", booking.getId(), booking.getName(),
							booking.getTel(), booking.getMovieName());
				}
				System.out.println("   =========================================================");

			} else {
				System.out.println("������ ȸ�� ������ �����ϴ�.");
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}
