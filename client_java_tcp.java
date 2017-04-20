import java.net.*;
import java.util.Scanner;
import java.io.*;
public class client_java_tcp {

	public static void main(String [] args)
	   {
	      //String serverName = args[0];
	      //int port = Integer.parseInt(args[1]);
			String serverName=null;
			String expression=null;
			int port=0;
	      try
	      {
	         
	         Scanner sc = new Scanner(System.in);
	         System.out.println("Enter server name");
	         serverName = sc.nextLine();
	         while(true)
	         {
	         System.out.println("Enter port number");
	         port = Integer.parseInt(sc.nextLine());
	         if(port<0 || port>65535)
	         {
	        	 System.out.println("Invalid port number. Terminating");
	        	 
	         }
	         else
	         {
	        	 break;
	         }
	         }
	         System.out.println("Enter expression");
	         expression = sc.nextLine();
	         System.out.println("Connecting to " + serverName +
	    			 " on port " + port);
	         //Basic socket code referred from http://www.tutorialspoint.com/java/java_networking.htm
	         Socket client = new Socket(serverName, port);
	         System.out.println("Just connected to " 
			 + client.getRemoteSocketAddress());
	         OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out = new DataOutputStream(outToServer);
	         out.writeUTF("Hello from "
	                      + client.getLocalSocketAddress());
	         out.writeUTF(expression);
	         InputStream inFromServer = client.getInputStream();
	         DataInputStream in =
	                        new DataInputStream(inFromServer);
	         int res = Integer.parseInt(in.readUTF());
	         System.out.println(res);
	         for(int i=0;i<res;i++)
	         {
	        	 System.out.println(in.readUTF());
	         }
	         System.out.println("Server says " + in.readUTF());
	         client.close();
	      }catch(IOException e)
	      {
	         e.printStackTrace();
	      }
	   }
}
