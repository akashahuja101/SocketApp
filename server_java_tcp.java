import java.net.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.io.*;
public class server_java_tcp extends Thread{

	private ServerSocket serverSocket;
	//Basic socket code referred from http://www.tutorialspoint.com/java/java_networking.htm
	   public server_java_tcp(int port) throws IOException
	   {
	      serverSocket = new ServerSocket(port);
	     // serverSocket.setSoTimeout(10000);
	   }

	   public void run()
	   {
	      while(true)
	      {
	         try
	         {
	            System.out.println("Waiting for client on port " +
	            serverSocket.getLocalPort() + "...");
	            Socket server = serverSocket.accept();
	            System.out.println("Just connected to "
	                  + server.getRemoteSocketAddress());
	            DataInputStream in =
	                  new DataInputStream(server.getInputStream());
	            System.out.println(in.readUTF());
	            String exp = in.readUTF();
	            System.out.println(exp);
	         // ScriptEngineManager referred from http://stackoverflow.com/questions/3422673/evaluating-a-math-expression-given-in-string-form
	            ScriptEngineManager sem = new ScriptEngineManager();
	            ScriptEngine engine = sem.getEngineByName("JavaScript");
	            
	            int result = (int) engine.eval(exp);
	            System.out.println(result);
	            DataOutputStream out =
	                 new DataOutputStream(server.getOutputStream());
	            out.writeUTF(Integer.toString(result));
	            for(int i=0;i<result;i++)
	            {
	            	out.writeUTF("Socket Programming");
	            }
	            out.writeUTF("Thank you for connecting to "
	              + server.getLocalSocketAddress() + "\nGoodbye!");
	            server.close();
	         }catch(SocketTimeoutException s)
	         {
	            System.out.println("Socket timed out!");
	            break;
	         }catch(IOException e)
	         {
	            e.printStackTrace();
	            break;
	         } catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	   }
	   public static void main(String [] args)
	   {
	      int port = Integer.parseInt(args[0]);
	      try
	      {
	         Thread t = new server_java_tcp(port);
	         t.start();
	      }catch(IOException e)
	      {
	         e.printStackTrace();
	      }
	   }
	}
