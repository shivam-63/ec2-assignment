package de.tub.ise.ec;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import de.tub.ise.hermes.IRequestHandler;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

public class SlaveNodeMessageHandler implements IRequestHandler {

	@Override
	public Response handleRequest(Request req) {
		
		List<Serializable> list = req.getItems();
		CrudRequest request=(CrudRequest )list.get(0);
		String requestType=request.getRequestType();
		CrudMethods obj = new CrudMethods();
		
		switch (requestType) {
		case "CREATE":
				obj.create(request.getKey(),request.getValue(),request.getMessageId());
				
			break;
		case "READ":
			obj.read(request.getKey());
			break;
			
		case "UPDATE":
			obj.update(request.getKey(), request.getValue(),request.getMessageId());
			break;
			
		case "DELETE":
			obj.delete(request.getKey());
			break;

		default:
			break;
		}
		
		return new Response("Echo okay for slave: " + req.getTarget(), true, req, req.getItems());
	}

	@Override
	public boolean requiresResponse() {
		// TODO Auto-generated method stub
		return true;
	}

}
