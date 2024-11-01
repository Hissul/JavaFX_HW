package JavaFXTest.First;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {
	
	private static String host = "localhost";
	private static int port = 9463;
	
	private static Client server;
	private static ServerSocket listener;
	
	private static ObjectInputStream inputStream;
	private static ObjectOutputStream outputStream;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		try {
			listener = new ServerSocket(port);			
			server = new Client();	
			System.out.println("listener started");
		} 
		catch (IOException e) {			
			e.printStackTrace();
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		listener.close();
		System.out.println("listener closed");
	}

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	

	@Test
	void testConnect() {
		
		Boolean testConnect = server.connect();	
		
		try {		
			Socket client = listener.accept();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
		
		assert(testConnect);	
		assertTrue(server.socket.isConnected());	
	}

	
	@Test
	void testDisconnect() {
		
		server.connect();	
		
		try {
			Socket client = listener.accept();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		Boolean testDisconnect = server.disconnect();	
		assert(testDisconnect);
	}

	
	@Test
	void testSandMessage() {
		server.connect();
		
		try {
			server.outputStream = new ObjectOutputStream(server.socket.getOutputStream());
			
			Socket client = listener.accept();
			
			ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
			
			assert(server.sandMessage("test"));
		} 
		catch (IOException e) {			
			e.printStackTrace();
		}
		
	}


	@Test
	void testListenServer() {
		
		server.connect();
		
		try {
			Socket client = listener.accept();
			
			ObjectOutputStream outServer = new ObjectOutputStream(client.getOutputStream());
			outServer.writeObject("test");			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		server.listenServer(null);
		
		// пришлось поставить задержку т.к. метод server.listenServer выполняется на другом потоке
		// без задержки assert наступает раньше чем инициализируются inputStream и outputStream
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		
		assertNotNull(server.inputStream);
		assertNotNull(server.outputStream);
	
	}
	

}
