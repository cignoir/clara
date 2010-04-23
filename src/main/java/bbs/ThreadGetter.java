package bbs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import enums.ThreadDiv;

public class ThreadGetter {
	public static final int GAME = 48698;
	public static final int NETGAME = 3867;
	
	public static List<Thr> getThreadList(Integer boardNum) throws IOException {
		String urlStr = "";
		if(boardNum.equals(GAME)) {
			urlStr = "http://jbbs.livedoor.jp/bbs/subject.cgi/game/48698/";
		} else if(boardNum.equals(NETGAME)) {
			urlStr = "http://jbbs.livedoor.jp/bbs/subject.cgi/netgame/3867/";
		} else {
			return null;
		}
		URL url = new URL(urlStr);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.connect();

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "euc-jp"));
		String line = "";
		List<Thr> list = new ArrayList<Thr>();
		while ((line = br.readLine()) != null) {
			if (line.contains("l50\">") && line.contains("(1000)") == false) {
				String th = line.substring(line.indexOf("l50\">") + "l50\">".length(), line.indexOf("</a><br>"));
				int start = line.indexOf(boardNum.toString()) + boardNum.toString().length() + 1;
				String thUrl = line.substring(start, start + 10);
				String boardUrl = null;
				ThreadDiv rank = null;
				if(boardNum.equals(GAME)) {
					boardUrl = "http://jbbs.livedoor.jp/bbs/read.cgi/game/48698/";
					rank = ThreadDiv.SALON;
				} else if(boardNum.equals(NETGAME)) {
					rank = ThreadDiv.VERY_EASY;
					boardUrl = "http://jbbs.livedoor.jp/bbs/read.cgi/netgame/3867/";
				}
				list.add(new Thr(th, boardUrl + thUrl, rank));
			}
		}

		if (br != null) {
			br.close();
		}
		if (con != null) {
			con.disconnect();
		}
		
		return list;
	}
}
