package de.tub.ise.ec;

import java.io.FileWriter;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


import de.tub.ise.ec.kv.FileSystemKVStore;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.*;


public class MasterNodeMessageHandler implements IRequestHandler{
	
	//KeyValueInterface store = new FileSystemKVStore("C:/Users/DELL PC/Desktop");
	
	@Override
	public Response handleRequest(Request req) {
		
		List<Serializable> list = req.getItems();
		CrudRequest request = (CrudRequest )list.get(0);
		String requestType=request.getRequestType();
		CrudMethods obj = new CrudMethods();
		
		
		
		switch (requestType) {
		case "CREATE":
				obj.create(request.getKey(),request.getValue(),request.getMessageId());
				
				//Slave address
				int port = 8080;
				String host = "52.59.205.51"; // localhost

			Request reqSlave = new Request(request, "slavenodemessagehandler","first_entry");
			// Master: send messages to Slave
			Sender sender = new Sender(host, port);

			if(request.isAsync()){
				sender.sendMessageAsync(reqSlave, new AsyncCallbackRecipient() {
					@Override
					public void callback(Response response) {

					}

				});

				// Return a dummy response, we don't handle async callbacks now
				return new Response("Success", true, req, new ArrayList<Request>());

			}
			else{
				Response res = sender.sendMessage(reqSlave, 5000);
			}


				
			break;
		case "READ":
			obj.read(request.getKey());
			break;
			
		case "UPDATE":
			obj.update(request.getKey(), request.getValue(), request.getMessageId());

			//Slave address
			int port1 = 8080;
			String host1 = "52.59.205.51"; // slaveNode


			Request reqSlave1 = new Request(request, "slavenodemessagehandler","first_entry");
			// Master: send messages to Slave
			Sender sender1 = new Sender(host1, port1);
			if(request.isAsync()){
				sender1.sendMessageAsync(reqSlave1, new AsyncCallbackRecipient() {
					@Override
					public void callback(Response response) {

					}


				});

				// Return a dummy response, we don't handle async callbacks now
				return new Response("Success", true, req, new ArrayList<Request>());

			}
			else{
				Response res = sender1.sendMessage(reqSlave1, 5000);
			}
			break;
			
		case "DELETE":
			obj.delete(request.getKey());
			break;

		default:
			break;
		}
		
		return new Response("Echo okay for target: " + req.getTarget(), true, req, req.getItems());
	}

	@Override
	public boolean requiresResponse() {
		// TODO Auto-generated method stub
		return true;
	}

}
