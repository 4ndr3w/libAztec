package lobos.andrew.aztec;

import java.util.HashMap;
import java.util.Iterator;

public class EasyRequestHandler implements RequestHandler {
	
	HashMap <String, RequestHandler> pageList = new HashMap<String, RequestHandler>(); 
	
	public void registerPage(String regex, RequestHandler handler)
	{
		pageList.put(regex, handler);
	}
	
	
	@Override
	public HTTPResponse handleHTTPRequest(HTTPRequest req) 
	{
		Iterator<String> it = pageList.keySet().iterator();
		while ( it.hasNext() )
		{
			String thisURL = it.next();
			if ( req.getPath().matches(thisURL) )
				return pageList.get(thisURL).handleHTTPRequest(req);
		}
		return ErrorFactory.internalServerError("No plugins where available to handle your request.");
	}
}
