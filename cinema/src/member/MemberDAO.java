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

	// 회원가입 데이터 인서트
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

	// 로그인 메서드
	public CinemaDTO memberLogin(String id, String pw) {

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		CinemaDTO user = null; // 유저정보 그릇

		try {
			sql = "SELECT * FROM members WHERE id = ? AND pw = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// 일치할 때만 객체 생성 (그전에 하면 쓰레기값 들어감)
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

	// id 중복 체크
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

			ck = rs.next(); // 값이 있으면 true 저장

			pstmt.close();
			rs.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return ck;
	}

	// 관리자페이지
	// 회원정보 전체출력
	public void printAll() {
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<CinemaDTO> membersList = new ArrayList<>();

		try {
			String sql = "SELECT * FROM members";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			// 결과를 리스트에 저장 (제외: ID가 'admin'인 경우)
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
				// Iterator 사용하여 리스트 출력
				Iterator<CinemaDTO> it = membersList.iterator();
				System.out.println("   ========================회원 정보========================");
				System.out.printf("    %s     %5s     %5s     %5s\n", "아이디", "이름", "생일", "전화번호");
				while (it.hasNext()) {
					CinemaDTO dto = it.next();
					System.out.printf("    %s     %5s    %8d    %10s\n", dto.getId(), dto.getName(), dto.getBirth(),
							dto.getTel());
				}
			} else {
				System.out.println("저장된 회원이 없습니다.");
			}
			System.out.println("   =========================================================");

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	// 회원찾기
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
				// 회원정보가 있으면
				dto = new CinemaDTO();
				dto.setId(rs.getString(1));
				dto.setPw(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setBirth(rs.getInt(4));
				dto.setTel(rs.getString(5));

				// 회원 정보 출력
				System.out.println("   ========================회원 정보========================");
				System.out.println("   ID: " + dto.getId());
				System.out.println("   PW: " + dto.getPw());
				System.out.println("   Name: " + dto.getName());
				System.out.println("   Birth: " + dto.getBirth());
				System.out.println("   Tel: " + dto.getTel());
				System.out.println("   =========================================================");

			} else {
				System.out.println("   해당하는 회원이 없습니다.");
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	// 회원 예매 내역
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

				System.out.println("   ========================예매 내역========================");
				System.out.printf("    %s %6s   %9s   %8s\n", "아이디", "이름", "전화번호", "영화이름");
				for (CinemaDTO booking : bookingList) {
					System.out.printf("    %s  %5s   %3s     %s\n", booking.getId(), booking.getName(),
							booking.getTel(), booking.getMovieName());
				}
				System.out.println("   =========================================================");

			} else {
				System.out.println("예매한 회원 정보가 없습니다.");
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}
