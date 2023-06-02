package org.example.netty;

import org.junit.Test;

public class WsClientTest {

    @Test
    public void testSout() {
        System.out.print("hello world");
    }

    @Test
    public void testWsClient() {
        WsClient wsClient = new WsClient();
        try {
            wsClient.dial(Constants.BloodServerWsPort);
            System.out.println(wsClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
