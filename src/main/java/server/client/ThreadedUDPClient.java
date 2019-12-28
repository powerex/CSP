package server.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import server.data.Packet;
import server.data.PacketHandler;

/**
 * A class for handling a multi-threaded instance of a UDP client
 * @author craig
 *
 */
public class ThreadedUDPClient implements Runnable {
	
	private data.Connection connection;
	private boolean running;
	
	private DatagramSocket socket;

	public ThreadedUDPClient(String addr, int port) {
		try {
			socket = new DatagramSocket();
			connection = new data.Connection(socket, InetAddress.getByName(addr), port, 0);
			this.init();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialise the client
	 */
	private void init() {
		Thread process = new Thread(this, "server_process");
		process.start();
	}
	
	/**
	 * Send some data
	 * @param data array of bytes
	 */
	public void send(final byte[] data) {
		Thread send = new Thread("Sending Thread") {
			public void run() {
				connection.send(data);
			}
		};
		
		send.start();
	}
	
	/**
	 * Receive data on the given server connection
	 */
	public void receive(final PacketHandler handler) {
		Thread receive = new Thread("receive_thread") {
			public void run() {
				while (running) {
					byte[] buffer = new byte[1024];
					DatagramPacket dgpacket = new DatagramPacket(buffer, buffer.length);

					try {
						socket.receive(dgpacket);
					} catch (IOException e) {
						e.printStackTrace();
					}

					handler.process(new Packet(dgpacket.getData(), dgpacket.getAddress(), dgpacket.getPort()));
				}
			}
		};
		
		receive.start();
	}

	@Override
	public void run() {
		running = true;
	}

}
