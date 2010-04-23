package bbs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreadReader {
	private static final String COL_LIMIT = "【希望対戦回数】";
	private static final String COL_CHARACTER = "【使用キャラ】";
	private static final String COL_COMMENT = "【その他コメント】";

	private URL url;
	private Res[] resArray;
	public int lastReadNum;

	/**
	 * Constructor
	 * 
	 */
	public ThreadReader(URL url) {
		this.url = url;
		resArray = new Res[1024];
		try {
			updateRes();
		} catch (IOException e) {
		} catch (NumberFormatException e) {
		} catch (IllegalArgumentException e) {

		}
	}

	// public static void main(String[] args) {
	// try {
	// new ThreadReader(new
	// URL("http://jbbs.livedoor.jp/bbs/read.cgi/netgame/3867/1260364436/"));
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// }
	// }

	public void updateRes() throws IOException, NumberFormatException,
			IllegalArgumentException {
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.connect();

		BufferedReader br = new BufferedReader(new InputStreamReader(con
				.getInputStream(), "euc-jp"));
		String line = "";
		while ((line = br.readLine()) != null) {
			if (line.startsWith("<dt")) {
				int resNum = Integer.parseInt(trim("\">", "</a> ：", line));
				if (resNum <= lastReadNum) {
					continue;
				}
				String name = trim("<b>", "</b>", line);
				String time = trim("：2", " ID:", line);
				String id = trim("ID:", "<dd>", line);
				String text = line.substring(line.indexOf("<dd> ")
						+ "<dd> ".length());

				String ip = getIPAddress(text);
				boolean isRecruitment = ip == null ? false : true;
				int parent = getParent(text);
				String frequency = getDetail(text, COL_LIMIT);
				String character = getDetail(text, COL_CHARACTER);
				String comment = getDetail(text, COL_COMMENT);
				resArray[resNum] = new Res(resNum, name, time, id, text,
						isRecruitment, false, ip, frequency, character,
						comment, parent);

				if (ip != null) {
					resArray[resNum].isOpened = true;
				}

				if (parent != -1 && isClosing(text) && resArray[parent] != null) {
					resArray[parent].isOpened = false;
				} else if (parent != -1 && isReRecruitement(text)
						&& resArray[parent] != null) {
					resArray[parent].isOpened = true;
				}

				this.lastReadNum = resNum;
			} else if (line.contains("そんな板orスレッドないです。")) {
				resArray = null;
				break;
			}
		}

		if (br != null) {
			br.close();
		}
		if (con != null) {
			con.disconnect();
		}

	}

	private String getDetail(String text, String condition) {
		String[] ary = text.split("<br>");
		for (String str : ary) {
			if (str.startsWith(condition)) {
				return str.substring(condition.length());
			}
		}
		return null;
	}

	private String getIPAddress(String text) {
		Pattern pattern = Pattern
				.compile("([0-9]{1,3}\\.){3}[0-9]{1,3}:[0-9]+");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			return matcher.group();
		} else {
			return null;
		}
	}

	public static String removeHtmlTag(String text) {
		Pattern pattern = Pattern.compile("<.+?>");
		Matcher matcher = pattern.matcher(text);
		return matcher.replaceAll("");
	}

	private boolean isReRecruitement(String text) {
		return text.contains("さいぼ") || text.contains("再募集")
				|| text.contains("再募") || text.contains("サイボ")
				|| text.contains("募集中") || text.contains("ラスト")
				|| text.contains("らすと") || text.contains("大募集")
				|| text.contains("saibo") || text.contains("歳募集");
	}

	private boolean isClosing(String text) {
		return text.contains("〆") || text.contains("締め") || text.contains("しめ")
				|| text.contains("閉め") || text.contains("休憩");
	}

	private int getParent(String text) {
		if (isReRecruitement(text) || isClosing(text)) {
			String[] ary = text.split("&gt;&gt;");
			if (ary.length > 2) {
				text = text.substring(text.indexOf(ary[ary.length - 2])
						+ ary[ary.length - 2].length());
			}
			Pattern pattern = Pattern.compile("&gt;&gt;[0-9]{1,3}</a>");
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				String extracted = matcher.group();
				return Integer.parseInt(extracted.substring(
						"&gt;&gt;".length(), extracted.length()
								- "</a>".length()));
			}
		}
		return -1;
	}

	private String trim(String open, String close, String content) {
		int start = content.indexOf(open);
		int end = content.indexOf(close);
		if (start + end != -2 && start < end) {
			return content.substring(start + open.length(), end);
		} else {
			return null;
		}
	}

	public Res[] getResArray() {
		return this.resArray;
	}
}
