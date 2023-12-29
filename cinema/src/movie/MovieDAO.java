package movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cinema.CinemaDTO;
import db.DBConn;

public class MovieDAO {

	// 영화 상영 정보 (예매좌석)
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

	// 영화 예매
	// 부킹테이블 (중복여부 체크) > 스크리닝 테이블 (좌석체크) > 부킹테이블 (값 입력)
	public CinemaDTO ticketing(int movieNum, String userId) {
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		CinemaDTO ticket = null; // 반환값 생성

		try {
			// 부킹 테이블 체크 (중복 예매 확인)

			sql = "select * from booking where movieNo = ? and userId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movieNum);
			pstmt.setString(2, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// 예매 내역이 있으면
				System.out.println("   이미 예매된 영화입니다.");
				return null;
			}

			// 스크리닝 테이블 셀렉트 (예매좌석 체크)
			sql = "select * from screenings where movieNo = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movieNum);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 값이 있으면
				int nowSeats = rs.getInt("nowSeats"); // 현재 좌석 저장

				if (nowSeats > 0) {
					// 좌석이 남아있으면
					sql = "insert into booking (movieNo, movieName, userId, checkMoive) values (?, ?, ?, ?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, rs.getInt("movieNo"));
					pstmt.setString(2, rs.getString("movieName"));
					pstmt.setString(3, userId);
					pstmt.setString(4, "1");

					int ck = pstmt.executeUpdate();

					if (ck > 0) {
						// 예매 성공시, 업데이트로 좌석수 -1 변경
						sql = "update screenings set nowSeats = nowSeats - 1 where movieNo = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, movieNum);
						pstmt.executeUpdate();

						// 예매 정보 조회 후 반환
						sql = "select * from booking where movieNo = ? and userId = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, rs.getInt("movieNo"));
						pstmt.setString(2, userId);
						rs = pstmt.executeQuery();

						if (rs.next()) {
							// 예매 정보 저장
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

	// 전체 영화 정보
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

	// 개별 영화 정보
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

	// 예매 정보
	public List<CinemaDTO> reserveMovie(String id) {
		List<CinemaDTO> lists = new ArrayList<CinemaDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			// 예매정보 테이블, 회원 테이블 조인 (아이디,이름, 영화정보)
			sql = "select a.userId, b.name, a.movieName  from booking a join members b ";
			sql += "on a.userId = b.id where a.userId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				CinemaDTO dto = new CinemaDTO();

				// 예매정보 저장
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

	// 예매취소
	// 부킹 테이블 삭제 > 스크리닝 테이블 (좌석+1, 영화이름 반환)
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
				// 삭제 되면 잔여좌석 +1
				sql = "update screenings set nowSeats = nowSeats+1 where movieName like ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + movieName + "%");
				int updateResult = pstmt.executeUpdate();

				if (updateResult != 0) {
					// 삭제 되면 예매된 영화 정보를 가져옴
					sql = "select movieName from screenings where movieName like ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%" + movieName + "%");
					rs = pstmt.executeQuery();

					if (rs.next()) {
						// rs가 있으면
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
