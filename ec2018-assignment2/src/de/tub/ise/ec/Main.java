package de.tub.ise.ec;

import de.tub.ise.ec.kv.FileSystemKVStore;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.*;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * @author DELL PC
 *
 */
public class Main {
	
	public static void main(String[] args) {
		
		Properties config;
		FileInputStream fis;
		config= new Properties();
		try {
			fis= new FileInputStream("config.properties");
			config.load(fis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(config.getProperty("master").equals("true")) {
			//MasterNOde
			int port = 8080;

			
			// Server: register handler
			RequestHandlerRegistry reg = RequestHandlerRegistry.getInstance();
			reg.registerHandler("masternodemessagehandler", new MasterNodeMessageHandler());
			
			// Server: start receiver
			try {
				Receiver receiver = new Receiver(port);
				receiver.start();
			} catch (IOException e) {
				System.out.println("Connection error: " + e);
			}

		}
		
		if(config.getProperty("slave").equals("true")) {
			
			int port = 8080;
			
			// Server: register handler
			RequestHandlerRegistry reg = RequestHandlerRegistry.getInstance();
			reg.registerHandler("slavenodemessagehandler", new SlaveNodeMessageHandler());
			
			// Server: start receiver
			try {
				Receiver receiver = new Receiver(port);
				receiver.start();
			} catch (IOException e) {
				System.out.println("Connection error: " + e);
			}
		}
		
		if(config.getProperty("client").equals("true")) {
			// HERMES TEST
			int port = 8080;
			String host = "18.185.126.39"; // masterNode
						
			//Writing a new key value pair
			for(int i=1;i<=50;i++) {
			
			CrudRequest request = new CrudRequest();
			request.setKey(i+"");
			request.setValue("hello "+i);
			request.setRequestType("CREATE");
			if(config.getProperty("isSynchronous").equals("true")) {
				request.setAsync(true);
			}
			else{
				request.setAsync(false);
			}
			request.setMessageId("KEY-" + request.getKey() + "-" +request.getRequestType());
			
			// Client: create request
			Request req2 = new Request(request, "masternodemessagehandler","value");
			
			// Client: send messages
			Sender sender = new Sender(host, port);
			Long start_time = System.currentTimeMillis();
			Response res = sender.sendMessage(req2, 5000);
			Long elapse = System.currentTimeMillis() - start_time;
			
			//Writing to file
			try {
				FileWriter pw = new FileWriter("/home/shivamsaini/Documents/elapsedtime.csv", true);
				pw.append(request.getMessageId());
				pw.append(",");
				pw.append(start_time+"");
				pw.append(",");
				pw.append(elapse+"");
				pw.append("\n");
				pw.flush();
		        pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block`2
				e.printStackTrace();
			}
			
			System.out.println("Elapse " + elapse);
			System.out.println("Received: " + res.getResponseMessage());
			}
//			 // Test kv store
//			 KeyValueInterface store = new FileSystemKVStore("C:/Users/DELL PC/Desktop");
//			 store.store(request.getKey(),request.getValue());
//			 System.out.println("Received: " + store.getValue(request.getKey()));
//			 //store.delete("monkey");
			
		}
		 
	}
	
}