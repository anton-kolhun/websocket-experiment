package com.kolhun.ws.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        System.out.println("ws open");

        for (int i = 0; i < 5; i++) {
            Thread.sleep(500);
            session.sendMessage(new TextMessage(String.valueOf(i)));
        }
        System.out.println(session.getAttributes());
        System.out.println(session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message.getPayload());
        session.sendMessage(new TextMessage("response"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        System.out.println("ws closed");

    }
}
