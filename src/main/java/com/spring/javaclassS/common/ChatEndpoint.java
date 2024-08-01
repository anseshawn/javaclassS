package com.spring.javaclassS.common;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

// 사용자 목록을 관리하고, 새로운 사용자가 접속하거나 연결이 끊어질 때마다 목록을 갱신하여 클라이언트에 브로드캐스트합니다. 클라이언트는 이 목록을 받아 사용자 인터페이스에 표시함
@ServerEndpoint("/webSocket/endPoint/{username}")
public class ChatEndpoint {
	private static Map<String, Session> clients = Collections.synchronizedMap(new HashMap<>());

	// 처음 접속시에 수행한다.
  @OnOpen
  public void onOpen(Session session, @PathParam("username") String username) {
      clients.put(username, session);
      broadcastUserList();	// 새롭 접속할때 접속자의 세션을 유저리스트에 담는다.
  }

  // 접속 종료시에 수행한다.
  @OnClose
  public void onClose(Session session, @PathParam("username") String username) {
      clients.remove(username);
      broadcastUserList();	// 접속종료할때 접속자의 세션을 유저리스트에서 제거한다.
  }

  // 새로운 메세지가 전송되어 왔을때 수행한다.
  @OnMessage
  public void onMessage(String message, @PathParam("username") String username) throws IOException {
    String[] parts = message.split(":", 2);
    if (parts.length == 2) {
      String targetUser = parts[0];
      String msg = parts[1];

      // 메세지를 보낸 접속자의 유저를 세션에 저장한다.
      Session targetSession = clients.get(targetUser);
      if (targetSession != null) {		// 접속자가 있으면 접속자와 접속자의 메세지를 전송시켜준다.
        targetSession.getBasicRemote().sendText(username + ": " + msg);
      }
    }
  }
  
  // 접속한 유저리스트 모두 담기(접속한 세션의 값으로 현재 유저리스트를 구해오고 있다.)
  private void broadcastUserList() {
    String userList = clients.keySet().stream().collect(Collectors.joining(","));
    for (Session session : clients.values()) {
      try {
        session.getBasicRemote().sendText("USER_LIST:" + userList);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
