package server.data;

import java.net.DatagramSocket;
import java.net.InetAddress;
import server.data.*;

public class Packet {

	private byte[] data;
	private InetAddress ip;
	private int port;
	private data.Connection conn;
	
	/**
	 * Create a new packet
	 * @param data The data to send
	 * @param receiver The packets' reciever
	 */
	public Packet(byte[] data, data.Connection receiver) {
		this.data = data;
		this.conn = receiver;
	}
	
	/**
	 * Create a new packet with simple information about the client
	 * @param data
	 * @param ip
	 * @param port
	 */
	public Packet(byte[] data, InetAddress ip, int port) {
		this.data = data;
		this.ip = ip;
		this.port = port;
		
		this.conn = new data.Connection(null, ip, port, 10);
	}
	
	/**
	 * Get the packet data
	 * @return the packet data
	 */
	public byte[] getData() {
		return data;
	}
	
	/**
	 * Get the ip
	 * @return the address of the receiver
	 */
	public InetAddress getAddr() {
		return ip;
	}
	
	/**
	 * Get the port
	 * @return the receivers port
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * Get the connection on which this packet was sent
	 * @return the connection
	 */
	public data.Connection getConnection() {
		return this.conn;
	}
	
	@Override
	public String toString() {
		return "Data: " + new String(this.data) + "\n From: " + getConnection().getAddress() + ":" + getConnection().getPort();
	}

}
