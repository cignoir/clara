package bbs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ThreadPosting {
	private static final String hostUrl = "http://jbbs.livedoor.jp";
	private static final String formTagS = "<form method=\"POST\" action=\"";
	private static final String formTagE = "\" name=\"fcs\"> ";

	public ThreadPosting(String threadUrl, String name, String mail, String message) {
		try {
			URL url = new URL(threadUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "euc-jp"));
			String line = "";
			String postUrl = "";
			Map<String, String> map = new HashMap<String, String>();
			while ((line = br.readLine()) != null) {
				if (line.startsWith(formTagS)) {
					postUrl = hostUrl + line.substring(formTagS.length(), line.length() - formTagE.length());
					map.put("URL", postUrl);

					line = br.readLine();

					if (line.contains("BBS")) {
						map.put("BBS", line.split("\"")[5]);
					}

					line = br.readLine();

					if (line.contains("KEY")) {
						map.put("KEY", line.split("\"")[5]);
					}

					line = br.readLine();

					if (line.contains("DIR")) {
						map.put("DIR", line.split("\"")[5]);
					}

					line = br.readLine();

					if (line.contains("TIME")) {
						map.put("TIME", line.split("\"")[5]);
					}

					map.put("NAME", name);
					map.put("MAIL", mail);
					map.put("MESSAGE", message);
				}
			}
			br.close();
			con.disconnect();

			url = new URL(map.get("URL"));
			String msg = "NAME=" + map.get("NAME") + "&MAIL=" + map.get("MAIL") + "&MESSAGE=" + map.get("MESSAGE")
					+ "&BBS=" + map.get("BBS") + "&KEY=" + map.get("KEY") + "&DIR=" + map.get("DIR") + "&TIME="
					+ map.get("TIME");

			Socket sock = new Socket(url.getHost(), 80);
			Writer out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			out.write("POST " + url.getFile() + " HTTP/1.0\n");
			out.write("Content-type: application/x-www-form-urlencoded\n");
			out.write("Content-length: " + msg.getBytes().length + "\n\n");
			out.write(msg + "\n");
			out.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String str = null;
			while ((str = reader.readLine()) != null)
				System.out.println(str);
			reader.close();

			out.close();
			sock.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
