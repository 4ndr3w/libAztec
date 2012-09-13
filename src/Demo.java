import java.io.IOException;

import lobos.andrew.aztec.Aztec;
import lobos.andrew.aztec.EasyRequestHandler;
import lobos.andrew.aztec.HTTPRequest;
import lobos.andrew.aztec.HTTPResponse;
import lobos.andrew.aztec.RequestHandler;


public class Demo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		EasyRequestHandler reqHandler = new EasyRequestHandler();
		reqHandler.registerPage("/test", new RequestHandler()
		{

			@Override
			public HTTPResponse handleHTTPRequest(HTTPRequest req) {
				return new HTTPResponse(200, "Test");
			}
			
		});
		
		reqHandler.registerPage("/test2", new RequestHandler()
		{

			@Override
			public HTTPResponse handleHTTPRequest(HTTPRequest req) {
				return new HTTPResponse(200, "Test 2 - Data: "+req.getParam("t"));
			}
			
		});
		
		
		new Aztec(8080, reqHandler);
	}

}
