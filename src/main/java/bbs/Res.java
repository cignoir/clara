package bbs;

public class Res {
	private int resNum; // レス番号
	private String name; // 名前
	private String time; // 時間
	private String id; // レスID:
	private String text; // 本文

	public boolean isRecruitment;
	public boolean isOpened;
	private String ip; // IP:Port
	private String frequency; // 希望対戦回数
	private String character; // 使用キャラ
	private String comment; // その他コメント
	
	private int parentResNum; // 再募集の場合の元の募集レス番号
	public boolean isDone = false; // コピー済みかどうか

	public Res(int resNum, String name, String time, String id, String text, boolean isRecruitment, boolean isOpened, String ip,
			String frequency, String character, String comment, int parentResNum) {
		this.setResNum(resNum);
		this.setName(name);
		this.setTime(time);
		this.setId(id);
		this.setText(convertHtmlChar(text));
		this.isRecruitment = isRecruitment;
		this.isOpened = isOpened;
		this.setIp(ip);
		this.setFrequency(frequency);
		this.setCharacter(character);
		this.setComment(comment);
		this.parentResNum = parentResNum;

	}
	
	private String convertHtmlChar(String text) {
		text = text.replace("&lt;", "<");
		text = text.replace("&gt;", ">");
		text = text.replace("&apos;", "'");
		text = text.replace("&quot;", "\"");
		return text;
	}

	public void setResNum(int resNum) {
		this.resNum = resNum;
	}

	public int getResNum() {
		return resNum;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getCharacter() {
		return character;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setParentResNum(int parentResNum) {
		this.parentResNum = parentResNum;
	}

	public int getParentResNum() {
		return parentResNum;
	}
}
