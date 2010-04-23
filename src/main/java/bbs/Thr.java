package bbs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.ThreadDiv;

public class Thr {
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
