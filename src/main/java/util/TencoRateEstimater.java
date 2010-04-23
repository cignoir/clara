package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TencoRateEstimater {
	static URL url;
	static HttpURLConnection con;

	public static String getRate(String id) throws IOException {
		String result = null;
		try {
			url = new URL(
					"http://tenco.info/estimate_rating.cgi?game_id=2&name="
							+ URLEncoder.encode(id, "UTF-8"));
			con = (HttpURLConnection) url.openConnection();
			con.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(con
					.getInputStream(), "UTF-8"));

			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.contains("<pre>")) {
					while (!(line = br.readLine()).contains("---------------")) {
						result += line + "\n";
					}
					break;
				}
			}
			con.disconnect();

			if (result != null && result.startsWith("null")) {
				result = result.replace("null", "");
			}
		} catch (Exception e) {
			result = null;
		}

		return result;
	}
}
