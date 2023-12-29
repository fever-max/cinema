package movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cinema.CinemaDTO;
import db.DBConn;

public class MovieDAO {

	// ��ȭ �� ���� (�����¼�)
	public List<CinemaDTO> getMovies() {
		List<CinemaDTO> moiveList = new ArrayList<CinemaDTO>();

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			sql = "select * from screenings";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CinemaDTO movie = new CinemaDTO();

				movie.setMovieNo(rs.getInt("movieNo"));
				movie.setMovieName(rs.getString("movieName"));
				movie.setFullSeats(rs.getInt("fullSeats"));
				movie.setNowSeats(rs.getInt("nowSeats"));

				moiveList.add(movie);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return moiveList;
	}

	// ��ȭ ����
	// ��ŷ���̺� (�ߺ����� üũ) > ��ũ���� ���̺� (�¼�üũ) > ��ŷ���̺� (�� �Է�)
	public CinemaDTO ticketing(int movieNum, String userId) {
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		CinemaDTO ticket = null; // ��ȯ�� ����

		try {
			// ��ŷ ���̺� üũ (�ߺ� ���� Ȯ��)

			sql = "select * from booking where movieNo = ? and userId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movieNum);
			pstmt.setString(2, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// ���� ������ ������
				System.out.println("   �̹� ���ŵ� ��ȭ�Դϴ�.");
				return null;
			}

			// ��ũ���� ���̺� ����Ʈ (�����¼� üũ)
			sql = "select * from screenings where movieNo = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movieNum);
			rs = pstmt.executeQuery();

			if (rs.next()) { // ���� ������
				int nowSeats = rs.getInt("nowSeats"); // ���� �¼� ����

				if (nowSeats > 0) {
					// �¼��� ����������
					sql = "insert into booking (movieNo, movieName, userId, checkMoive) values (?, ?, ?, ?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, rs.getInt("movieNo"));
					pstmt.setString(2, rs.getString("movieName"));
					pstmt.setString(3, userId);
					pstmt.setString(4, "1");

					int ck = pstmt.executeUpdate();

					if (ck > 0) {
						// ���� ������, ������Ʈ�� �¼��� -1 ����
						sql = "update screenings set nowSeats = nowSeats - 1 where movieNo = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, movieNum);
						pstmt.executeUpdate();

						// ���� ���� ��ȸ �� ��ȯ
						sql = "select * from booking where movieNo = ? and userId = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, rs.getInt("movieNo"));
						pstmt.setString(2, userId);
						rs = pstmt.executeQuery();

						if (rs.next()) {
							// ���� ���� ����
							ticket = new CinemaDTO();
							ticket.setMovieNo(rs.getInt("movieNo"));
							ticket.setMovieName(rs.getString("movieName"));
							ticket.setId(rs.getString("userId"));
							ticket.setCheckMoive(rs.getString("checkMoive"));
						}
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return ticket;
	}

	// ��ü ��ȭ ����
	public List<CinemaDTO> moivesInfo() {

		List<CinemaDTO> lists = new ArrayList<CinemaDTO>();

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql;

		try {
			sql = "select * from movies";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CinemaDTO dto = new CinemaDTO();

				dto.setMovieNo(rs.getInt(1));
				dto.setMovieName(rs.getString(2));
				dto.setMovieGenre(rs.getString(3));
				dto.setMovieTime(rs.getString(4));
				dto.setMovieSub(rs.getString(5));

				lists.add(dto);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lists;
	}

	// ���� ��ȭ ����
	public CinemaDTO movieInfo(int movieNum) {
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CinemaDTO dto = null;
		String sql;

		try {
			sql = "select * from movies where movieNo = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movieNum);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new CinemaDTO();
				dto.setMovieNo(rs.getInt(1));
				dto.setMovieName(rs.getString(2));
				dto.setMovieGenre(rs.getString(3));
				dto.setMovieTime(rs.getString(4));
				dto.setMovieSub(rs.getString(5));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return dto;
	}

	// ���� ����
	public List<CinemaDTO> reserveMovie(String id) {
		List<CinemaDTO> lists = new ArrayList<CinemaDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			// �������� ���̺�, ȸ�� ���̺� ���� (���̵�,�̸�, ��ȭ����)
			sql = "select a.userId, b.name, a.movieName  from booking a join members b ";
			sql += "on a.userId = b.id where a.userId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				CinemaDTO dto = new CinemaDTO();

				// �������� ����
				dto.setMovieName(rs.getString("movieName"));
				dto.setId(rs.getString("userId"));
				dto.setName(rs.getString("name"));

				lists.add(dto);

			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return lists;
	}

	// �������
	// ��ŷ ���̺� ���� > ��ũ���� ���̺� (�¼�+1, ��ȭ�̸� ��ȯ)
	public CinemaDTO cancelBooking(String movieName, String id) {
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		CinemaDTO movie = null;

		try {
			sql = "delete from booking where movieName like ? and userId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + movieName + "%");
			pstmt.setString(2, id);
			int result = pstmt.executeUpdate();

			if (result != 0) {
				// ���� �Ǹ� �ܿ��¼� +1
				sql = "update screenings set nowSeats = nowSeats+1 where movieName like ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + movieName + "%");
				int updateResult = pstmt.executeUpdate();

				if (updateResult != 0) {
					// ���� �Ǹ� ���ŵ� ��ȭ ������ ������
					sql = "select movieName from screenings where movieName like ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%" + movieName + "%");
					rs = pstmt.executeQuery();

					if (rs.next()) {
						// rs�� ������
						movie = new CinemaDTO();
						movie.setMovieName(rs.getString("movieName"));
					}
				}
				rs.close();
				pstmt.close();
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return movie;
	}

}
