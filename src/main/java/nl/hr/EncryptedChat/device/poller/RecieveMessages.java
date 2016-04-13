package nl.hr.EncryptedChat.device.poller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

public class RecieveMessages {
	
	private ConnectionFactory factory;
	private final String EXCHANGE_NAME ="ec.output";
	private Connection connection;
	private Channel channel;
	
	public RecieveMessages(String host, String username, String password) throws Exception {
		factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setPort(5671);
		factory.useSslProtocol();
	}
	
	public void startup(String routingKey, String username, MessageQueue<String> queue) throws Exception {
		connection = factory.newConnection();
		channel = connection.createChannel();
		String queueName = username + routingKey;
		
	    channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
	    channel.queueDeclare(queueName, true, false, false, null);
	    
	    channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
	    
	    Consumer consumer = new DeviceConsumer(channel, queue);
	    channel.basicConsume(queueName, true, consumer);
	}
	
	public void stop() throws Exception {
		channel.close();
		connection.close();
	}
	
	private void initQueue(){
		
	}

}
