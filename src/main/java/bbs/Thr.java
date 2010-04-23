package bbs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.ThreadDiv;

public class Thr {
//	public static final int SALON = 200;
//	public static final int VERY_EASY = 100;
//	public static final int EASY = 101;
//	public static final int NORMAL = 102;
//	public static final int EXTRA = 103;
//	public static final int HARD = 104;
//	public static final int LUNATIC = 105;
//	public static final int PHANTASM = 106;

	private String name;
	private String url;
	private ThreadDiv rank;

	public Thr(String name, String url, ThreadDiv rank) {
		Pattern pattern = Pattern.compile("\\([0-9]{1,3}\\)");
		Matcher matcher = pattern.matcher(name);
		if (matcher.find()) {
			name = matcher.replaceAll("");
		}
		this.setName(name);
		this.setUrl(url);
		this.setRank(rank);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setRank(ThreadDiv rank) {
		this.rank = rank;
	}

	public ThreadDiv getRank() {
		return rank;
	}
}
