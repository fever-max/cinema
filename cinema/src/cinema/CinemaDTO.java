package cinema;

public class CinemaDTO {

	// ȸ����������
	private String id;
	private String pw;
	private String name;
	private int birth;
	private String tel;

	// ��ȭ����
	private int movieNo;
	private String movieName;
	private String movieGenre;
	private String movieTime;
	private String movieSub;

	// ��ȭ������
	private int fullSeats; // ��ü�¼�
	private int nowSeats; // �ܿ��¼�

	// ��������
	private String checkMoive; // ����Ȯ��

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBirth() {
		return birth;
	}

	public void setBirth(int birth) {
		this.birth = birth;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getMovieNo() {
		return movieNo;
	}

	public void setMovieNo(int movieNo) {
		this.movieNo = movieNo;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getMovieGenre() {
		return movieGenre;
	}

	public void setMovieGenre(String movieGenre) {
		this.movieGenre = movieGenre;
	}

	public String getMovieTime() {
		return movieTime;
	}

	public void setMovieTime(String movieTime) {
		this.movieTime = movieTime;
	}

	public String getMovieSub() {
		return movieSub;
	}

	public void setMovieSub(String movieSub) {
		this.movieSub = movieSub;
	}

	public int getFullSeats() {
		return fullSeats;
	}

	public void setFullSeats(int fullSeats) {
		this.fullSeats = fullSeats;
	}

	public int getNowSeats() {
		return nowSeats;
	}

	public void setNowSeats(int nowSeats) {
		this.nowSeats = nowSeats;
	}

	public String getCheckMoive() {
		return checkMoive;
	}

	public void setCheckMoive(String checkMoive) {
		this.checkMoive = checkMoive;
	}

}
