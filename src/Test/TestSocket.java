package Test;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestSocket {
	public static void main(String[] args) {
		try {
			Socket instance = new Socket("127.0.0.1",9654);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
