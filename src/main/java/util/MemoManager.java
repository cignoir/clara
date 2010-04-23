package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MemoManager {
	private static final String fileName = "memo.txt"; 
	String message;
	StringBuffer strBuff;
	
	public MemoManager() {
	}
	
	public MemoManager(String message) {
		this.message = message;
	}
	
	public String load() {
		try {
			strBuff = new StringBuffer();
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = "";
			while((line = reader.readLine()) != null) {
				strBuff.append(line + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strBuff.toString();
	}
	
	public void save() {
		BufferedWriter writer = null;
		String[] ary = message.split("\n");
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			for(String str : ary) {
				writer.write(str);
				writer.newLine();
			}
			
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
	
}
