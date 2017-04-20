import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class client_java_udp {
	//	Basic socket code referred from https://systembash.com/a-simple-java-udp-server-and-udp-client/
	public static void main(String args[]) throws Exception {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData = new byte[1024];
		byte[] sendData1 = new byte[1024];
		byte[] receiveData = new byte[1024];
		System.out.println("Enter Expression:");
		String sentence = inFromUser.readLine();
		String length = Integer.toString(sentence.length());
		sendData1 = length.getBytes();
		sendData = sentence.getBytes();
		DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, IPAddress, 9876);
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		//for (int i = 0; i < 3; i++) {
			//System.out.println("Sending Data");
			clientSocket.send(sendPacket1);
			
			clientSocket.send(sendPacket);
			clientSocket.setSoTimeout(1000);
			int flag = 0;
			while (true) {
				String rec = null;
				try {
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					clientSocket.receive(receivePacket);
					rec = new String(receivePacket.getData());
					System.out.println("FROM SERVER:" + rec.trim());
				} catch (SocketTimeoutException e) {
					flag = 1;
					break;
				}
				if (rec != null) {
					break;
				}

			}
			clientSocket.setSoTimeout(0);
			receiveData = new byte[1024];
			DatagramPacket receivePacket1 = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket1);
			String recv = new String(receivePacket1.getData());
			int len = Integer.parseInt(recv.trim());
			System.out.println("FROM SERVER:" + recv.trim());
			
			receiveData = new byte[1024];
			receivePacket1 = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket1);
			recv = new String(receivePacket1.getData());
			System.out.println("The result is:" + recv.trim());
			
			if(recv.trim().length() == len)
			{
				String ack = "ACK";
				sendData = ack.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
				clientSocket.send(sendPacket);
			}
			clientSocket.close();
			//clientSocket.wait(1000);
		//}
	}

}
