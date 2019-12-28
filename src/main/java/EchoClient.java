import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class EchoClient {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public static void main(String[] args) {
        EchoClient ec = new EchoClient();
        while (true) {
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            ec.sendEcho(s);
        }
    }

    public EchoClient() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String sendEcho(String msg) {
        DatagramPacket packet = null;
        try {
            buf = msg.getBytes();
            packet = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close() {
        socket.close();
    }
}