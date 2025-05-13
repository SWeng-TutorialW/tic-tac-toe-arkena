package il.cshaifasweng.OCSFMediatorExample.server;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private static SimpleServer server;
    public static void main( String[] args ) throws IOException
    {
        server = new SimpleServer(3000);
        try {
            server.listen();
            System.out.println("Server listening on port 3000");
        }
        catch (Exception e) {
            System.out.println("Server could not listen on port 3000");
            e.printStackTrace();
        }
    }
}
