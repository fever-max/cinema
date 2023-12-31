package member;

import java.util.Scanner;

import cinema.CinemaDTO;
import movie.Movie;

public class Member {

	MemberDAO meberDao = new MemberDAO();
	Scanner sc = new Scanner(System.in);
	int ck;

	// 로그인 메서드
	public void login() {
		// 아이디, 비밀번호 입력 > db 체크
		System.out.print("   ID: ");
		String id = sc.next();
		System.out.print("   PW: ");
		String pw = sc.next();

		CinemaDTO user = meberDao.memberLogin(id, pw);
		Movie movie = new Movie();

		// 로그인 성공

		if (user != null) {

			while (true) {
				do {
					System.out.println();
					System.out.println();
					System.out.println("       ##       ######   ##       ##        ####      ##    ");
					System.out.println("       ##       ##       ##       ##       ##  ##     ##    ");
					System.out.println("       #####    ####     ##       ##       ##  ##     ##    ");
					System.out.println("       ##  ##   ##       ##       ##       ##  ##     ##    ");
					System.out.println("       ##  ##   ##       ##       ##       ##  ##            ");
					System.out.println("       ##  ##   ######   ######   ######    ####      ##    ");
					System.out.println();
					System.out.println("                      "
							+ (user.getId().equals("admin") ? "관리자님 환영합니다." : user.getName() + "님 환영합니다."));
					System.out.println();
					System.out.println("   =========================================================");
					System.out.println("   |    1.영화예매      2.영화정보    3.예매확인 및 취소   |");
					System.out.println("   |    4.관리자페이지  5.로그아웃    6.종료               |");
					System.out.println("   =========================================================");
					System.out.print("   ▶ ");
					ck = sc.nextInt();
				} while (ck < 1);

				switch (ck) {
				case 1:
					movie.ticketingMoive(user.getId());
					break;
				case 2:
					movie.info();
					break;
				case 3:
					movie.checkMoive(user.getId(), user.getName());
					break;
				case 4:
					manager(user.getId(), user.getName());
					break;
				case 5:
					System.out.println("   메인으로 돌아갑니다.");
					return;
				case 6:
					System.out.println("   예매 시스템을 종료합니다.");
					System.exit(0);
				default:
					System.out.println("   올바른 번호를 입력하세요.");
					break;
				}

			}

		}

	}

	// 회원가입
	public void join() {
		CinemaDTO dto = new CinemaDTO();
		System.out.println();
		System.out.println("                ##     ####     ####    ##  ##  ");
		System.out.println("                ##    ##  ##     ##     ### ##  ");
		System.out.println("                ##    ##  ##     ##     ######  ");
		System.out.println("                ##    ##  ##     ##     ## ###  ");
		System.out.println("                ##    ##  ##     ##     ##  ##  ");
		System.out.println("              ####     ####     ####    ##  ##  ");
		System.out.println();
		System.out.println("                   회원가입을 시작합니다.");
		System.out.println("   =========================================================");

		// 아이디 중복 체크
		while (true) {
			System.out.print("   ID 입력: ");
			String id = sc.next();

			if (id.length() < 6 || id.length() > 15) {
				System.out.println("   6글자 이상 15이하 ID만 입력 가능합니다.");
				continue;
			}

			if (meberDao.isIdDuplicate(id)) {
				System.out.println("   이미 사용 중인 아이디입니다. 다른 아이디를 입력하세요.");
			} else {
				dto.setId(id);
				break;
			}
		}

		// 패스워드 체크 (중복 입력 체크)
		while (true) {
			System.out.print("   PW 입력: ");
			String pw = sc.next();
			System.out.print("   PW 확인: ");
			String pw2 = sc.next();

			if (!pw.equals(pw2)) {
				System.out.println("   비밀번호가 동일하지 않습니다.");
			}

			if (pw.equals(pw2)) {
				dto.setPw(pw);
				break;
			}
		}

		// 이름 체크 (한글 2~4 글자)
		while (true) {
			System.out.print("   이름(ex.한글 2~4글자): ");
			String name = sc.next();

			if (name.length() >= 2 && name.length() <= 4 && isKorean(name)) {
				dto.setName(name);
				break;
			} else {
				System.out.println("   2~4글자의 한글만 입력 가능합니다.");
			}
		}

		// 생일 6숫자 (정규화)
		while (true) {
			System.out.print("   생일 (ex.970411): ");
			String userInput = sc.next();

			if (userInput.matches("\\d{6}")) {// 정규화
				// 6자리 숫자인 경우
				dto.setBirth(Integer.parseInt(userInput));
				break;
			} else {
				System.out.println("6자리 숫자를 입력하세요. ex.970411");
			}

		}

		// 전화번호 010-xxxx-xxxx 형식
		while (true) {
			System.out.print("   전화번호(ex.010-xxxx-xxxx): ");
			String tel = sc.next();

			if (tel.length() == 13) {
				// 11자리일 경우
				dto.setTel(tel);
				break;
			} else {
				System.out.println("   형식에 맞춰 입력하세요. ex.010-xxxx-xxxx");
			}
		}

		// 데이터 저장
		int result = meberDao.joinData(dto);

		if (result != 0) {// 값이 있으면
			System.out.println("   회원가입 완료");
		} else {
			System.out.println("   회원가입 실패");
		}

	}

	// 이름 한글 체크
	private boolean isKorean(String name) {
		for (char c : name.toCharArray()) {
			// 각 문자가 한글 범위에 있는지 확인
			if ((c < '가' || c > '힣') && (c < 'ㄱ' || c > 'ㅎ')) {
				return false;
			}
		}
		return true;
	}

	private void manager(String id, String name) {

		if (id.equals("admin")) {
			System.out.println("   관리자 페이지입니다.");
			// MemberDAO meberDao = new MemberDAO();
			int ch;
			while (true) {
				do {
					System.out.println();
					System.out.println("   =========================================================");
					System.out.println("   |     1.회원출력 2.회원검색 3.회원예매내역  4.HOME      |");
					System.out.println("   =========================================================");
					System.out.print("   ▶ ");
					ch = sc.nextInt();
				} while (ch < 1);

				switch (ch) {
				case 1:
					meberDao.printAll();
					break;
				case 2:
					System.out.print("   검색할 회원 아이디: ");
					String userId = sc.next();
					meberDao.printOne(userId);
					break;
				case 3:
					meberDao.reserveAll();
					break;
				case 4:
					System.out.println("   메인으로 돌아갑니다.");
					return;
				default:
					System.out.println("   잘못된 번호 입력");
					break;

				}

			}

		} else {
			System.out.println("   관리자 권한이 없습니다.");
			return;
		}

	}

}
