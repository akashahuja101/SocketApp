import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class server_java_udp {
//	Basic socket code referred from https://systembash.com/a-simple-java-udp-server-and-udp-client/
	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		byte[] sendData1 = new byte[1024];
		while (true) {
			try {
				System.out.println("Waiting for client...");
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				String sentence = new String(receivePacket.getData());
				int len = Integer.parseInt(sentence.trim());
				System.out.println("RECEIVED: " + len);
				serverSocket.setSoTimeout(500);// referred from www.stackoverflow.com
				String exp = null;
				String ack = "ACK";
				while (true) {

					try {
						serverSocket.receive(receivePacket);
						exp = new String(receivePacket.getData());
						System.out.println(exp.trim());

					} catch (SocketTimeoutException e) {
						System.out.println("Did not receive valid expression from client. Terminating.");
						serverSocket.close();
					}
					if (exp != null)
						break;
				}
				serverSocket.setSoTimeout(0);
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				if (exp.trim().length() == len) {
					sendData = ack.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					serverSocket.send(sendPacket);
				} else {
					System.out.println("Message is invalid");
				}
				// ScriptEngineManager referred from http://stackoverflow.com/questions/3422673/evaluating-a-math-expression-given-in-string-form
				ScriptEngineManager sem = new ScriptEngineManager();
				ScriptEngine engine = sem.getEngineByName("JavaScript");
				int result = (int) engine.eval(exp.trim());

				String size = Integer.toString(result);
				int res_size = size.length();
				String send_size = Integer.toString(res_size);
				sendData1 = send_size.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData1, sendData1.length, IPAddress, port);
				System.out.println(send_size);				
				serverSocket.send(sendPacket);
				
				sendData1 = size.getBytes();
				sendPacket = new DatagramPacket(sendData1, sendData1.length, IPAddress, port);
				serverSocket.send(sendPacket);
				
				serverSocket.setSoTimeout(1000);
				while(true)
				{
					serverSocket.receive(receivePacket);
					exp = new String(receivePacket.getData());
					System.out.println("From Client:" + exp.trim());
					if (exp != null)
						break;
				}
				serverSocket.setSoTimeout(0);
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

}
