package cinema;

import java.util.Scanner;

import member.Member;
import movie.Movie;

public class CinemaMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Member member = new Member();
		Movie movie = new Movie();

		int ch;

		while (true) {
			do {
				System.out.println("      " + " __       __   ______   __     __  ______  ________ ");
				System.out.println("      " + "/  \\     /  | /      \\ /  |   /  |/      |/        |");
				System.out.println("      " + "$$  \\   /$$ |/$$$$$$  |$$ |   $$ |$$$$$$/ $$$$$$$$/ ");
				System.out.println("      " + "$$$  \\ /$$$ |$$ |  $$ |$$ |   $$ |  $$ |  $$ |__    ");
				System.out.println("      " + "$$$$  /$$$$ |$$ |  $$ |$$  \\ /$$/   $$ |  $$    |   ");
				System.out.println("      " + "$$ $$ $$/$$ |$$ |  $$ | $$  /$$/    $$ |  $$$$$/    ");
				System.out.println("      " + "$$ |$$$/ $$ |$$ \\__$$ |  $$ $$/    _$$ |_ $$ |_____ ");
				System.out.println("      " + "$$ | $/  $$ |$$    $$/    $$$/    / $$   |$$       |");
				System.out.println("      " + "$$/      $$/  $$$$$$/      $/     $$$$$$/ $$$$$$$$/ ");
				System.out.println("                                                    ");
				System.out.println("   =========================================================");
				System.out.println("   |        1.로그인 2.회원가입 3.상영중영화 4.종료        |");
				System.out.println("   =========================================================");
				System.out.print("   ▶ ");
				ch = sc.nextInt();
			} while (ch < 1);

			switch (ch) {
			case 1:
				member.login();
				break;
			case 2:
				member.join();
				break;
			case 3:
				movie.info();
				break;
			case 4:
				System.out.println();

				System.out.println("    #####    #####    ##  ##   ######     ##       ##    ");
				System.out.println("    ##  ##   ##  ##   ##  ##   ##         ##       ##    ");
				System.out.println("    #####    #####     ####    ####       ##       ##    ");
				System.out.println("    ##  ##   ##  ##     ##     ##         ##       ##    ");
				System.out.println("    ##  ##   ##  ##     ##     ##                        ");
				System.out.println("    #####    #####      ##     ######     ##       ##    ");

				System.out.println();
				System.out.println("    예매 시스템을 종료합니다.");
				System.exit(0);
			default:
				System.out.println("    올바른 번호를 입력해주세요.");
				break;
			}

		}

	}

}
