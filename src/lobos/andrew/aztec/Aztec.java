package lobos.andrew.aztec;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Aztec extends Thread
{
	ServerSocket listener;
	RequestHandler reqHandler = null;
	public Aztec(int port) throws IOException
	{
		listener = new ServerSocket(port);
		start();
	}
	
	public Aztec(int port, RequestHandler handler) throws IOException
	{
		this(port);
		reqHandler = handler;
	}
	
	public void setRequestHandler(RequestHandler handler)
	{
		reqHandler = handler;
	}
	
	public void close() throws IOException
	{
		listener.close();
	}
	
	public void run()
	{
		while ( true )
		{
			try {
				final Socket thisSock = listener.accept();
				new Thread()
				{
					public void run()
					{
						try
						{
							HTTPRequest req = HTTPRequest.fromSocket(thisSock);
							if ( reqHandler == null )
								new HTTPResponse(500, "No handler registered.");
							else
								reqHandler.handleHTTPRequest(req).send(thisSock);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
						
					}
				}.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
