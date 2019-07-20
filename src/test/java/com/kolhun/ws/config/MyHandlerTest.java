package com.kolhun.ws.config;

import com.kolhun.ws.ApplicationRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationRunner.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class MyHandlerTest {

  @LocalServerPort
  int port;

  @Test
  public void handleTextMessage() throws ExecutionException, InterruptedException, IOException {
    WebSocketClient client = new StandardWebSocketClient();
    WebSocketSession session = client.doHandshake(new MyWebSocketHandler(), "ws://localhost:" + port + "/myHandler").get();
    session.sendMessage(new TextMessage("hello from test"));
    Thread.sleep(5000);
    //assert logs
    session.close();
  }

  static class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
      System.out.println("connection established");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
      System.out.println(message.getPayload());
      //session.sendMessage(new TextMessage("client response"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
      System.out.println("ws client session closed");
    }

  }
}