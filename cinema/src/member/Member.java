package member;

import java.util.Scanner;

import cinema.CinemaDTO;
import movie.Movie;

public class Member {

	MemberDAO meberDao = new MemberDAO();
	Scanner sc = new Scanner(System.in);
	int ck;

	// ·Î±×ÀÎ ¸Þ¼­µå
	public void login() {
		// ¾ÆÀÌµð, ºñ¹Ð¹øÈ£ ÀÔ·Â > db Ã¼Å©
		System.out.print("   ID: ");
		String id = sc.next();
		System.out.print("   PW: ");
		String pw = sc.next();

		CinemaDTO user = meberDao.memberLogin(id, pw);
		Movie movie = new Movie();

		// ·Î±×ÀÎ ¼º°ø

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
							+ (user.getId().equals("admin") ? "°ü¸®ÀÚ´Ô È¯¿µÇÕ´Ï´Ù." : user.getName() + "´Ô È¯¿µÇÕ´Ï´Ù."));
					System.out.println();
					System.out.println("   =========================================================");
					System.out.println("   |    1.¿µÈ­¿¹¸Å      2.¿µÈ­Á¤º¸    3.¿¹¸ÅÈ®ÀÎ ¹× Ãë¼Ò   |");
					System.out.println("   |    4.°ü¸®ÀÚÆäÀÌÁö  5.·Î±×¾Æ¿ô    5.Á¾·á               |");
					System.out.println("   =========================================================");
					System.out.print("   ¢º ");
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
					System.out.println("   ¸ÞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
					return;
				case 6:
					System.out.println("   ¿¹¸Å ½Ã½ºÅÛÀ» Á¾·áÇÕ´Ï´Ù.");
					System.exit(0);
				default:
					System.out.println("   ¿Ã¹Ù¸¥ ¹øÈ£¸¦ ÀÔ·ÂÇÏ¼¼¿ä.");
					break;
				}

			}

		}

	}

	// È¸¿ø°¡ÀÔ
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
		System.out.println("                   È¸¿ø°¡ÀÔÀ» ½ÃÀÛÇÕ´Ï´Ù.");
		System.out.println("   =========================================================");

		// ¾ÆÀÌµð Áßº¹ Ã¼Å©
		while (true) {
			System.out.print("   ID ÀÔ·Â: ");
			String id = sc.next();

			if (id.length() < 6 || id.length() > 15) {
				System.out.println("   6±ÛÀÚ ÀÌ»ó 15ÀÌÇÏ ID¸¸ ÀÔ·Â °¡´ÉÇÕ´Ï´Ù.");
				continue;
			}

			if (meberDao.isIdDuplicate(id)) {
				System.out.println("   ÀÌ¹Ì »ç¿ë ÁßÀÎ ¾ÆÀÌµðÀÔ´Ï´Ù. ´Ù¸¥ ¾ÆÀÌµð¸¦ ÀÔ·ÂÇÏ¼¼¿ä.");
			} else {
				dto.setId(id);
				break;
			}
		}

		// ÆÐ½º¿öµå Ã¼Å© (Áßº¹ ÀÔ·Â Ã¼Å©)
		while (true) {
			System.out.print("   PW ÀÔ·Â: ");
			String pw = sc.next();
			System.out.print("   PW È®ÀÎ: ");
			String pw2 = sc.next();

			if (!pw.equals(pw2)) {
				System.out.println("   ºñ¹Ð¹øÈ£°¡ µ¿ÀÏÇÏÁö ¾Ê½À´Ï´Ù.");
			}

			if (pw.equals(pw2)) {
				dto.setPw(pw);
				break;
			}
		}

		// ÀÌ¸§ Ã¼Å© (ÇÑ±Û 2~4 ±ÛÀÚ)
		while (true) {
			System.out.print("   ÀÌ¸§(ex.ÇÑ±Û 2~4±ÛÀÚ): ");
			String name = sc.next();

			if (name.length() >= 2 && name.length() <= 4 && isKorean(name)) {
				dto.setName(name);
				break;
			} else {
				System.out.println("   2~4±ÛÀÚÀÇ ÇÑ±Û¸¸ ÀÔ·Â °¡´ÉÇÕ´Ï´Ù.");
			}
		}

		// »ýÀÏ 6¼ýÀÚ (Á¤±ÔÈ­)
		while (true) {
			System.out.print("   »ýÀÏ (ex.970411): ");
			String userInput = sc.next();

			if (userInput.matches("\\d{6}")) {// Á¤±ÔÈ­
				// 6ÀÚ¸® ¼ýÀÚÀÎ °æ¿ì
				dto.setBirth(Integer.parseInt(userInput));
				break;
			} else {
				System.out.println("6ÀÚ¸® ¼ýÀÚ¸¦ ÀÔ·ÂÇÏ¼¼¿ä. ex.970411");
			}

		}

		// ÀüÈ­¹øÈ£ 010-xxxx-xxxx Çü½Ä
		while (true) {
			System.out.print("   ÀüÈ­¹øÈ£(ex.010-xxxx-xxxx): ");
			String tel = sc.next();

			if (tel.length() == 13) {
				// 11ÀÚ¸®ÀÏ °æ¿ì
				dto.setTel(tel);
				break;
			} else {
				System.out.println("   Çü½Ä¿¡ ¸ÂÃç ÀÔ·ÂÇÏ¼¼¿ä. ex.010-xxxx-xxxx");
			}
		}

		// µ¥ÀÌÅÍ ÀúÀå
		int result = meberDao.joinData(dto);

		if (result != 0) {// °ªÀÌ ÀÖÀ¸¸é
			System.out.println("   È¸¿ø°¡ÀÔ ¿Ï·á");
		} else {
			System.out.println("   È¸¿ø°¡ÀÔ ½ÇÆÐ");
		}

	}

	// ÀÌ¸§ ÇÑ±Û Ã¼Å©
	private boolean isKorean(String name) {
		for (char c : name.toCharArray()) {
			// °¢ ¹®ÀÚ°¡ ÇÑ±Û ¹üÀ§¿¡ ÀÖ´ÂÁö È®ÀÎ
			if ((c < '°¡' || c > 'ÆR') && (c < '¤¡' || c > '¤¾')) {
				return false;
			}
		}
		return true;
	}

	private void manager(String id, String name) {

		if (id.equals("admin")) {
			System.out.println("   °ü¸®ÀÚ ÆäÀÌÁöÀÔ´Ï´Ù.");
			// MemberDAO meberDao = new MemberDAO();
			int ch;
			while (true) {
				do {
					System.out.println();
					System.out.println("   =========================================================");
					System.out.println("   |     1.È¸¿øÃâ·Â 2.È¸¿ø°Ë»ö 3.È¸¿ø¿¹¸Å³»¿ª  4.HOME      |");
					System.out.println("   =========================================================");
					System.out.print("   ¢º ");
					ch = sc.nextInt();
				} while (ch < 1);

				switch (ch) {
				case 1:
					meberDao.printAll();
					break;
				case 2:
					System.out.print("   °Ë»öÇÒ È¸¿ø ¾ÆÀÌµð: ");
					String userId = sc.next();
					meberDao.printOne(userId);
					break;
				case 3:
					meberDao.reserveAll();
					break;
				case 4:
					System.out.println("   ¸ÞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
					return;
				default:
					System.out.println("   Àß¸øµÈ ¹øÈ£ ÀÔ·Â");
					break;

				}

			}

		} else {
			System.out.println("   °ü¸®ÀÚ ±ÇÇÑÀÌ ¾ø½À´Ï´Ù.");
			return;
		}

	}

}
