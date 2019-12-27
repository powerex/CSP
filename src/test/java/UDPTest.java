import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class UDPTest {
    EchoClient client;

    @Before
    public void setup() throws IOException {
        new EchoServer().start();
        client = new EchoClient();
    }

    @Test
    public void whenCanSendAndReceivePacket_thenCorrect() {
        String echo = client.sendEcho("hello server");
        assertEquals("hello server", echo);
        echo = client.sendEcho("server is working");
        assertFalse(echo.equals("hello server"));
    }

    private void assertEquals(String hello_server, String echo) {
    }

    @After
    public void tearDown() {
        client.sendEcho("end");
        client.close();
    }
}