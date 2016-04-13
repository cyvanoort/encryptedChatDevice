package nl.hr.EncryptedChat.device.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Publisher {
	
	private ConnectionFactory factory = new ConnectionFactory();
	private final String QUEUE_NAME = "input";
	
	public Publisher(String host, String username, String password)  throws Exception {
		factory.setHost(host);
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setPort(5671);
		factory.useSslProtocol();
	}
	
	public void sendMessage(String fromKey, String message, String toKey) throws Exception{
		Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    String sentMessage = toKey + ":" + message + ":" + fromKey;

	    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
	    channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, sentMessage.getBytes("UTF-8"));
	}
	
	public void sendMessage(String message, String fromKey) throws Exception{
		Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    String sentMessage = message + ":" + fromKey;

	    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
	    channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, sentMessage.getBytes("UTF-8"));
	}

}
