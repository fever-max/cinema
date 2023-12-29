package movie;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import cinema.CinemaDTO;

public class Movie {

	Scanner sc = new Scanner(System.in);

	MovieDAO movieDao = new MovieDAO();

	// ��ȭ ���� �޼���
	public void ticketingMoive(String userId) {
		List<CinemaDTO> movieList = movieDao.getMovies();

		System.out.println();
		System.out.println("   =======================���� ����=======================");
		System.out.println();

		for (CinemaDTO movie : movieList) {
			System.out.println("   " + movie.getMovieNo() + "." + movie.getMovieName() + " (��ü�¼�: "
					+ movie.getFullSeats() + "/ �����¼�: " + movie.getNowSeats() + ")");
		}
		System.out.println();
		System.out.println("   =========================================================");

		int ck = 1;

		System.out.print("   ������ ��ȭ ��ȣ�� ");
		int movieNum = sc.nextInt();

		CinemaDTO ticket = movieDao.ticketing(movieNum, userId);

		// ��ȭ ���� ���� ����
		if (ticket != null) { // ���� ������
			System.out.println();
			System.out.println("   ~~--~--~--~~--~--~--~~--~--~--~~--~--~--~~--~--~--");
			System.out.println("   .,;,.;~.~;.,;,.;~.~;.,;,.;~.~;.,;,.;~.~;.,;,.;~.~;");
			System.out.println("   :~,~:,-:-,:~,~:,-:-,:~,~:,-:-,:~,~:,-:-,:~,~:,-:-,");
			System.out.println("   ;~.~;.,-                                  .~;.,;,.");
			System.out.println("   .,;,.;~      TICKET                       ~,.;~.~;");
			System.out.println("   --~--~-      " + ticket.getMovieName());
			System.out.println("   ;~.~;.,,                                 .,~;.,;,.");
			System.out.println("   ~---~--,                                 ,;-~--~--");
			System.out.println("   ;~.~;.-      �̿����ּż� �����մϴ�.    .,:;.,;,.");
			System.out.println("   .,.;~.                                   ,;,.;~.~;");
			System.out.println("   ,-:-,:~,~:,-:-,:~,~:,-:-,:~,~:,-:-,:~,~:,-:-,:~,~:");
			System.out.println("   ;~.~;.,;,.;~.~;.,;,.;~.~;.,;,.;~.~;.,;,.;~.~;.,;,.");
			System.out.println("   --~--~~-~~--~--~~-~~--~--~~-~~--~--~~-~~--~--~~-~~");
			System.out.println("   " + ticket.getMovieName() + " ���� �Ϸ�");
			System.out.println();

			while (true) {
				do {
					System.out.println("   1.�߰����� 2.HOME");
					System.out.print("   �� ");
					ck = sc.nextInt();
				} while (ck < 1);

				switch (ck) {
				case 1:
					ticketingMoive(userId);
					break;
				case 2:
					return;
				default:
					System.out.println("   �ùٸ� ��ȭ ��ȣ�� �Է����ּ���.");
					break;
				}
			}

		} else {
			System.out.println("   " + "���� ����");
		}
	}

	// ��ȭ����
	public void info() {
		System.out.println();
		System.out.println("   =======================���� ����=======================");
		System.out.println("   |    1.�뷮   2.������ ��   3.����Ƹǰ� �ν�Ʈ ŷ��    |");
		System.out.println("   |                                                       |");
		System.out.println("   |      4. ����ġ ��ī����-���۽� 5.���� 6.��ü����      |");
		System.out.println("   =========================================================");
		System.out.print("   �� ���� ��");
		int movieNum = sc.nextInt();

		if (movieNum == 6) {
			List<CinemaDTO> lists = movieDao.moivesInfo();
			Iterator<CinemaDTO> it = lists.iterator();

			while (it.hasNext()) {
				// ��ȭ ���� ��ü���
				CinemaDTO movieAll = it.next();
				System.out.println();
				System.out.println("   ========================��ȭ ����========================");
				System.out.println("   ��ȭ��: " + movieAll.getMovieName());
				System.out.println("   �帣: " + movieAll.getMovieGenre() + "   ����Ÿ��: " + movieAll.getMovieTime());
				System.out.println("   " + movieAll.getMovieSub());
				System.out.println("   =========================================================");
			}

		} else {
			CinemaDTO dto = movieDao.movieInfo(movieNum);

			while (true) {
				if (dto != null) {
					System.out.println();
					System.out.println("   ========================��ȭ ����========================");
					System.out.println("   ��ȭ��: " + dto.getMovieName());
					System.out.println("   �帣: " + dto.getMovieGenre() + "   ����Ÿ��: " + dto.getMovieTime());
					System.out.println("   " + dto.getMovieSub());
					System.out.println("   =========================================================");

					System.out.println("   1.�ڷΰ��� 2.HOME");
					System.out.print("   �� ");
					int ck = sc.nextInt();

					switch (ck) {
					case 1:
						info();
						break;
					case 2:
						return;
					default:
						System.out.println("   �ùٸ� ��ȣ�� �Է��ϼ���.");
						break;
					}

				} else {
					System.out.println("   �ùٸ� ��ȭ ��ȣ�� �Է����ּ���.");
					break;
				}
				return;
			}

		}

	}

	// ���� Ȯ�� �� ���
	public void checkMoive(String id, String name) {

		// ���� ����
		List<CinemaDTO> lists = movieDao.reserveMovie(id);

		// �޾ƿ� ������ ���
		Iterator<CinemaDTO> it = lists.iterator();
		System.out.println();
		System.out.println("   ========================���� ����========================");
		System.out.println();
		System.out.println("   " + name + "���� ���ų���");

		int i = 1;

		if (lists.isEmpty()) {
			System.out.println("   ���� ������ �����ϴ�.");
		} else {
			while (it.hasNext()) {
				CinemaDTO dto = (CinemaDTO) it.next();
				System.out.println("   " + i + ". " + dto.getMovieName());
				i++;
			}
			i = 1; // ���� �ʱ�ȭ
		}
		System.out.println();
		System.out.println("   =========================================================");

		int ck = 1;
		while (true) {
			do {
				System.out.println("   1.������� 2.�ڷΰ���");
				System.out.print("   �� ");
				ck = sc.nextInt();
			} while (ck < 1);

			switch (ck) {
			case 1:
				System.out.println("   ����� ��ȭ �̸�: ");
				System.out.print("   �� ");
				String movieName = sc.next();
				sc.nextLine(); // ���� ���� �Һ�

				CinemaDTO dto = movieDao.cancelBooking(movieName, id);

				if (dto != null) {
					System.out.println("   " + dto.getMovieName() + " ���� ��� �Ϸ�");
					return;
				} else {
					System.out.println("   ���� ��� ����");
				}

				break;
			case 2:
				return;
			default:
				System.out.println("   �ùٸ� ��ȣ�� �Է��ϼ���.");
				break;
			}

		}

	}

}
