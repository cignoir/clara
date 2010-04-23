package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsManager {
	private static final String fileName = "settings.ini"; 
	String message;
	private int posx = 0;
	private int posy = 0;
	private String threadName = "";
	private String threadUrl = "";
	private int interval = 60000;
	private boolean autoUpdate = false;

	public SettingsManager() {
		
	}
	
	public SettingsManager(String message) {
		this.message = message;
	}
	
	public void load() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = "";
			while((line = reader.readLine()) != null) {
				if(line.startsWith("posx:")) {
					setPosx(Integer.parseInt(line.substring("posx:".length())));
				}
				if(line.startsWith("posy:")) {
					setPosy(Integer.parseInt(line.substring("posy:".length())));
				}
				if(line.startsWith("name:")) {
					setThreadName(line.substring("name:".length()));
				}
				if(line.startsWith("url:")) {
					setThreadUrl(line.substring("url:".length()));
				}
				if(line.startsWith("interval:")) {
					int num = Integer.parseInt(line.substring("interval:".length()));
					if(num != 30000 && num != 60000 && num != 180000 && num != 300000) {
						num = 60000;
					}
					setInterval(num);
				}
				if(line.startsWith("auto:")) {
					String str = line.substring("auto:".length());
					if(str.equals("on") || str.equals("ON") || str.equals("On")) {
						setAutoUpdate(true);
					} else {
						setAutoUpdate(false);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter("settings.ini"));
			writer.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosx() {
		return posx;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}

	public int getPosy() {
		return posy;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadUrl(String threadUrl) {
		this.threadUrl = threadUrl;
	}

	public String getThreadUrl() {
		return threadUrl;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getInterval() {
		return interval;
	}

	public void setAutoUpdate(boolean autoUpdate) {
		this.autoUpdate = autoUpdate;
	}

	public boolean isAutoUpdate() {
		return autoUpdate;
	}

}
