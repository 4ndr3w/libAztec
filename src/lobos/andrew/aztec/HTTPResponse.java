package lobos.andrew.aztec;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class HTTPResponse {

	Vector<String> response = new Vector<String>();
	String content;
	
	public static String stringForResponseCode(int code)
	{
		switch ( code )
		{
			case 200:
				return "OK";
			case 401:
				return "Unauthorized";
			case 403:
				return "Forbidden";
			case 404:
				return "Not Found";
			case 500:
				return "Internal Server Error";
			case 501:
				return "Not implemented";
			default:
				return "Error";
		}
	}
	
	public HTTPResponse(int code, String content)
	{
		response.add("HTTP/1.1 "+code+" "+stringForResponseCode(code)+"\n");
		addResponseHeader("Content-Length", String.valueOf(content.length()));
		addResponseHeader("Content-type", "text/html");
		addResponseHeader("Server", "Aztec");
		addResponseHeader("Connection", "close");
		this.content = content;
	}
	
	public HTTPResponse(int code, String strResponse, String content, String content_type)
	{
		response.add("HTTP/1.1 "+code+" "+strResponse+"\n");
		addResponseHeader("Content-Length", String.valueOf(content.length()));
		addResponseHeader("Content-type", content_type);
		addResponseHeader("Server", "Aztec");
		addResponseHeader("Connection", "close");
		this.content = content;
	}

	public void addResponseHeader(String name, String value)
	{
		response.add(name+": "+value+"\n");
	}
	
	public void send(Socket client) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		Iterator<String> it = response.iterator();
		
		while (it.hasNext())
			writer.write(it.next());
		
		writer.write("\n"+content);
		
		writer.flush();
	}
	
}
