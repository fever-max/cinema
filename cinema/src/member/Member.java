package member;

import java.util.Scanner;

import cinema.CinemaDTO;
import movie.Movie;

public class Member {

	MemberDAO meberDao = new MemberDAO();
	Scanner sc = new Scanner(System.in);
	int ck;

	// �α��� �޼���
	public void login() {
		// ���̵�, ��й�ȣ �Է� > db üũ
		System.out.print("   ID: ");
		String id = sc.next();
		System.out.print("   PW: ");
		String pw = sc.next();

		CinemaDTO user = meberDao.memberLogin(id, pw);
		Movie movie = new Movie();

		// �α��� ����

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
							+ (user.getId().equals("admin") ? "�����ڴ� ȯ���մϴ�." : user.getName() + "�� ȯ���մϴ�."));
					System.out.println();
					System.out.println("   =========================================================");
					System.out.println("   |    1.��ȭ����      2.��ȭ����    3.����Ȯ�� �� ���   |");
					System.out.println("   |    4.������������  5.�α׾ƿ�    5.����               |");
					System.out.println("   =========================================================");
					System.out.print("   �� ");
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
					System.out.println("   �������� ���ư��ϴ�.");
					return;
				case 6:
					System.out.println("   ���� �ý����� �����մϴ�.");
					System.exit(0);
				default:
					System.out.println("   �ùٸ� ��ȣ�� �Է��ϼ���.");
					break;
				}

			}

		}

	}

	// ȸ������
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
		System.out.println("                   ȸ�������� �����մϴ�.");
		System.out.println("   =========================================================");

		// ���̵� �ߺ� üũ
		while (true) {
			System.out.print("   ID �Է�: ");
			String id = sc.next();

			if (id.length() < 6 || id.length() > 15) {
				System.out.println("   6���� �̻� 15���� ID�� �Է� �����մϴ�.");
				continue;
			}

			if (meberDao.isIdDuplicate(id)) {
				System.out.println("   �̹� ��� ���� ���̵��Դϴ�. �ٸ� ���̵� �Է��ϼ���.");
			} else {
				dto.setId(id);
				break;
			}
		}

		// �н����� üũ (�ߺ� �Է� üũ)
		while (true) {
			System.out.print("   PW �Է�: ");
			String pw = sc.next();
			System.out.print("   PW Ȯ��: ");
			String pw2 = sc.next();

			if (!pw.equals(pw2)) {
				System.out.println("   ��й�ȣ�� �������� �ʽ��ϴ�.");
			}

			if (pw.equals(pw2)) {
				dto.setPw(pw);
				break;
			}
		}

		// �̸� üũ (�ѱ� 2~4 ����)
		while (true) {
			System.out.print("   �̸�(ex.�ѱ� 2~4����): ");
			String name = sc.next();

			if (name.length() >= 2 && name.length() <= 4 && isKorean(name)) {
				dto.setName(name);
				break;
			} else {
				System.out.println("   2~4������ �ѱ۸� �Է� �����մϴ�.");
			}
		}

		// ���� 6���� (����ȭ)
		while (true) {
			System.out.print("   ���� (ex.970411): ");
			String userInput = sc.next();

			if (userInput.matches("\\d{6}")) {// ����ȭ
				// 6�ڸ� ������ ���
				dto.setBirth(Integer.parseInt(userInput));
				break;
			} else {
				System.out.println("6�ڸ� ���ڸ� �Է��ϼ���. ex.970411");
			}

		}

		// ��ȭ��ȣ 010-xxxx-xxxx ����
		while (true) {
			System.out.print("   ��ȭ��ȣ(ex.010-xxxx-xxxx): ");
			String tel = sc.next();

			if (tel.length() == 13) {
				// 11�ڸ��� ���
				dto.setTel(tel);
				break;
			} else {
				System.out.println("   ���Ŀ� ���� �Է��ϼ���. ex.010-xxxx-xxxx");
			}
		}

		// ������ ����
		int result = meberDao.joinData(dto);

		if (result != 0) {// ���� ������
			System.out.println("   ȸ������ �Ϸ�");
		} else {
			System.out.println("   ȸ������ ����");
		}

	}

	// �̸� �ѱ� üũ
	private boolean isKorean(String name) {
		for (char c : name.toCharArray()) {
			// �� ���ڰ� �ѱ� ������ �ִ��� Ȯ��
			if ((c < '��' || c > '�R') && (c < '��' || c > '��')) {
				return false;
			}
		}
		return true;
	}

	private void manager(String id, String name) {

		if (id.equals("admin")) {
			System.out.println("   ������ �������Դϴ�.");
			// MemberDAO meberDao = new MemberDAO();
			int ch;
			while (true) {
				do {
					System.out.println();
					System.out.println("   =========================================================");
					System.out.println("   |     1.ȸ����� 2.ȸ���˻� 3.ȸ�����ų���  4.HOME      |");
					System.out.println("   =========================================================");
					System.out.print("   �� ");
					ch = sc.nextInt();
				} while (ch < 1);

				switch (ch) {
				case 1:
					meberDao.printAll();
					break;
				case 2:
					System.out.print("   �˻��� ȸ�� ���̵�: ");
					String userId = sc.next();
					meberDao.printOne(userId);
					break;
				case 3:
					meberDao.reserveAll();
					break;
				case 4:
					System.out.println("   �������� ���ư��ϴ�.");
					return;
				default:
					System.out.println("   �߸��� ��ȣ �Է�");
					break;

				}

			}

		} else {
			System.out.println("   ������ ������ �����ϴ�.");
			return;
		}

	}

}
