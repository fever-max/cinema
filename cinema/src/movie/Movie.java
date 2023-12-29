package movie;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import cinema.CinemaDTO;

public class Movie {

	Scanner sc = new Scanner(System.in);

	MovieDAO movieDao = new MovieDAO();

	// 영화 예매 메서드
	public void ticketingMoive(String userId) {
		List<CinemaDTO> movieList = movieDao.getMovies();

		System.out.println();
		System.out.println("   =======================현재 상영작=======================");
		System.out.println();

		for (CinemaDTO movie : movieList) {
			System.out.println("   " + movie.getMovieNo() + "." + movie.getMovieName() + " (전체좌석: "
					+ movie.getFullSeats() + "/ 현재좌석: " + movie.getNowSeats() + ")");
		}
		System.out.println();
		System.out.println("   =========================================================");

		int ck = 1;

		System.out.print("   예매할 영화 번호▶ ");
		int movieNum = sc.nextInt();

		CinemaDTO ticket = movieDao.ticketing(movieNum, userId);

		// 영화 예매 성공 여부
		if (ticket != null) { // 값이 있으면
			System.out.println();
			System.out.println("   ~~--~--~--~~--~--~--~~--~--~--~~--~--~--~~--~--~--");
			System.out.println("   .,;,.;~.~;.,;,.;~.~;.,;,.;~.~;.,;,.;~.~;.,;,.;~.~;");
			System.out.println("   :~,~:,-:-,:~,~:,-:-,:~,~:,-:-,:~,~:,-:-,:~,~:,-:-,");
			System.out.println("   ;~.~;.,-                                  .~;.,;,.");
			System.out.println("   .,;,.;~      TICKET                       ~,.;~.~;");
			System.out.println("   --~--~-      " + ticket.getMovieName());
			System.out.println("   ;~.~;.,,                                 .,~;.,;,.");
			System.out.println("   ~---~--,                                 ,;-~--~--");
			System.out.println("   ;~.~;.-      이용해주셔서 감사합니다.    .,:;.,;,.");
			System.out.println("   .,.;~.                                   ,;,.;~.~;");
			System.out.println("   ,-:-,:~,~:,-:-,:~,~:,-:-,:~,~:,-:-,:~,~:,-:-,:~,~:");
			System.out.println("   ;~.~;.,;,.;~.~;.,;,.;~.~;.,;,.;~.~;.,;,.;~.~;.,;,.");
			System.out.println("   --~--~~-~~--~--~~-~~--~--~~-~~--~--~~-~~--~--~~-~~");
			System.out.println("   " + ticket.getMovieName() + " 예매 완료");
			System.out.println();

			while (true) {
				do {
					System.out.println("   1.추가예매 2.HOME");
					System.out.print("   ▶ ");
					ck = sc.nextInt();
				} while (ck < 1);

				switch (ck) {
				case 1:
					ticketingMoive(userId);
					break;
				case 2:
					return;
				default:
					System.out.println("   올바른 영화 번호를 입력해주세요.");
					break;
				}
			}

		} else {
			System.out.println("   " + "예매 실패");
		}
	}

	// 영화정보
	public void info() {
		System.out.println();
		System.out.println("   =======================현재 상영작=======================");
		System.out.println("   |    1.노량   2.서울의 봄   3.아쿠아맨과 로스트 킹덤    |");
		System.out.println("   |                                                       |");
		System.out.println("   |      4. 류이치 사카모토-오퍼스 5.괴물 6.전체보기      |");
		System.out.println("   =========================================================");
		System.out.print("   상세 정보 ▶");
		int movieNum = sc.nextInt();

		if (movieNum == 6) {
			List<CinemaDTO> lists = movieDao.moivesInfo();
			Iterator<CinemaDTO> it = lists.iterator();

			while (it.hasNext()) {
				// 영화 정보 전체출력
				CinemaDTO movieAll = it.next();
				System.out.println();
				System.out.println("   ========================영화 정보========================");
				System.out.println("   영화명: " + movieAll.getMovieName());
				System.out.println("   장르: " + movieAll.getMovieGenre() + "   러닝타임: " + movieAll.getMovieTime());
				System.out.println("   " + movieAll.getMovieSub());
				System.out.println("   =========================================================");
			}

		} else {
			CinemaDTO dto = movieDao.movieInfo(movieNum);

			while (true) {
				if (dto != null) {
					System.out.println();
					System.out.println("   ========================영화 정보========================");
					System.out.println("   영화명: " + dto.getMovieName());
					System.out.println("   장르: " + dto.getMovieGenre() + "   러닝타임: " + dto.getMovieTime());
					System.out.println("   " + dto.getMovieSub());
					System.out.println("   =========================================================");

					System.out.println("   1.뒤로가기 2.HOME");
					System.out.print("   ▶ ");
					int ck = sc.nextInt();

					switch (ck) {
					case 1:
						info();
						break;
					case 2:
						return;
					default:
						System.out.println("   올바른 번호를 입력하세요.");
						break;
					}

				} else {
					System.out.println("   올바른 영화 번호를 입력해주세요.");
					break;
				}
				return;
			}

		}

	}

	// 예매 확인 및 취소
	public void checkMoive(String id, String name) {

		// 예매 정보
		List<CinemaDTO> lists = movieDao.reserveMovie(id);

		// 받아온 데이터 출력
		Iterator<CinemaDTO> it = lists.iterator();
		System.out.println();
		System.out.println("   ========================예매 내역========================");
		System.out.println();
		System.out.println("   " + name + "님의 예매내역");

		int i = 1;

		if (lists.isEmpty()) {
			System.out.println("   예매 내역이 없습니다.");
		} else {
			while (it.hasNext()) {
				CinemaDTO dto = (CinemaDTO) it.next();
				System.out.println("   " + i + ". " + dto.getMovieName());
				i++;
			}
			i = 1; // 순서 초기화
		}
		System.out.println();
		System.out.println("   =========================================================");

		int ck = 1;
		while (true) {
			do {
				System.out.println("   1.예매취소 2.뒤로가기");
				System.out.print("   ▶ ");
				ck = sc.nextInt();
			} while (ck < 1);

			switch (ck) {
			case 1:
				System.out.println("   취소할 영화 이름: ");
				System.out.print("   ▶ ");
				String movieName = sc.next();
				sc.nextLine(); // 개행 문자 소비

				CinemaDTO dto = movieDao.cancelBooking(movieName, id);

				if (dto != null) {
					System.out.println("   " + dto.getMovieName() + " 예매 취소 완료");
					return;
				} else {
					System.out.println("   예매 취소 실패");
				}

				break;
			case 2:
				return;
			default:
				System.out.println("   올바른 번호를 입력하세요.");
				break;
			}

		}

	}

}
