package com.example.taoxiao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.taoxiao.mapper.messageMapper;
import com.example.taoxiao.mapper.userMapper;

import org.springframework.web.socket.server.standard.SpringConfigurator;


@ServerEndpoint(value = "/ws/asset")
@Component
public class WebSocketServer {
	private static ApplicationContext applicationContext;
	private messageMapper tmessage;
	public static void setApplicationContext(ApplicationContext applicationContext) {
		WebSocketServer.applicationContext = applicationContext;
	}
	private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
	private static final AtomicInteger OnlineCount = new AtomicInteger(0);
	private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();
	//存session和account
	private static Map <String,Session> online= new Hashtable<String,Session>();
	
	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	//public void onOpen(Session session,String account) {
	public void onOpen(Session session) {
		
		String account=null;
		SessionSet.add(session); 
		int cnt = OnlineCount.incrementAndGet(); // 在线数加1
		log.info("有连接加入，当前连接数为：{}", cnt);
		log.info("session：{}", session);
		//online.put(account, session);
		SendMessage(session, "连接成功");
		//if(user.setSession(session))
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(Session session) {
		String account=null;
		int t=0;
		for (Map.Entry<String, Session> entry : online.entrySet()) { 
			if(session==entry.getValue()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
				online.remove(entry.getKey());
			}
		}
		SessionSet.remove(session);
		
		int cnt = OnlineCount.decrementAndGet();	
		log.info("有连接关闭，当前连接数为：{}", cnt);
		log.info("用户{}下线", account);
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @throws ParseException 
	 */
	
	@OnMessage
	public void onMessage(String message, Session session){
		log.info("来自客户端的消息：{}",message);
		//System.out.println(message);
		JSONObject jsonObject = JSON.parseObject(message);
		//System.out.println(jsonObject);
		
		tmessage = applicationContext.getBean(messageMapper.class);
		
		String function=jsonObject.getString("function");
		if(function.equals("online"))
		{
			String account=jsonObject.getString("account");
			online.put(account, session);
			System.out.println("用户"+account+"上线");
			//有离线的消息
			if(tmessage.select3(account,0)!=null) {
				List<Message> unmessage = tmessage.select3(account,0);
				for(int i=0; i<unmessage.size(); i++) {
					SendMessage(session, unmessage.get(i).toString());
				}
				tmessage.update(account, 1);
			}
		}
		else 
		{
			String sendaccount=jsonObject.getString("sendaccount");
			String receiveaccount=jsonObject.getString("receiveaccount");
			String type=jsonObject.getString("type");
			String amessage=jsonObject.getString("message");
			String str =jsonObject.getString("mtime");
			Date mtime = new Date();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				mtime = sdf.parse(str);
			}catch(ParseException e){
				e.printStackTrace();
			}
			
			System.out.println(receiveaccount);
			System.out.println(mtime);
			if(function.equals("chat"))
			{
				
				//对方在线
				if(online.containsKey(receiveaccount))
				{
					Session resession=online.get(receiveaccount);
					SendMessage(resession, "收到消息，消息内容："+message);
					tmessage.insert(function, sendaccount, receiveaccount, type, amessage, mtime, 1);
					System.out.println("在线");
				}
				//对方离线
				else
				{
					tmessage.insert(function, sendaccount, receiveaccount, type, amessage, mtime, 0);
				}
			}
			else if(function.equals("tosystem"))
			{
				tmessage.insert(function, sendaccount, receiveaccount, type, amessage, mtime, 1);
			}
		}
	}

	/**
	 * 出现错误
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误：{}，Session ID： {}",error.getMessage(),session.getId());
		error.printStackTrace();
	}

	/**
	 * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
	 * @param session
	 * @param message
	 */
	public static void SendMessage(Session session, String message) {
		try {
			session.getBasicRemote().sendText(String.format("%s (From Server，Session ID=%s)",message,session.getId()));
		} catch (IOException e) {
			log.error("发送消息出错：{}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 群发消息
	 * @param message
	 * @throws IOException
	 */
	public static void BroadCastInfo(String message) throws IOException {
		for (Session session : SessionSet) {
			if(session.isOpen()){
				SendMessage(session, message);
			}
		}
	}

	/**
	 * 指定Session发送消息
	 * @param sessionId
	 * @param message
	 * @throws IOException
	 */
	public static void SendMessage(String sessionId,String message) throws IOException {
		Session session = null;
		for (Session s : SessionSet) {
			if(s.getId().equals(sessionId)){
				session = s;
				break;
			}
		}
		if(session!=null){
			SendMessage(session, message);
		}
		else{
			log.warn("没有找到你指定ID的会话：{}",sessionId);
		}
	}
}

