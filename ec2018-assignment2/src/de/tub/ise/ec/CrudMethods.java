package de.tub.ise.ec;

import java.io.FileWriter;
import java.io.IOException;

import de.tub.ise.ec.kv.FileSystemKVStore;

public class CrudMethods {
	
	FileSystemKVStore obj;
	public CrudMethods() {
		obj= new FileSystemKVStore();
	}
	
	public void create(String key, String value, String messageId) {
		obj.store(key, value);
		
		//write commit log to the file.
		String time = System.currentTimeMillis() + "";
		writeToFile(messageId, time);
	}
	
	public void read(String key) {
		obj.getValue(key);
	}
	
	public void update(String key, String value, String messageId) {
		if(obj.getKeys().contains(key)) {
			obj.store(key, value);
			//write commit log to the file.
			String time = System.currentTimeMillis() + "";
			writeToFile(messageId, time);
		}
	}
	
	public void delete(String key) {
		obj.delete(key);
		
	}
	
	public void writeToFile(String messageId, String time) {
		try {
			System.out.println("inside");
			FileWriter pw = new FileWriter("/home/ec2-user/commitLogs.csv", true);
			pw.append(messageId);
			pw.append(",");
			pw.append(time);
			pw.append("\n");
			pw.flush();
	        pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
